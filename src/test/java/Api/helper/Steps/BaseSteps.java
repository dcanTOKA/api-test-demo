package Api.helper.Steps;

import Api.helper.Base.BaseTest;
import Api.helper.Constants.Elements;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BaseSteps extends BaseTest{


    public void delay(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {

            log("Delay de hata : " + e.getMessage(), "RED");
            Thread.currentThread().interrupt();
        }
    }

    public String WeavedOutput(Elements constant){
        return constant.name();
    }

    public WebElement findElement(Elements constant) {
        WebDriverWait wait = new WebDriverWait(driver, 20, 100L);

        if("xpath".equals(constant.getKey())){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(constant.getValue())));
            WebElement element = driver.findElement(By.xpath(constant.getValue()));
            Assert.assertNotNull(element);
            return element;
        }else if("css".equals(constant.getKey())){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(constant.getValue())));
            WebElement element = driver.findElement(By.cssSelector(constant.getValue()));
            Assert.assertNotNull(element);
            return element;
        }else{
            try {
                throw new Exception("The key is wrong !");
            } catch (Exception e) {
                log(e.getMessage());
            }
            return null;
        }
    }

    public void click(Elements constant) {
        //waitUntil(constant);
        WebElement element = findElement(constant);
        element.click();
        log("Element " + WeavedOutput(constant) + " is clicked .");

    }

    public void sendKeys(Elements constant, String text, boolean submit) {
        //waitUntil(constant);
        WebElement element = findElement(constant);
        log("Element " + WeavedOutput(constant) + " is found . ");
        delay(1);
        element.clear();
        delay(1);
        element.sendKeys(text);
        if (submit) {
            element.submit();
            log("Submit is succesfull");
        }
        log("Text is written to " + WeavedOutput(constant));

    }
    public String getText(Elements constant) {
        WebElement element ;
        String textToBeGot = null;
        boolean status = false;
        if (!status) {
            try {
                element = findElement(constant);
                textToBeGot = element.getText();
                status = true;
            } catch (Exception e) {
                log("Element could not be found... INFO : " + WeavedOutput(constant), "RED");
            }
        }
        return textToBeGot;
    }

    public JSONArray readJsonArrayUnderResources(String path){
        JSONParser parser = new JSONParser();

        // Proceed with getting users from users.json file
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) parser.parse(new FileReader("src/test/resources/" + path));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONObject readJsonObjectUnderResources(String path){
        JSONParser parser = new JSONParser();

        // Proceed with getting user from users.json file
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("src/test/resources/" + path));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public void checkTheUserInfos(Response response, JSONObject user){
        // Check if all the fields matches or not
        Assert.assertEquals(user.get("id").toString(), response.jsonPath().get("id").toString());
        Assert.assertEquals(user.get("username"), response.jsonPath().get("username"));
        Assert.assertEquals(user.get("firstName"), response.jsonPath().get("firstName"));
        Assert.assertEquals(user.get("lastName"), response.jsonPath().get("lastName"));
        Assert.assertEquals(user.get("password").toString(), response.jsonPath().get("password").toString());
        Assert.assertEquals(user.get("email"), response.jsonPath().get("email"));
        Assert.assertEquals(user.get("phone").toString(), response.jsonPath().get("phone").toString());
        Assert.assertEquals(user.get("userStatus").toString(), response.jsonPath().get("userStatus").toString());
    }

    public long timeBetweenTwoDates(String first , String second , String typeSelector) throws java.text.ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date firstDate = sdf.parse(first);
        Date secondDate = sdf.parse(second);
        long diffInMillies;

        diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());

        if("days".equals(typeSelector)){
            return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        }else{
            return diffInMillies;
        }
    }

    public String daysToWeeks(long days){
        long week = 0;
        long day = 0;
        if(days >= 7){
            week = days / 7;
            day = days % 7;
        }
        return (week + "w " + day + "d");
    }

}
