package apiTests.negativeCases;

import apiTests.positiveCases.CreateOrder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateOrderNegative {
    String partnerId = "partner";
    String username = "partner";
    String password = "!PaRt_Ne09_R#";

    @Test
    public void createOrderNoOrderId() {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("mPhone", "+380977896541");
        requestBody.put("panEnd", "9654");
        requestBody.put("shopId", "46546-БwfqК");
        requestBody.put("orderId", "");
        requestBody.put("orderSum", "619700");
        requestBody.put("orderTerm", "6");
        requestBody.put("eMailPartner", "asfaf@erger.te");
        requestBody.put("callBackURL", "https://dpartnapu01.sensebank.com.ua/v1/alfa_postback/");

        Response response = given()
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("https://retailapi.sensebank.com.ua:8243/api/PartnerInstallment/v1.0/createOrder/" + partnerId);

        assertThat(response.getStatusCode(), equalTo(200));

        HashMap<String, String> responseBody = response.jsonPath().get();
        assertThat(responseBody.get("statusCode"), equalTo("INVALID_ORDERID"));
        assertThat(responseBody.get("statusText"), equalTo("Указан некорректный номер заказа!"));
    }

    @Test
    public void createOrderNoPartnerId() {
        String partnerIdTest = "partnertest";
        CreateOrder createOrder = new CreateOrder();
        createOrder.createOrder();
        String orderId = createOrder.generateRandomChar(10);

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("mPhone", "+380977896541");
        requestBody.put("panEnd", "9654");
        requestBody.put("shopId", "46546-БwfqК");
        requestBody.put("orderId", orderId);
        requestBody.put("orderSum", "619700");
        requestBody.put("orderTerm", "6");
        requestBody.put("eMailPartner", "asfaf@erger.te");
        requestBody.put("callBackURL", "https://dpartnapu01.sensebank.com.ua/v1/alfa_postback/");

        Response response = given()
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("https://retailapi.sensebank.com.ua:8243/api/PartnerInstallment/v1.0/createOrder/" + partnerIdTest);

        assertThat(response.getStatusCode(), equalTo(200));

        HashMap<String, String> responseBody = response.jsonPath().get();
        assertThat(responseBody.get("statusCode"), equalTo("NO_PARTNERID"));
        assertThat(responseBody.get("statusText"), equalTo("Партнер " + partnerIdTest + " не зарегистрирован в системе!"));
    }
}
