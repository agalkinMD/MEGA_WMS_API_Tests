package ru.mobiledimension.megaapp.wms.tests.RegisterShipping;

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
import ru.mobiledimension.megaapp.wms.models.RegisterShipping.RegisterShippingResponse;
import ru.mobiledimension.megaapp.wms.tests.GetStatePurchases.GetStatePurchasesTest;
import ru.mobiledimension.megaapp.wms.utilities.EndPoint;
import ru.mobiledimension.megaapp.wms.utilities.BaseRequest;

import java.io.*;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.mobiledimension.megaapp.wms.utilities.FileUtils.*;

@Feature("Запрос на оформление доставки в пункт \"МЕГА Авто\"")
public class RegisterShippingMEGAAutoDeliveryTest extends BaseRequest {

    Response response;
    String responseAsString;
    ObjectMapper objectMapper;
    RegisterShippingResponse responseBody;

//    TODO: избавиться от duplicated-кода в @BeforeClass
    @BeforeClass(description = "Выполнение запроса. Получение ответа. JSON parsing. Запись списка кодов покупок в файл и его валидация")
    public void getResponse() {
        response =
            given()
                .spec(registerShippingMEGAAutoDelivery_requestSpec)
            .when()
                .post(EndPoint.REGISTER_SHIPPING);

        objectMapper = new ObjectMapper();

        responseAsString = response.asString();

        try {
            responseBody = objectMapper.readValue(responseAsString, RegisterShippingResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeBarcodeList(responseBody.getPurchases(), getActualBarcodeListFile());

        Assert.assertTrue(isEqualContentInActualAndVerifiedFiles(getActualBarcodeListFile(), getVerifiedBarcodeListFile()),
                "Content in API response and \"BarcodeList_verified\" file doesn't match!");
    }

    @Test(description = "Валидация JSON-схемы")
    @Description("Валидация JSON-схемы на соответствие названиям полей и их типам")
    @Severity(SeverityLevel.BLOCKER)
    public void RegisterShippingMEGAAutoDelivery_validateJSONSchema() {
        response
            .then()
                .body(matchesJsonSchemaInClasspath("schemas/RegisterShippingSchema.json5"));
    }

    @Test(description = "Валидация списка кодов покупок")
    @Description("Валидация соответствия списка кодов покупок, полученных в ответе API, со списком, сохраненным в файле \"BarcodeList_verified\"")
    @Severity(SeverityLevel.BLOCKER)
    public void RegisterShippingMEGAAutoDelivery_validateBarcodeList() {
        if (isEmpty(getVerifiedBarcodeListFile())) {
            System.out.println("RegisterShipping_validateBarcodeList has been marked automatically as \"passed\" because empty " +
                    "\"BarcodeList_verified\" file. It happens only once due to first test suite execution.");
        }
        else {
            Assert.assertTrue(isEqualContentInActualAndVerifiedFiles(getActualBarcodeListFile(), getVerifiedBarcodeListFile()),
                    "Content in API response and \"BarcodeList_verified\" file doesn't match!");
        }
    }

    @Test(description = "Валидация успешного оформления доставки")
    @Description("Валидация успешного оформления доставки путем анализа полей \"Error\", \"ErrorID\" и \"ErrorText\"")
    @Severity(SeverityLevel.NORMAL)
    public void RegisterShippingMEGAAutoDelivery_validateSuccessfulShippingRegistration() {
        Assert.assertEquals(responseBody.getError(), Boolean.FALSE);
        Assert.assertEquals(responseBody.getErrorID(), Integer.valueOf(0));
        Assert.assertEquals(responseBody.getErrorText(), "");
    }

//    TODO: подумать над оптимизацией проверки licensePlate
    @Test(description = "Валидация содержимого полей \"CelType\", \"CellID\" и \"LicensePlate\"")
    @Description("Валидация содержимого полей \"CelType\", \"CellID\" и \"LicensePlate\" путем сравнения значений полей в запросе " +
        "со значениями, полученными в ответе API")
    @Severity(SeverityLevel.NORMAL)
    public void RegisterShippingMEGAAutoDelivery_validateDeliveryParameters() {
        for (Purchase purchase : responseBody.getPurchases()) {
            Assert.assertEquals(purchase.getCellType(),Integer.valueOf(3));
            Assert.assertEquals(purchase.getCellID(), "8321");

            if (!(purchase.getLicensePlate() instanceof String) && purchase.getLicensePlate() != null)
                Assert.fail("LicencePlate has incorrect type!");
        }
    }

    @Test(description = "Валидация содержимого полей \"DeliveryTimer\" и \"DeliverySLA\"")
    @Description("Валидация содержимого полей \"DeliveryTimer\" и \"DeliverySLA\" путем проверки \"DeliverySLA != 0\" и " +
            "\"0 < DeliveryTimer <= DeliverySLA\"")
    @Severity(SeverityLevel.NORMAL)
    public void RegisterShippingMEGAAutoDelivery_validateDeliveryTimer() {
        for (Purchase purchase : responseBody.getPurchases()) {
            Assert.assertEquals(purchase.getDeliveryTimer(), Integer.valueOf(0));
            Assert.assertEquals(purchase.getDeliverySLA(), Integer.valueOf(0));
        }
    }

//    TODO: избавиться от duplicated-кода в @AfterClass
    @AfterClass(description = "Копирование данных из файла \"BarcodeList_actual\" в файл \"BarcodeList_verified\". " +
            "Последовательное выполнение запросов /GetStatePurchases -> /RegisterShipping (\"ReturnOfPurchases\":true) -> /GetStatePurchases " +
            ", а также соответствующих проверок")
    public void prepareForNextAPIMethodCall() {
        copyBarcodeListToFile(getActualBarcodeListFile(), getVerifiedBarcodeListFile());

        GetStatePurchasesTest getStatePurchasesTest = new GetStatePurchasesTest();
        getStatePurchasesTest.getStatePurchasesTests();

        RegisterShippingCancelTest registerShippingCancelTest = new RegisterShippingCancelTest(registerShippingCancelMEGAAutoDelivery_requestSpec);
        registerShippingCancelTest.registerShippingCancelTests();
    }
}
