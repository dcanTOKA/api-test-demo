package Api.helper.Base;

import Api.*;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.BeforeStep;
import com.thoughtworks.gauge.ExecutionContext;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;


public class BaseTest extends BaseTestHelper{

    public static WebDriver driver;

    public String OOBEYA_LOGIN_PAGE = "http://qa-dashboard.testinium.com";


    @BeforeScenario
    public void setup(ExecutionContext context) throws Exception {


        /*
        driver = getDriver(BrowserType.CHROME, BrowserType.HEADLESS_MODE_ON);

        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get(OOBEYA_LOGIN_PAGE);

         */

        log("======================== TEST STARTED =============================", "CYAN");
        log("SPECIFICATION : " + context.getCurrentSpecification().getName(), "CYAN");
        log("SCENERIO : " + context.getCurrentScenario().getName(), "CYAN");
    }

    @BeforeStep
    public void stepStartUp(ExecutionContext context){

        log("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$", "BLUE");
        log("STEP : " + context.getCurrentStep().getText(), "BLUE");
    }


    @AfterScenario
    public void tearDown(){
        log("======================= TEST END ==========================", "CYAN");
        //driver.quit();
    }
}
