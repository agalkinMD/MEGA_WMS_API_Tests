package ru.mobiledimension.megaapp.wms.tests.GetStateReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.mobiledimension.megaapp.wms.models.GetStateReference.GetStateReferenceResponse;
import ru.mobiledimension.megaapp.wms.utilities.EndPoint;
import ru.mobiledimension.megaapp.wms.utilities.BaseRequest;

import static io.restassured.RestAssured.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Feature("Запрос списка статусов")
public class GetStateReferenceTest extends BaseRequest {

    Response response;
    String responseAsSting;
    ObjectMapper objectMapper;
    List<GetStateReferenceResponse> responseBody;

    @BeforeClass(description = "Выполнение запроса. Получение ответа. JSON parsing")
    public void getResponse() {
        response =
            given()
                .spec(getStateReference_requestSpec)
            .when()
                .post(EndPoint.GET_STATE_REFERENCE);

        objectMapper = new ObjectMapper();

        responseAsSting = response.asString();

        try {
            responseBody = Arrays.asList(objectMapper.readValue(responseAsSting, GetStateReferenceResponse[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(description = "Валидация JSON-схемы")
    @Description("Валидация JSON-схемы на соответствие названиям полей и их типам")
    @Severity(SeverityLevel.BLOCKER)
    public void GetStateReference_validateJSONSchema() {
        response.
            then()
                .body(matchesJsonSchemaInClasspath("schemas/GetStateReferenceSchema.json5"));
    }

    @Test(description = "Валидация количества статусов")
    @Description("Валидация соответствия количества статусов ожидаемому значению (6)")
    @Severity(SeverityLevel.NORMAL)
    public void GetStateReference_validateStateIDsAndStateTypesAmount() {
        Assert.assertEquals(responseBody.size(), 6);
    }

    @Test(description = "Валидация содержимого статусов")
    @Description("Валидация соответствия ID статуса соответствующему типу, а также его наименования")
    @Severity(SeverityLevel.NORMAL)
    public void GetStateReference_validateStateIDsAndStateTypesMatching() {
        for (GetStateReferenceResponse stateReference : responseBody) {
            switch (stateReference.getStateID()) {
                case 1:
                    Assert.assertEquals(stateReference.getStateType(), Integer.valueOf(1));
                    Assert.assertEquals(stateReference.getStateName(), "Принята");
                    break;
                case 2:
                    Assert.assertEquals(stateReference.getStateType(), Integer.valueOf(2));
                    Assert.assertEquals(stateReference.getStateName(), "Готовится к выдаче");
                    break;
                case 3:
                    Assert.assertEquals(stateReference.getStateType(), Integer.valueOf(3));
                    Assert.assertEquals(stateReference.getStateName(), "На месте выдачи");
                    break;
                case 4:
                    Assert.assertEquals(stateReference.getStateType(), Integer.valueOf(4));
                    Assert.assertEquals(stateReference.getStateName(), "Доставлена");
                    break;
                case 5:
                    Assert.assertEquals(stateReference.getStateType(), Integer.valueOf(1));
                    Assert.assertEquals(stateReference.getStateName(), "Возвращена на склад");
                    break;
                case 6:
                    Assert.assertEquals(stateReference.getStateType(), Integer.valueOf(5));
                    Assert.assertEquals(stateReference.getStateName(), "Выдача на МЕГА Авто");
                    break;
                default:
                    System.out.println("One of stateID mismatches stateType or has incorrect naming!");
                    break;
            }
        }
    }
}
