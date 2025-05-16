package utilities;

import base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtils extends BaseTest {

    public static Response get(String endpoint) {
        return RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .get(endpoint)
                .then()
                .extract().response();
    }

    public static Response post(String endpoint, Object body) {
        return RestAssured
                .given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract().response();
    }

    // Add put(), delete() etc.
}

