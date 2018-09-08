package ru.mobiledimension.megaapp.wms.tests.RegisterPurchase;

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
import ru.mobiledimension.megaapp.wms.models.RegisterPurchase.RegisterPurchaseResponse;
import ru.mobiledimension.megaapp.wms.tests.GetStatePurchases.GetStatePurchasesTest;
import ru.mobiledimension.megaapp.wms.utilities.EndPoint;
import static ru.mobiledimension.megaapp.wms.utilities.FileUtils.*;
import ru.mobiledimension.megaapp.wms.utilities.BaseRequest;

import java.io.*;

import static io.restassured.RestAssured.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Feature("Запрос на регистрацию покупки")
public class RegisterPurchaseTest extends BaseRequest {
    Response response;
    String responseAsString;
    ObjectMapper objectMapper;
    RegisterPurchaseResponse registerPurchaseResponseBody;

    @BeforeClass(description = "Выполнение запроса. Получение ответа. JSON parsing. Валидация отправленного и полученного кода покупки")
    public void getResponse() {
        deleteLastLine(getUnregisteredBarcodeListFile());

        response =
            given()
                .spec(registerPurchase_requestSpec)
            .when()
                .post(EndPoint.REGISTER_PURCHASE);

        objectMapper = new ObjectMapper();

        responseAsString = response.asString();

        try {
            registerPurchaseResponseBody = objectMapper.readValue(responseAsString, RegisterPurchaseResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(getLastAddedBarcode().equals(registerPurchaseResponseBody.getPurchases().get(0).getBarcode()),
                "Returned barcode doesn't match sent barcode!");
    }

    @Test(description = "Валидация JSON-схемы")
    @Description("Валидация JSON-схемы на соответствие названиям полей и их типам")
    @Severity(SeverityLevel.BLOCKER)
    public void RegisterPurchase_validateJSONSchema() {
        response
            .then()
                .body(matchesJsonSchemaInClasspath("schemas/RegisterPurchaseSchema.json5"));
    }

    @Test(description = "Валидация успешного добавления покупки")
    @Description("Валидация успешного добавления покупки путем анализа полей \"Error\", \"ErrorID\" и \"ErrorText\"")
    @Severity(SeverityLevel.NORMAL)
    public void RegisterPurchase_validateSuccessfulPurchaseAddition() {
        Assert.assertEquals(registerPurchaseResponseBody.getError(), Boolean.FALSE);
        Assert.assertEquals(registerPurchaseResponseBody.getErrorID(), Integer.valueOf(0));
        Assert.assertEquals(registerPurchaseResponseBody.getErrorText(), "");
    }

    @Test(description = "Валидация полученного кода покупки")
    @Description("Валидация соответствия полученного кода покупки путем сравнения значения до отправки со значением, полученным в ответе API")
    @Severity(SeverityLevel.NORMAL)
    public void RegisterPurchase_validateReturnedBarcodeValue() {
        Assert.assertEquals(getLastAddedBarcode(), registerPurchaseResponseBody.getPurchases().get(0).getBarcode());
    }

    @AfterClass(description = "Копирование добавленного кода покупки в файл \"BarcodeList_verified\". " +
            "Выполнение запроса /GetStatePurchases, а также соответствующих проверок")
    public void prepareForNextAPIMethodCall() {
        writeBarcodeList(getLastAddedBarcode(), getVerifiedBarcodeListFile());

        GetStatePurchasesTest getStatePurchasesTest = new GetStatePurchasesTest();
        getStatePurchasesTest.getStatePurchasesTests();
    }
}
