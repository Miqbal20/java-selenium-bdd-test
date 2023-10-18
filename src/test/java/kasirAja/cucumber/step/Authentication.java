package kasirAja.cucumber.step;

import io.cucumber.java.en.*;
import org.junit.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;


public class Authentication {
    public WebDriver driver;
    public String baseUrl = "https://kasirdemo.belajarqa.com";
    public Integer timeout = 1000;

    public Object[][] loginData;

    public Authentication(){
        // apply chrome driver setup
        WebDriverManager.chromedriver().setup();
        ChromeOptions opt = new ChromeOptions();
        // opt.addArguments("--headless=new");

        driver = new ChromeDriver(opt);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

        loginData = new Object[][] {
                { "miqbal20@mail.com", "miqbal120", "invalid"},
                { "miqbal20@mail.com", "miqbal20", "valid"}
        };
    }

    @Given("User on Login Pages kasir Aja")
    public void userOnLoginPagesKasirAja() {
        this.driver.get(baseUrl);
    }

    @When("User fill valid username and password")
    public void userFillUsernameAndPassword() {
        int rowIndex = 0;
        while (rowIndex < loginData.length) {
            String email = (String) loginData[rowIndex][0];
            String password = (String) loginData[rowIndex][1];
            String status = (String) loginData[rowIndex][2];

            if (status.equals("valid")) {
                // Login
                this.driver.findElement(By.id("email")).sendKeys(email);
                this.driver.findElement(By.id("password")).sendKeys(password);
                break;
            } else {
                rowIndex++;
            }
        }
    }

    @And("User click login button")
    public void clickLoginButton() throws InterruptedException {
        this.driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(timeout);
    }

    @Then("User redirect to dashboard page")
    public void userRedirectToDashboardPage() throws InterruptedException {
        // Assertion
        String PageTitle = this.driver.findElement(By.cssSelector("h3.chakra-heading")).getText();
        String CurrentUrl = this.driver.getCurrentUrl();
        Assert.assertEquals(PageTitle, "kasirAja");
        Assert.assertEquals(CurrentUrl, "https://kasirdemo.belajarqa.com/dashboard");
        Thread.sleep(timeout);

        this.driver.close();
    }


    @When("User fill invalid username and password")
    public void userFillInvalidUsernameAndPassword() {
        int rowIndex = 0;
        while (rowIndex < loginData.length) {
            String email = (String) loginData[rowIndex][0];
            String password = (String) loginData[rowIndex][1];
            String status = (String) loginData[rowIndex][2];

            if (status.equals("invalid")) {
                // Login
                this.driver.findElement(By.id("email")).sendKeys(email);
                this.driver.findElement(By.id("password")).sendKeys(password);
                break;
            } else {
                rowIndex++;
            }
        }

    }

    @Then("User get error message")
    public void userGetErrorMessage() {
        String AlertFailedText = this.driver.findElement(By.cssSelector("div.chakra-alert")).getText();
        String CurrentUrl = this.driver.getCurrentUrl();

        Assert.assertEquals(AlertFailedText, "Kredensial yang Anda berikan salah");
        Assert.assertEquals(CurrentUrl, "https://kasirdemo.belajarqa.com/login");

        this.driver.close();
    }

    @And("User click logout button on Dashboard page")
    public void clickLogoutButton() throws InterruptedException {
        String PageUrl = this.driver.getCurrentUrl();
        Assert.assertEquals(PageUrl, "https://kasirdemo.belajarqa.com/dashboard");
        Thread.sleep(timeout);
        this.driver.findElement(By.id("menu-button-14")).click();
        Thread.sleep(timeout);
        this.driver.findElement(By.id("menu-list-14-menuitem-12")).click();
    }

    @Then("User redirect to login page")
    public void userRedirectToLoginPage() throws InterruptedException {
        // Assertion
        String PageTitle = this.driver.getTitle();
        String CurrentUrl = this.driver.getCurrentUrl();

        Assert.assertEquals(PageTitle, "kasirAja");
        Assert.assertEquals(CurrentUrl, "https://kasirdemo.belajarqa.com/login");

        Thread.sleep(timeout);
        this.driver.close();
    }
}
