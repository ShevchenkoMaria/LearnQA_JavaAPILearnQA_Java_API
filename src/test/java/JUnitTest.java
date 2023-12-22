import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUnitTest {
    @Test
    public void testAssertTrue(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/map")
                .andReturn();
        assertTrue(response.statusCode()==200,"Unexpectes status code");
    }
    @Test
    public void testAssertEquals(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/map")
                .andReturn();
        assertEquals(200, response.statusCode(),"Unexpectes status code");
    }
    @Test
    public void testAssertEquals2(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/map2")
                .andReturn();
        assertEquals(200, response.statusCode(),"Unexpectes status code");
    }

    @Test
    public void testHelloWordWithoutName(){
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
        String answer = response.getString("answer");
        assertEquals("Hello, someone", answer,"The answer is not expected");
    }
    @Test
    public void testHelloWordWithName(){
        String name = "Username";
        JsonPath response = RestAssured
                .given()
                .queryParam("name", name)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
        String answer = response.getString("answer");
        assertEquals("Hello, " + name, answer,"The answer is not expected");
    }
    @ParameterizedTest
    @ValueSource(strings = {"", "John", "Alex"})
    public void testWithParams(String name){
        Map<String,String> params = new HashMap<>();
        if (name.length()>0){
            params.put("name", name);
        }
        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
        String answer = response.getString("answer");
        String expectedName = (name.length()>0)? name : "someone";
        assertEquals("Hello, " + expectedName, answer,"The answer is not expected");
    }
}
