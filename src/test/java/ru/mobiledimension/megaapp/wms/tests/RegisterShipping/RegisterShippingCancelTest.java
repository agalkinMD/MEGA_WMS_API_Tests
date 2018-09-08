package ru.mobiledimension.megaapp.wms.tests.RegisterShipping;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import ru.mobiledimension.megaapp.wms.models.Purchase;
import ru.mobiledimension.megaapp.wms.models.RegisterShipping.RegisterShippingResponse;
import ru.mobiledimension.megaapp.wms.tests.GetStatePurchases.GetStatePurchasesTest;
import ru.mobiledimension.megaapp.wms.utilities.EndPoint;
import ru.mobiledimension.megaapp.wms.utilities.BaseRequest;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.mobiledimension.megaapp.wms.utilities.FileUtils.*;

public class RegisterShippingCancelTest extends BaseRequest {
    Response response;
    String responseAsString;
    ObjectMapper objectMapper;
    RegisterShippingResponse responseBody;
    RequestSpecification requestSpec;

    public RegisterShippingCancelTest(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

//    TODO: избавиться от duplicated-кода в @BeforeClass (getResponse)
    public void getResponse() {
        response =
            given()
                .spec(requestSpec)
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

    public void RegisterShippingCancel_validateJSONSchema() {
        response
            .then()
            .body(matchesJsonSchemaInClasspath("schemas/RegisterShippingSchema.json5"));
    }

    public void RegisterShippingCancel_validateSuccessfulShippingRegistration() {
        Assert.assertEquals(responseBody.getError(), Boolean.FALSE);
        Assert.assertEquals(responseBody.getErrorID(), Integer.valueOf(0));
        Assert.assertEquals(responseBody.getErrorText(), "");
    }

    public void RegisterShippingCancel_validateDeliveryParameters() {
        for (Purchase purchase : responseBody.getPurchases()) {
            Assert.assertEquals(purchase.getCellType(), null);
            Assert.assertEquals(purchase.getCellID(), null);
            Assert.assertEquals(purchase.getLicensePlate(), null);
        }
    }

    public void RegisterShippingCancel_validateDeliveryTimer() {
        for (Purchase purchase : responseBody.getPurchases()) {
            if (requestSpec.equals(registerShippingCancelMEGAAutoDelivery_requestSpec)) {
                Assert.assertEquals(purchase.getDeliveryTimer(), Integer.valueOf(0));
                Assert.assertEquals(purchase.getDeliverySLA(), Integer.valueOf(0));
            }
            else {
                Assert.assertTrue(purchase.getDeliverySLA() != 0);
                Assert.assertTrue((0 < purchase.getDeliveryTimer()) && (purchase.getDeliveryTimer() <= purchase.getDeliverySLA()));

            }
        }
    }

    public void RegisterShippingCancel_validateBarcodeList() {
        if (isEmpty(getVerifiedBarcodeListFile())) {
            System.out.println("RegisterShippingCancel_validateBarcodeList has been marked automatically as \"passed\" because empty " +
                    "\"BarcodeList_verified\" file. It happens only once due to first test suite execution.");
        }
        else {
            Assert.assertTrue(isEqualContentInActualAndVerifiedFiles(getActualBarcodeListFile(), getVerifiedBarcodeListFile()),
                    "Content in API response and \"BarcodeList_verified\" file doesn't match!");
        }
    }

    public void prepareForNextAPIMethodCall() {
        copyBarcodeListToFile(getActualBarcodeListFile(), getVerifiedBarcodeListFile());

        GetStatePurchasesTest getStatePurchasesTest = new GetStatePurchasesTest();
        getStatePurchasesTest.getStatePurchasesTests();
    }

    public void registerShippingCancelTests() {
        getResponse();
        RegisterShippingCancel_validateJSONSchema();
        RegisterShippingCancel_validateSuccessfulShippingRegistration();
        RegisterShippingCancel_validateDeliveryParameters();
        RegisterShippingCancel_validateDeliveryTimer();
        RegisterShippingCancel_validateBarcodeList();
        prepareForNextAPIMethodCall();
    }
}
