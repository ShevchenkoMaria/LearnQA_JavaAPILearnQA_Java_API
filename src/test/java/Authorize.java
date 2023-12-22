import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import io.restassured.http.Headers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Authorize {
    @Test
    public void authTest(){
        Map<String,String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
        Map<String,String> cookies = responseGetAuth.getCookies();
        Headers headers = responseGetAuth.getHeaders();
        int userIdAuth = responseGetAuth.jsonPath().getInt("user_id");

        assertEquals(200,responseGetAuth.statusCode(),"Unexpected status code");
        assertTrue(cookies.containsKey("auth_sid"), "Response doesn't have'auth_sid' cookies");
        assertTrue(headers.hasHeaderWithName("x-csrf-token"),"Response doesn't have 'x-csrf-token' header");
        assertTrue(responseGetAuth.jsonPath().getInt("user_id")>0,"User id should be greater than 0");

        JsonPath responseCheckAuth = RestAssured
                .given()
                .header("x-csrf-token",responseGetAuth.getHeader("x-csrf-token"))
                .cookies("auth_sid",responseGetAuth.getCookie("auth_sid"))
                .get("https://playground.learnqa.ru/api/user/auth")
                .jsonPath();
        int userIdOnCheck = responseCheckAuth.getInt("user_id");
        assertTrue(userIdOnCheck>0,"Unexpected user id " + userIdOnCheck);
        assertEquals(userIdAuth,userIdOnCheck, "User id from auth is not equal");
    }
    @ParameterizedTest
    @ValueSource(strings = {"cookies", "headers"})
    public void authTestNegative(String condition) throws IllegalAccessException {
        Map<String,String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
        Map<String,String> cookies = responseGetAuth.getCookies();
        Headers headers = responseGetAuth.getHeaders();
        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");
        if(condition.equals("cookies")){
            spec.cookie("auth_sid",responseGetAuth.getCookie("auth_sid"));
        } else if (condition.equals("headers")){
            spec.header("x-csrf-token",responseGetAuth.getHeader("x-csrf-token"));
        } else {
            throw new IllegalAccessException("Condition value is known " + condition);
        }
        JsonPath responseCheckAuth = spec.get().jsonPath();
        assertEquals(0, responseCheckAuth.getInt("user_id"), "Uncorrect user id");
    }
}
