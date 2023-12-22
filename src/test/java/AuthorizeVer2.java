import io.restassured.RestAssured;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthorizeVer2 {
    String cookie;
    String header;
    int userIdAuth;
    @BeforeEach
    public void loginUser(){
        Map<String,String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
        this.cookie = responseGetAuth.getCookie("auth_sid");
        this.header = responseGetAuth.getHeader("x-csrf-token");
        this.userIdAuth = responseGetAuth.jsonPath().getInt("user_id");
    }

    @Test
    public void authTest(){
        JsonPath responseCheckAuth = RestAssured
                .given()
                .header("x-csrf-token",this.header)
                .cookie("auth_sid", this.cookie)
                .get("https://playground.learnqa.ru/api/user/auth")
                .jsonPath();
        int userIdOnCheck = responseCheckAuth.getInt("user_id");
        assertTrue(userIdOnCheck>0,"Unexpected user id " + userIdOnCheck);
        assertEquals(userIdAuth,userIdOnCheck, "User id from auth is not equal");
    }
    @ParameterizedTest
    @ValueSource(strings = {"cookies", "headers"})
    public void authTestNegative(String condition) throws IllegalAccessException {
        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");
        if(condition.equals("cookies")){
            spec.cookie("auth_sid",this.cookie);
        } else if (condition.equals("headers")){
            spec.header("x-csrf-token",this.header);
        } else {
            throw new IllegalAccessException("Condition value is known " + condition);
        }
        JsonPath responseForCheck= spec.get().jsonPath();
        assertEquals(0, responseForCheck.getInt("user_id"), "Uncorrect user id");
    }
}
