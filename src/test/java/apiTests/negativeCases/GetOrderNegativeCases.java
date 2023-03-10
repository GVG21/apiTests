package apiTests.negativeCases;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetOrderNegativeCases {

    String URL = "https://retailapi.sensebank.com.ua:8243/api/PartnerInstallment/v1.0/getOrder/";
    String partnerId = "partner";
    String username = "partner";
    String password = "!PaRt_Ne09_R#";

        @Test
        public void getOrderWithoutOrderId() {
            Response response = given()
                    .auth().preemptive().basic(username, password)
                    .get(URL + partnerId + "?orderId=")
                    .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .extract().response();

            HashMap<String, Object> responseBody = response.jsonPath().get();
            assertThat(responseBody.get("statusCode"), equalTo("NO_IDS"));
            assertThat(responseBody.get("statusText"), equalTo("Не передан идентификатор заказа"));
        }

        @Test
    public void getOrderWithNoApp() {
            Response response = given()
                    .auth().preemptive().basic(username, password)
                    .get(URL + partnerId + "?orderId=" + "dsasda")
                    .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .extract().response();

            HashMap<String, Object> responseBody = response.jsonPath().get();
            assertThat(responseBody.get("statusCode"), equalTo("NO_APP"));
            assertThat(responseBody.get("statusText"), equalTo("Заказ не найден!"));
        }
    }

