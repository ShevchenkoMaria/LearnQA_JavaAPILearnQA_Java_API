import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex12Test {
    @Test
    public void testAssertTrue(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        Headers headers = response.getHeaders();
        assertTrue(headers.hasHeaderWithName("x-secret-homework-header"),"Response doesn't have 'x-secret-homework-header' header");
        if(headers.hasHeaderWithName("x-secret-homework-header")){
            assertEquals("Some secret value", response.getHeader("x-secret-homework-header"), "<x-secret-homework-header> cookie has failed value");
        }
    }
}
