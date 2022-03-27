package Api.helper.Utils;

import Api.helper.Base.BaseTestHelper;
import Api.helper.Constants.Endpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static Api.helper.Constants.RestRequestFields.CONTENT_TYPE;
import static io.restassured.RestAssured.given;

public class RestHandler {
    private String method, endpoint;
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private Object BODY;
    private Response response;

    BaseTestHelper baseTestHelper = new BaseTestHelper();


    public RestHandler(Endpoints end){
        RestAssured.baseURI = BASE_URL;
        method = end.getMethod();
        endpoint = end.getEndpoint();
    }

    public void requestHandler(){
        switch (method){
            case "get":
                getRequest();
                break;
            case "post":
                postRequest((JSONObject) BODY);
                break;
            case "put":
                putRequest((JSONObject) BODY);
                break;
            case "delete":
                deleteRequest();
                break;
            default:
        }
    }

    protected void getRequest(){
        RestAssured.basePath = endpoint;

        baseTestHelper.log("GET Request for " + BASE_URL + endpoint);

        this.response = given()
                .contentType(CONTENT_TYPE.getValue())
                .when()
                .get()
                .then()
                .extract()
                .response();
    }

    protected void postRequest(JSONObject body){
        RestAssured.basePath = endpoint;

        baseTestHelper.log("POST Request for " + BASE_URL + endpoint);

        this.response = given()
                .contentType(CONTENT_TYPE.getValue())
                .body(body.toJSONString())
                .when()
                .post()
                .then()
                .extract()
                .response();
    }

    protected void putRequest(JSONObject body){
        RestAssured.basePath = endpoint;

        baseTestHelper.log("PUT Request for " + BASE_URL + endpoint);

        RequestSpecification request = RestAssured.given();
        request.contentType(CONTENT_TYPE.getValue());
        request.body(body);
        this.response = request
                .put()
                .then()
                .extract()
                .response();
    }

    protected void deleteRequest(){
        RestAssured.basePath = endpoint;

        baseTestHelper.log("DELETE Request for " + BASE_URL + endpoint);

        RequestSpecification request = RestAssured.given();
        request.contentType(CONTENT_TYPE.getValue());
        this.response = request
                .delete()
                .then()
                .extract()
                .response();
    }

    public void setBody(Object json){
        BODY =  json;
    }

    public Response response(){
        return this.response;
    }
}
