package apiTests.positiveCases;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CancelOrder {


    @Test
    public void cancelOrder() {
        String URL = "https://retailapi.sensebank.com.ua:8243/api/PartnerInstallment/v1.0/cancelOrder/";
        String partnerId = "partner";
        String username = "partner";
        String password = "!PaRt_Ne09_R#";

        CreateOrder createOrder = new CreateOrder();
        createOrder.createOrder();

        String orderId = CreateOrder.responseData.get("orderId");
        String cancelId = createOrder.generateRandomChar(3) + "-" + createOrder.generateRandomChar(3);

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("orderId", orderId);
        requestBody.put("cancelId", cancelId);
        requestBody.put("reasonCancel", "відмова кліента");

        Response response = given()
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(URL + partnerId);

        assertThat(response.getStatusCode(), equalTo(200));

        HashMap<String, String> responseBody = response.jsonPath().get();
        assertThat(responseBody.get("statusCode"), equalTo("CANCEL_IS_OK"));
        assertThat(responseBody.get("statusText"), equalTo("Отмена заказа с номером " + orderId + " в обработке."));
    }
}
