package kasirAja.cucumber.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/kasirAja/cucumber/features",
        glue = "kasirAja.cucumber.step",
        plugin = {"html:target/HTML_report.html"},
        tags = "@Positive"
)

public class runLogin {

}
