package Api.helper.Base;
import Api.BaseHelper;
import Api.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTestHelper implements BaseHelper {


    Logger logger = LogManager.getLogger(BaseTest.class);

    @Override
    @LoggerInstance(color = false)
    public void log(String msg) {
        logger.info(msg);
    }

    @Override
    @LoggerInstance(color = true)
    public void log(String msg, String color) {

        StringBuilder cString = new StringBuilder();

        if ("WHITE".equals(color)) {
            cString.append("\u001B[30m");// WHITE foreground
        } else if ("RED".equals(color)) {
            cString.append("\u001B[31m");// RED foreground
        } else if ("GREEN".equals(color)) {
            cString.append("\u001B[32m");// GREEN foreground
        } else if ("YELLOW".equals(color)) {
            cString.append("\u001B[33m");// YELLOW foreground
        } else if ("BLUE".equals(color)) {
            cString.append("\u001B[34m");// BLUE foreground
        } else if ("MAGENTA".equals(color)) {
            cString.append("\u001B[35m");// MAGENTA foreground
        } else if ("CYAN".equals(color)) {
            cString.append("\u001B[36m");// CYAN foreground
        } else if ("GRAY".equals(color)) {
            cString.append("\u001B[37m");// GRAY foreground
        } else {
            // Reset colors
        }
        cString.append(msg);
        cString.append("\u001B[0m");// Reset colors
        logger.info(cString);
    }


    public static WebDriver getDriver(String browserType, boolean headlessMode) throws Exception {

        Assert.assertNotNull(headlessMode);

        if("chrome".equalsIgnoreCase(browserType)){
            if(headlessMode){
                @DriverSetup(browserType = "chrome", headlessMode = true)
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("start-maximized");
                chromeOptions.addArguments("disable-infobars");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--window-size=1920x1080");
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(chromeOptions);
            }else{
                @DriverSetup(browserType = "chrome", headlessMode = false)
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("start-maximized");
                chromeOptions.addArguments("--kiosk");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            }

        }else{
            throw new Exception("There is only CHROME support for now. Please check your browserType parameter in getDriver() method");
        }

    }


}
