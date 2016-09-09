package stepDefinition;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kayipcheung on 08/09/16.
 */
public class NSSteps {
    private AndroidDriver driver;
    private String device = "Test";
    private String deviceName = "emulator-5554";
    private String platformName = "Android";
    private String version = "5.0.2";

    @cucumber.api.java.Before
    public void SetUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("device", device);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("platformVersion", version);
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("chromeoptions", "chromeOptions: {args: ['--disable-translate']}");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Given("^user goes to the NS Site$")
    public void openBrowser() {
        driver.navigate().to("http://www.ns.nl");
    }

    @When("^user clicks the Accept cookies message$")
    public void clickOnAcceptCookies() {
        //Accept Cookies
        driver.switchTo().frame(driver.findElement(By.className("r42CookieBar")));
        WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Accepteer')]"));
        element.click();
    }

    @And("^user is able to enter his from/to destination$")
    public void nsSiteIsActive() {
        boolean isActive = false;
        WebElement vanField = driver.findElement(By.id("PLANBAR_LOCATION_LABEL_DEPARTURE"));
        if (vanField.isDisplayed()) {
            isActive = true;
            System.out.println("vanField is" + isActive);
        } else {
            System.out.println("vanField is" + isActive);
        }
    }

    @And("^user is on the NS site$")
    public void userIsOnNSSite() {
        nsSiteIsActive();
    }

    @And("^user inputs \"Rotterdam Centraal\" in the field$")
    public void fillInFromField() {
        WebElement vanField = driver.findElement(By.id("PLANBAR_LOCATION_LABEL_DEPARTURE"));
        vanField.sendKeys("Rotterdam Centraal");
    }

    @And("^user inputs \"Amsterdam Centraal\" in the field$")
    public void fillInToField() {
        WebElement naarField = driver.findElement(By.id("PLANBAR_LOCATION_LABEL_ARRIVAL"));
        naarField.sendKeys("Amsterdam Centraal");
    }

    @And("^user clicks on \"Plannen\" button$")
    public void clickOnPlannenButton() {
        WebElement planButton = driver.findElement(By.xpath("//*[@id=\"reisplanner-view\"]/div/reisplanbalk/form/div[2]/div[2]/button[2]/span"));
        planButton.click();
    }

    @Then("^in the overview it should display \"€ 15,10\"$")
    public void checkOverviewPremium() {
        //Selecteer de eerste mogelijkheid
        WebElement eersteOptie = driver.findElement(By.xpath("//mogelijkheid[1]/a"));
        eersteOptie.click();
        //Check of het kaartje 15,10 kost
        WebElement detailOverview = driver.findElement(By.xpath("//span[@class='rp-reisdetailsHeaderPrice__priceAmount']"));
        assert (detailOverview.getText().contains("15,10"));
    }

    @cucumber.api.java.After
    public void CleanUp() {
        driver.quit();
    }
}
