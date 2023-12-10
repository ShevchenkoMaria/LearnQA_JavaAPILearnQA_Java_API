import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class Ex5Test {
    @Test
    public void testGetJsonHomework2(){
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        String message = response.getString("messages.message[1]");
        System.out.println("text in the second message is : " + message);
    }
} 
