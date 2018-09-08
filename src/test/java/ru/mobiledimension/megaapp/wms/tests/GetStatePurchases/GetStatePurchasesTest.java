package ru.mobiledimension.megaapp.wms.tests.GetStatePurchases;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.mobiledimension.megaapp.wms.models.Purchase;
import ru.mobiledimension.megaapp.wms.utilities.EndPoint;
import static ru.mobiledimension.megaapp.wms.utilities.FileUtils.*;

import ru.mobiledimension.megaapp.wms.utilities.BaseRequest;

import static io.restassured.RestAssured.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Feature("Запрос списка покупок")
public class GetStatePurchasesTest extends BaseRequest {
    Response response;
    static String responseToString;
    static ObjectMapper objectMapper;
    static List<Purchase> responseBody;

    @BeforeClass(description = "Выполнение запроса. Получение ответа. JSON parsing. Запись списка кодов покупок в файл и его валидация")
    public void getResponse() {
        response =
            given()
                .spec(getStatePurchases_requestSpec)
            .when()
                .post(EndPoint.GET_STATE_PURCHASES);

        objectMapper = new ObjectMapper();

        responseToString = response.asString();

        try {
            responseBody = Arrays.asList(objectMapper.readValue(responseToString, Purchase[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeBarcodeList(responseBody, getActualBarcodeListFile());

        Assert.assertTrue(isEqualContentInActualAndVerifiedFiles(getActualBarcodeListFile(), getVerifiedBarcodeListFile()),
                "Content in API response and \"BarcodeList_verified\" file doesn't match!");
    }

    @Test(description = "Валидация JSON-схемы")
    @Description("Валидация JSON-схемы на соответствие названиям полей и их типам")
    @Severity(SeverityLevel.BLOCKER)
    public void GetStatePurchases_ValidateJSONSchema() {
        response
            .then()
            .body(matchesJsonSchemaInClasspath("schemas/GetStatePurchasesSchema.json5"));
    }

    @Test(description = "Валидация списка кодов покупок")
    @Description("Валидация соответствия списка кодов покупок, полученных в ответе API, списку, сохраненному в файле \"BarcodeList_verified\"")
    @Severity(SeverityLevel.BLOCKER)
    public void GetStatePurchases_validateBarcodeList() {
        if (isEmpty(getVerifiedBarcodeListFile())) {
            System.out.println("GetStatePurchases_validateBarcodeList has been marked automatically as \"passed\" because empty " +
                    "\"BarcodeList_verified\" file. It happens only once due to first test suite execution.");
        }
        else {
            Assert.assertTrue(isEqualContentInActualAndVerifiedFiles(getActualBarcodeListFile(), getVerifiedBarcodeListFile()),
                    "Content in API response and \"BarcodeList_verified\" file doesn't match!");
        }
    }

    @AfterClass(description = "Копирование данных из файла \"BarcodeList_actual\" в файл \"BarcodeList_verified\"")
    public void prepareForNextAPIMethodCall() {
        copyBarcodeListToFile(getActualBarcodeListFile(), getVerifiedBarcodeListFile());
    }

    public void getStatePurchasesTests() {
        getResponse();
        GetStatePurchases_ValidateJSONSchema();
        GetStatePurchases_validateBarcodeList();
        prepareForNextAPIMethodCall();
    }

}
