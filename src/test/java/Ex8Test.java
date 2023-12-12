import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import java.lang.Thread;

public class Ex8Test {
    @Test
    public void testLongtimeJob() throws InterruptedException {
        JsonPath createTask = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String token = createTask.getString("token");
        String seconds = createTask.getString("seconds");
        JsonPath firstResponseWithToken = RestAssured
                .given()
                .queryParam("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String status = firstResponseWithToken.get("status");
        if (status.equals("Job is NOT ready")){
            int millis = Integer.parseInt(seconds)*1000;
            Thread.sleep(millis);
            JsonPath secondResponseWithToken = RestAssured
                    .given()
                    .queryParam("token", token)
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .jsonPath();
            String newStatus = secondResponseWithToken.get("status");
            String result = secondResponseWithToken.get("result");
            if (result.isEmpty()){
                System.out.println("TEST FAILED - result is empty");
            } else if (newStatus.equals("Job is ready")){
                System.out.println("TEST PASSED - Task is created, result = " + result);
            } else System.out.println("TEST FAILED - status " + newStatus + " isn't equal 'Job is ready'");
        } else System.out.println("TEST FAILED - status isn't correct");
    }
}
