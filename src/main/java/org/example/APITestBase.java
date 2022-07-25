package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.Assertion;

import java.io.FileNotFoundException;

import static io.restassured.RestAssured.given;

public class APITestBase {

    public static final String MMI_ENDPOINT = "/mmi/now";

    public static RequestSpecification requestSpecification;
    public ResponseSpecification okResponseSpecification;
    public ResponseSpecification badResponseSpecification;

    @BeforeTest
    public void startTest(ITestContext context) throws FileNotFoundException {

        // construct requestSpec
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.tickertape.in");
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        // construct responseSpec
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectContentType(ContentType.JSON);
        responseSpecBuilder.log(LogDetail.ALL);
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_OK);
        okResponseSpecification = responseSpecBuilder.build();

        responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectContentType(ContentType.JSON);
        responseSpecBuilder.log(LogDetail.ALL);
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_BAD_REQUEST);
        badResponseSpecification = responseSpecBuilder.build();
    }

    public static String getMMINow() {
        Response response = given()
                .spec(requestSpecification)
                .when()
                .get(MMI_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
        JSONObject responseJson = response.as(JSONObject.class);
        String responseStatus = responseJson.get("success").toString();
        return responseStatus;
    }
}
