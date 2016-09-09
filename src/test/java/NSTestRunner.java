import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by kayipcheung on 08/09/16.
 */

@RunWith(Cucumber.class)
@CucumberOptions(format = { "json:target/REPORT_NAME.json", "pretty",
        "html:target/HTML_REPORT_NAME" }, features = { "src/test/java/feature/NsDemoCucumber.feature" })
public class NSTestRunner {

}


