package base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;

import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigReader.get("base_url");

        requestSpec = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
    }
}
