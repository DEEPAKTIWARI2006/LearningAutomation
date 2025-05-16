package apiTests;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;
import base.BaseTest;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class UserApiTest extends BaseTest {

    @Test
    public void getUser_shouldReturn200() {
        Response res = ApiUtils.get("/products/");
        assertEquals(res.getStatusCode(), 200);
        assertEquals(res.jsonPath().getString("data.first_name"), "Janet");
    }
}
