import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class Ex7Test {
    @Test
    public void testLongRedirect() {
        String redirectedURL = "https://playground.learnqa.ru/api/long_redirect";
        int statusCode = 0;
        while (statusCode != 200){
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(redirectedURL)
                    .andReturn();
            String locationHeader = response.getHeader("location");
            statusCode = response.getStatusCode();
            System.out.println("Status code: " + statusCode);
            if (locationHeader!=null){
                System.out.println("The request was redirected to " + locationHeader + "\n");
                redirectedURL = locationHeader;
            }
        }
    }
}