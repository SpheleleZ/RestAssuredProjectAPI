package secondtest;

/*
Different ways to create POST request body
1.Post request body using Hashmap
2.Post request body using Org.json
3.Post request body using POJO class
4.Post request body using external json file Data
 */


import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class SecondTestApi {

    /*
    Post request using HasMap and delete that added user
     */
    @Test
    public void postUsingHashMap(){
        String contentType = "application/json";
        baseURI = "http://localhost:3000/";

        HashMap data = new HashMap();
        data.put("id",6);
        data.put("firstName","Jiji");
        data.put("lastName","Zwane");
        data.put("age",25);
        data.put("job","QA Engineer");

        given()
                .contentType(contentType)
                .body(data)
                .when().post("users")
                .then()
                .statusCode(201)
                .body("firstName",equalTo("Jiji"))
                .body("age",equalTo(25))
                .log().body();
    }

    /*
    delete user after it has added
     */
    @Test(priority = 1,dependsOnMethods = "postUsingHashMap")
    public void deleteUsers(){
        baseURI = "http://localhost:3000/";

        given()
                .when().delete("users/6")
                .then()
                .statusCode(200);
    }
}
