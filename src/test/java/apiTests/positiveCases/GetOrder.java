package apiTests.positiveCases;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class GetOrder {

    @Test
    public void getOrder() {
        String URL = "https://retailapi.sensebank.com.ua:8243/api/PartnerInstallment/v1.0/getOrder/";
        String partnerId = "partner";
        String username = "partner";
        String password = "!PaRt_Ne09_R#";

        CreateOrder createOrder = new CreateOrder();
        createOrder.createOrder();

        String orderId = CreateOrder.responseData.get("orderId");
        String messageId =  CreateOrder.responseData.get("messageId");

        String mPhone = CreateOrder.requestData.get("mPhone");
        String panEnd = CreateOrder.requestData.get("panEnd");
        String shopId = CreateOrder.requestData.get("shopId");
        String orderSum = CreateOrder.requestData.get("orderSum");
        String orderTerm = CreateOrder.requestData.get("orderTerm");

        Response response = given()
                .auth().preemptive().basic(username, password)
                    .get(URL + partnerId + "?orderId=" + orderId)
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .extract().response();

        HashMap<String, String> responseBody = response.jsonPath().get();
        assertThat(responseBody.get("statusCode"), equalTo("INST_ALLOWED_OK"));
        assertThat(responseBody.get("orderId"), equalTo(orderId));
        assertThat(responseBody.get("messageId"), equalTo(messageId));
        assertThat(responseBody.get("mPhone"), equalTo(mPhone));
        assertThat(responseBody.get("panEnd"), equalTo(panEnd));
        assertThat(responseBody.get("shopId"), equalTo(shopId));
        assertThat(responseBody.get("orderSum"), equalTo(orderSum));
        assertThat(responseBody.get("orderTerm"), equalTo(orderTerm));
    }
}
