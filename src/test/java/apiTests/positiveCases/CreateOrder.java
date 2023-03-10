    package apiTests.positiveCases;

    import io.restassured.http.ContentType;
    import io.restassured.response.Response;
    import org.junit.jupiter.api.Test;
    import java.util.HashMap;
    import java.util.Random;

    import static io.restassured.RestAssured.given;
    import static org.hamcrest.MatcherAssert.assertThat;
    import static org.hamcrest.Matchers.*;


    public class CreateOrder {

            public String generateRandomChar(int length) {
                final String allChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                Random rand = new Random();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    int index = rand.nextInt(allChar.length());
                    char randomChar = allChar.charAt(index);
                    builder.append(randomChar);
                }
                return builder.toString();
            }

        public static HashMap<String, String> requestData;
        public static HashMap<String, String> responseData;

        @Test
        public void createOrder() {
            String URL = "https://retailapi.sensebank.com.ua:8243/api/PartnerInstallment/v1.0/createOrder/";
            String partnerId = "partner";
            String username = "partner";
            String password = "!PaRt_Ne09_R#";
            String orderId = generateRandomChar(10);

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
                    .post(URL + partnerId);

            assertThat(response.getStatusCode(), equalTo(200));

            HashMap<String, String> responseBody = response.jsonPath().get();
            assertThat(responseBody.get("statusCode"), equalTo("IN_PROCESSING"));
            assertThat(responseBody.get("statusText"), equalTo("Заказ в обработке!"));
            assertThat(responseBody.get("orderId"), equalTo(orderId));
            assertThat(responseBody.get("messageId"), is(notNullValue()));

            requestData = requestBody;
            responseData = responseBody;
        }
    }

