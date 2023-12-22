import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex11Test {
    @Test
    public void testAssertTrue(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        Map<String,String> cookies = response.getCookies();
        assertTrue(cookies.containsKey("HomeWork"), "Response doesn't have <HomeWork> cookie");
        if(cookies.containsKey("HomeWork")){
            assertEquals("hw_value", response.getCookie("HomeWork"), "<HomeWork> cookie has failed value");
        }
    }
}
