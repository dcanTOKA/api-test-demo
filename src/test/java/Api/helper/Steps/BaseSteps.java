package Api.helper.Steps;

import Api.helper.Base.BaseTest;
import Api.helper.Constants.Elements;
import io.restassured.response.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
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

    public Pair<Long,Long> weekDayHourToMilisec(String metricValue){
        Assert.assertNotNull(metricValue);

        Long tolarance = 0L;

        List<String> args = Arrays.asList(metricValue.split(" "));
        int hours = 0;
        for (String arg:args) {
            if(arg.contains("w")){
                hours = hours + (168*Integer.parseInt(arg.substring(0,arg.indexOf('w'))));
            }
            if(arg.contains("d")){
                hours = hours + (24*Integer.parseInt(arg.substring(0,arg.indexOf('d'))));
                tolarance = 86340000L;
            }
            if(arg.contains("h")){
                hours = hours + (Integer.parseInt(arg.substring(0,arg.indexOf('h'))));
                tolarance = 3599000L;
            }
        }
        return Pair.of(TimeUnit.MILLISECONDS.convert(hours, TimeUnit.HOURS), tolarance);
    }

    public Pair<Long,Long> convertToMilisec(String metricValue){
        Assert.assertNotNull(metricValue);

        Long tolarance = 0L;

        List<String> args = Arrays.asList(metricValue.split(" "));
        int elapsedTime = 0;
        for (String arg:args) {
            if(arg.contains("w")){
                elapsedTime = elapsedTime + (604800 *Integer.parseInt(arg.substring(0,arg.indexOf('w'))));
            }
            if(arg.contains("d")){
                elapsedTime = elapsedTime + (86400 *Integer.parseInt(arg.substring(0,arg.indexOf('d'))));
                tolarance = 86340000L;
            }
            if(arg.contains("h")){
                elapsedTime = elapsedTime + (3600 * Integer.parseInt(arg.substring(0,arg.indexOf('h'))));
                tolarance = 3599000L;
            }
            if(arg.contains("min")){
                elapsedTime = elapsedTime + ( 60 * Integer.parseInt(arg.substring(0,arg.indexOf('m'))));
                tolarance = 59999L;
            }
            if(arg.contains("sec")){
                elapsedTime = elapsedTime + (Integer.parseInt(arg.substring(0,arg.indexOf('s'))));
                tolarance = 999L;
            }
        }
        return Pair.of(TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.SECONDS), tolarance);
    }

    public void scrollListDynamic(){
        for (int i = 0; i < 7 ; i++) {
            Actions action = new Actions(driver);
            action.sendKeys(Keys.ARROW_DOWN).perform();
        }
    }

    public List<String> csvReader(String file, String seperator) throws IOException {

        String line;
        List<String> bilgiler = new ArrayList<>();
        try (BufferedReader csvReader = new BufferedReader(new FileReader(file))) {

            while ((line = csvReader.readLine()) != null) {
                bilgiler = (Arrays.asList(line.split(seperator)));

            }
        } catch (FileNotFoundException e) {
            log("csv Reader error : " + e.getMessage());
        }
        return bilgiler;
    }

    public File[] getListOfFile(String fileName, String sourceDir) {
        Assert.assertNotNull(fileName);
        File folder = new File(sourceDir);
        return folder.listFiles();
    }

    public String getText(By by) {
        WebElement element ;
        String textToBeGot = null;
        boolean status = false;
        if (!status) {
            try {
                element = driver.findElement(by);
                textToBeGot = element.getText();
                status = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return textToBeGot;
    }

}
