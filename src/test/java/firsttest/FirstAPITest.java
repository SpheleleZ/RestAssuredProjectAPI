package firsttest;
/*
 given()
    Content type ,set cookies, add auth, add param , set headers, info etc......
 when()
    get, post, put , delete
 then()
    Validate status code, extract response, extract headers cookies and response body ......
  */


import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FirstAPITest {
    int id;

    @Test

    public void getSingleUserRequest() {
        baseURI = "https://reqres.in/";

        given()
                .when().get("/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .log().all();
    }

    @Test(priority = 1)
    public void getListUsersRequest() {
        baseURI = "https://reqres.in/";

        given()
                .when().get("api/users?page=2")
                .then()
                .statusCode(200)
                .body("data[4].id", equalTo(11))
                .log().body();
    }

    @Test(priority = 2)
    public void postCreateUserRequest() {
        baseURI = "https://reqres.in/";
        String contentType = "application/json";
        String bodyData = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        id = given().contentType(contentType)
                .body(bodyData)
                .when()
                .post("api/users")
                .jsonPath().getInt("id");
    }

    @Test(priority = 3, dependsOnMethods = "postCreateUserRequest")
    public void putUpdateUserRequest() {
        baseURI = "https://reqres.in/";
        String contentType = "application/json";
        String bodyData = "{\n" +
                "    \"name\": \"Sphelele\",\n" +
                "    \"job\": \"QA Engineer\"\n" +
                "}";

        given().contentType(contentType)
                .body(bodyData)
                .when()
                .put("api/users/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo("Sphelele"))
                .log().body();
    }

    @Test(priority = 4)
    public void deleteUserRequest() {
        baseURI = "https://reqres.in/";

        given()
                .when().delete("/api/users/" + id)
                .then()
                .statusCode(204)
                .log().all();
    }
}
