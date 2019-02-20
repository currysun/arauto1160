package setup.features;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"json:target/setup-cucumber.json", "html:target/site/setup-cucumber"},
        monochrome = true,
        glue = "com.lombardrisk"
)
public class SetupFeaturesRunner {
    // Features Runner for setting up the system
}
