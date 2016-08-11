import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class NsDemo {
    private AndroidDriver driver;
    private String device = "Test";
    private String deviceName = "emulator-5554";
    private String platformName = "Android";
    private String version = "5.0.2";

    @Before
    public void SetUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("device", device);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("platformVersion", version);
        capabilities.setCapability("platformName", platformName);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void AcceptCookies() throws MalformedURLException {
        driver.navigate().to("http://www.ns.nl");

        //Accept Cookies
        driver.switchTo().frame(driver.findElement(By.className("r42CookieBar")));
        WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Accepteer')]"));
        element.click();
    }

    @Test
    public void EnterDestinations() throws MalformedURLException {
        AcceptCookies();

        //Plan een reis van Rotterdam Centraal naar Amsterdam Centraal
        WebElement vanField = driver.findElement(By.id("PLANBAR_LOCATION_LABEL_DEPARTURE"));
        vanField.sendKeys("Rotterdam Centraal");
        WebElement naarField = driver.findElement(By.id("PLANBAR_LOCATION_LABEL_ARRIVAL"));
        naarField.sendKeys("Amsterdam Centraal");

        WebElement planButton = driver.findElement(By.xpath("//button[@class='rp-Reisplanbalk__submit button button--arrowRight']"));
        planButton.click();

        //Selecteer de eerste mogelijkheid
        WebElement eersteOptie = driver.findElement(By.xpath("//mogelijkheid[1]/a"));
        eersteOptie.click();

        //Check of het kaartje 15,10 kost
        WebElement detailOverview = driver.findElement(By.xpath("//reisdetails/div[@class='rp-reisdetails']/div[@class='rp-reisdetails__tileWrapper']"));
        assert (detailOverview.getText().contains("15,10"));
    }

    @Test
    public void SlideCarousel() throws MalformedURLException, InterruptedException {
        AcceptCookies();
        Thread.sleep(5000);
        WebElement currentImage = driver.findElement(By.xpath("//li[contains(@class,'is-active')]//source"));

        //Tip touchElements worden alleen ondersteund in NATIVE_APP
        driver.context("NATIVE_APP");
        new TouchAction(driver).longPress(450, 350).moveTo(20, 350).release().perform();
        driver.context("CHROMIUM");
        Thread.sleep(5000);
        WebElement adjustedImage = driver.findElement(By.xpath("//li[contains(@class,'is-active')]//source"));

        //Vergelijk of de plaatjes verschillen
        assert (!currentImage.getAttribute("srcset").equals(adjustedImage.getAttribute("srcset")));
    }

    @After
    public void CleanUp() {
        driver.quit();
    }
}
