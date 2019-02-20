package features;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"json:target/cucumber.json", "html:target/site/cucumber","junit:target/zephyrcucumber.xml"},
        monochrome = true,
        glue = "com.lombardrisk"
)
public class FeaturesRunner {
    // Features Runner
}
