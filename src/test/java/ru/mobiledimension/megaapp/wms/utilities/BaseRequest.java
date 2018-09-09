package ru.mobiledimension.megaapp.wms.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import static ru.mobiledimension.megaapp.wms.utilities.FileUtils.*;

import io.restassured.builder.ResponseSpecBuilder;
import org.testng.annotations.BeforeSuite;
import ru.mobiledimension.megaapp.wms.models.GetStatePurchases.*;
import ru.mobiledimension.megaapp.wms.models.RegisterPurchase.RegisterPurchaseRequest;
import ru.mobiledimension.megaapp.wms.models.RegisterShipping.RegisterShippingRequest;

public class BaseRequest {

    public static RequestSpecification getStateReference_requestSpec;
    public static RequestSpecification getStatePurchases_requestSpec;
    public static RequestSpecification registerPurchase_requestSpec;

    public static RequestSpecification registerShippingPointDelivery_requestSpec;
    public static RequestSpecification registerShippingParkingDelivery_requestSpec;
    public static RequestSpecification registerShippingMEGAAutoDelivery_requestSpec;
    public static RequestSpecification registerShippingCancelPointDelivery_requestSpec;
    public static RequestSpecification registerShippingCancelParkingDelivery_requestSpec;
    public static RequestSpecification registerShippingCancelMEGAAutoDelivery_requestSpec;

    public static ResponseSpecification responseSpec;

    public static ObjectMapper objectMapper;

    @BeforeSuite(description = "Ololo")
    public void setup() throws JsonProcessingException {
        PreemptiveBasicAuthScheme preemptiveBasicAuthScheme = new PreemptiveBasicAuthScheme();
        preemptiveBasicAuthScheme.setUserName("Administrator");
        preemptiveBasicAuthScheme.setPassword("123");

        String crmID = "2907a204-3292-e711-80cf-00155dfa2a2f";
        String mallID = "17";

        RestAssured.baseURI = "https://ikea.corp.rarus-cloud.ru";
        RestAssured.basePath = "/base_2/hs/Mobile/v1";
        RestAssured.authentication = preemptiveBasicAuthScheme;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        //RestAssured.proxy("192.168.20.238", 8888);

        getStateReference_requestSpec = new RequestSpecBuilder()
            .addHeader("Content-Type", "application/json")
            .build();

        objectMapper = new ObjectMapper();

        getStatePurchases_requestSpec = new RequestSpecBuilder()
            .addHeader("Content-Type", "application/json")
            .setBody(objectMapper.writeValueAsString(new GetStatePurchasesRequest.Builder()
                .withMallID(mallID)
                .withCustomer(crmID)
                .withStateTypes(1, 2, 3, 5)
                .build()))
            .build();

        registerPurchase_requestSpec = new RequestSpecBuilder()
            .addHeader("Content-Type", "application/json")
            .setBody(objectMapper.writeValueAsString(new RegisterPurchaseRequest.Builder()
                .withMalID(mallID)
                .withCustomer(crmID)
                .withBarcode(FileUtils.readLastUnregisteredBarcode())
                .build()))
            .build();

        registerShippingPointDelivery_requestSpec = new RequestSpecBuilder()
            .addHeader("Content-Type", "application/json")
            .setBody(objectMapper.writeValueAsString(new RegisterShippingRequest.Builder()
                .withMallID(mallID)
                .withCustomer(crmID)
                .withBarcode(getBarcodeList())
                .withCellType(1)
                .withCellID("8322")
                .withLicensePlate("A123BC150")
                .withReturnOfPurchases(false)
                .build()))
            .build();

        registerShippingParkingDelivery_requestSpec = new RequestSpecBuilder()
            .addHeader("Content-Type", "application/json")
            .setBody(objectMapper.writeValueAsString(new RegisterShippingRequest.Builder()
                .withMallID(mallID)
                .withCustomer(crmID)
                .withBarcode(getBarcodeList())
                .withCellType(2)
                .withCellID("4475")
                .withLicensePlate("A123BC150")
                .withReturnOfPurchases(false)
                .build()))
            .build();

        registerShippingMEGAAutoDelivery_requestSpec = new RequestSpecBuilder()
            .addHeader("Content-Type", "application/json")
            .setBody(objectMapper.writeValueAsString(new RegisterShippingRequest.Builder()
                .withMallID(mallID)
                .withCustomer(crmID)
                .withBarcode(getBarcodeList())
                .withCellType(3)
                .withCellID("")
                .withLicensePlate("A123BC150")
                .withReturnOfPurchases(false)
                .build()))
            .build();

        registerShippingCancelPointDelivery_requestSpec = new RequestSpecBuilder()
            .addHeader("Content-Type", "application/json")
            .setBody(objectMapper.writeValueAsString(new RegisterShippingRequest.Builder()
                .withMallID(mallID)
                .withCustomer(crmID)
                .withBarcode(getBarcodeList())
                .withCellType(1)
                .withCellID("8322")
                .withLicensePlate(null)
                .withReturnOfPurchases(true)
                .build()))
            .build();

        registerShippingCancelParkingDelivery_requestSpec = new RequestSpecBuilder()
            .addHeader("Content-Type", "application/json")
            .setBody(objectMapper.writeValueAsString(new RegisterShippingRequest.Builder()
                .withMallID(mallID)
                .withCustomer(crmID)
                .withBarcode(getBarcodeList())
                .withCellType(1)
                .withCellID("8322")
                .withLicensePlate(null)
                .withReturnOfPurchases(true)
                .build()))
            .build();

        registerShippingCancelMEGAAutoDelivery_requestSpec = new RequestSpecBuilder()
            .addHeader("Content-Type", "application/json")
            .setBody(objectMapper.writeValueAsString(new RegisterShippingRequest.Builder()
                .withMallID(mallID)
                .withCustomer(crmID)
                .withBarcode(getBarcodeList())
                .withCellType(1)
                .withCellID("8322")
                .withLicensePlate(null)
                .withReturnOfPurchases(true)
                .build()))
            .build();

        responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectResponseTime(lessThan(30000L))
            .expectContentType("application/json")
            .expectHeader("X-Server-Time", any(String.class))
            .build();

        RestAssured.responseSpecification = responseSpec;
    }

//    Данный метод необходим из-за того, что инициализация спецификаций происходит ДО вызова тестовых классов, что приводит к тому, что на момент обращения
//    к /RegisterShipping спецификация сформирована на основе изначального Verified-файла, который еще не содержал код незарегистрированной покупки.
//    Это приводило к тому, что оформление доставки происходило для N-1 покупок, из-за чего тесты, проверяющие целостность данных, всегда были в статусе FAILED
    private ArrayList<String> getBarcodeList() {
        ArrayList<String> barcodeList = readBarcodeList(getVerifiedBarcodeListFile());
        barcodeList.add(readLastUnregisteredBarcode());

        return barcodeList;
    }
}
