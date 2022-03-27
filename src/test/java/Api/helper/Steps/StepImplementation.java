package Api.helper.Steps;

import Api.helper.Utils.RestHandler;
import com.thoughtworks.gauge.Step;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.util.concurrent.TimeUnit;

import static Api.helper.Constants.Endpoints.*;

public class StepImplementation extends BaseSteps {

    private Response response;

    @Step("Create a user")
    public void createUser() {

        // Fetch user & users
        JSONObject user = readJsonObjectUnderResources("swagger_users/users.json");

        // Rest Handler instance
        RestHandler restHandler = new RestHandler(CREATE_USER);

        // For setting body if you want to pass
        restHandler.setBody(user);

        // Rest Handler will handle the request type automatically based on the method value in Endpoints Enum
        restHandler.requestHandler();

        // Get response
        response = restHandler.response();

        // Get response time
        response.getTimeIn(TimeUnit.MILLISECONDS);

        // Check whether it is successful
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
        log("Successfull operation for creating a user - Status Code : " + statusCode, "GREEN");

        // Check the response message. It should be the same as the id of the user.
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(user.get("id").toString(), message);
        log("Message is : " + message, "GREEN");

        // Check whethet the response time is suitable
        long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        Assert.assertTrue(3000 > responseTime);
        log("Suitable response time : " + responseTime + " milisecond", "GREEN");
    }

    @Step("Get the user whose username is <username> , expect the <expectedStatusCode> status code and compare it in <path> file")
    public void getUser(String username, int expectedStatusCode,  String path) {
        // Set the username in Enum variable exactly that you want to search.
        GET_USER.setEndpoint(GET_USER.getEndpoint().replaceAll("\\{.*?\\}", username));

        // Rest Handler instance
        RestHandler restHandler = new RestHandler(GET_USER);

        // Rest Handler will handle the request type automatically based on the method value in Endpoints Enum
        restHandler.requestHandler();

        // Get response
        response = restHandler.response();

        // Get response time
        response.getTimeIn(TimeUnit.MILLISECONDS);

        // Check whether it is successful
        int statusCode = response.getStatusCode();
        Assert.assertEquals(expectedStatusCode, statusCode);
        if (statusCode == 200) {
            log("Status code : " + statusCode, "GREEN");
            // Check the response / compare it with the json file that has been used for creating this user in the first time.
            JSONObject user = readJsonObjectUnderResources(path);

            checkTheUserInfos(response, user);
            log("It is the same as the user that has been created in the fisrt step. Operation success / correct user information", "GREEN");
        }
        else if (statusCode == 404) {
            log("Status code : " + statusCode, "YELLOW");
            log(response.jsonPath().get("message"));
        }
        else {
            log("Status code : " + statusCode, "RED");
            log(response.jsonPath().get("message"));
        }


        // Check whether the response time is suitable
        long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        Assert.assertTrue(3000 > responseTime);
        log("Suitable response time : " + responseTime + " milisec", "GREEN");
    }

    @Step("Delete the user whose username is <username>")
    public void deleteUser(String username) {
        // Set the username in Enum variable exactly that you want to delete.
        DELETE_USER.setEndpoint(DELETE_USER.getEndpoint().replaceAll("\\{.*?\\}", username));

        // Rest Handler instance
        RestHandler restHandler = new RestHandler(DELETE_USER);

        // Rest Handler will handle the request type automatically based on the method value in Endpoints Enum
        restHandler.requestHandler();

        // Get response
        response = restHandler.response();

        // Get response time
        response.getTimeIn(TimeUnit.MILLISECONDS);

        // Check whether it is successful
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
        log("Successfull operation for deleting user  : " + statusCode, "GREEN");

        // Check the response message. It should be the same as the username of the user.
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(username, message);
        log("Message is : " + message, "GREEN");

        // Check whethet the response time is suitable
        long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        Assert.assertTrue(3000 > responseTime);
        log("Suitable response time : " + responseTime + "milisec", "GREEN");
    }

    @Step("Update the user whose username is <username>")
    public void updateUser(String username) {

        JSONObject updatedUser = readJsonObjectUnderResources("swagger_users/email-phone-updated-user.json");

        UPDATE_USER.setEndpoint(UPDATE_USER.getEndpoint().replaceAll("\\{.*?\\}", username));

        // Rest Handler instance
        RestHandler restHandler = new RestHandler(UPDATE_USER);

        // For setting body if you want to pass
        restHandler.setBody(updatedUser);

        // Rest Handler will handle the request type automatically based on the method value in Endpoints Enum
        restHandler.requestHandler();

        // Get response
        response = restHandler.response();

        // Get response time
        response.getTimeIn(TimeUnit.MILLISECONDS);

        // Check whether it is successful
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
        log("Successfull operation for updating user  : " + statusCode, "GREEN");

        // Check the response message. It should be the same as the id of the user.
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(updatedUser.get("id").toString(), message);
        log("Message is : " + message, "GREEN");

        // Check whether the response time is suitable
        long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        Assert.assertTrue(3000 > responseTime);
        log("Suitable response time : " + responseTime + " milisecond", "GREEN");
    }
}
