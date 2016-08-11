import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

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
        WebElement vanField = driver.findElement(By.id("PLANBAR_LOCATION_LABEL_DEPARTURE"));
        vanField.sendKeys("Rotterdam Centraal");
        WebElement naarField = driver.findElement(By.id("PLANBAR_LOCATION_LABEL_ARRIVAL"));
        naarField.sendKeys("Amsterdam Centraal");

        WebElement planButton = driver.findElement(By.xpath("//button[@class='rp-Reisplanbalk__submit button button--arrowRight']"));
        planButton.click();

        //Assert

    }

    @After
    public void CleanUp() {
        driver.quit();
    }
}
