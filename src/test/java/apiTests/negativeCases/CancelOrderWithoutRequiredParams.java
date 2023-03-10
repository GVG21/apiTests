package apiTests.negativeCases;

import apiTests.positiveCases.CreateOrder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class CancelOrderWithoutRequiredParams {
    String URL = "https://retailapi.sensebank.com.ua:8243/api/PartnerInstallment/v1.0/cancelOrder/";
    String partnerId = "partner";
    String username = "partner";
    String password = "!PaRt_Ne09_R#";

    @Test
    public void cancelOrderWithoutRequiredParamsOrderId() {
        CreateOrder createOrder = new CreateOrder();
        createOrder.createOrder();

        String cancelId = createOrder.generateRandomChar(3) + "-" + createOrder.generateRandomChar(3);

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("cancelId", cancelId);
        requestBody.put("reasonCancel", "відмова кліента");

        Response response = given()
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(URL + partnerId);

        assertThat(response.getStatusCode(), equalTo(200));

        HashMap<String, String> responseBody = response.jsonPath().get();
        assertThat(responseBody.get("statusCode"), equalTo("NO_IDS"));
        assertThat(responseBody.get("statusText"), equalTo("Не передан идентификатор заказа"));
    }

    @Test
    public void cancelOrderWithoutRequiredParamsCancelId() {
        CreateOrder createOrder = new CreateOrder();
        createOrder.createOrder();
        String orderId = CreateOrder.responseData.get("orderId");

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("orderId", orderId);
        requestBody.put("reasonCancel", "відмова кліента");

        Response response = given()
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(URL + partnerId);

        assertThat(response.getStatusCode(), equalTo(200));

        HashMap<String, String> responseBody = response.jsonPath().get();
        assertThat(responseBody.get("statusCode"), equalTo("INVALID_CANCELID"));
        assertThat(responseBody.get("statusText"), equalTo("Указан некорректный номер отмены!"));
    }
}
