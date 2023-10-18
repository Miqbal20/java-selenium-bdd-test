package kasirAja;

import com.opencsv.exceptions.CsvValidationException;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

import com.opencsv.CSVReader;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Authentication {
    public WebDriver driver;
    public String baseUrl = "https://kasirdemo.belajarqa.com";
    public Integer timeout = 1000;

    public Object[][] loginData;

    public Authentication(){
        // apply chrome driver setup
        WebDriverManager.chromedriver().setup();
        ChromeOptions opt = new ChromeOptions();
//        opt.addArguments("--headless=new");

        driver = new ChromeDriver(opt);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

        loginData = new Object[][] {
                { "miqbal20@mail.com", "miqbal120", "invalid"},
                { "miqbal20@mail.com", "miqbal20", "valid"}

        };
    }

    @Test()
    public void TC001_login_success_case() throws InterruptedException {
        // Open Url
        WebDriver driverTest = this.driver;
        driverTest.get(baseUrl);

        int rowIndex = 0;
        while (rowIndex < loginData.length) {
            String email = (String) loginData[rowIndex][0];
            String password = (String) loginData[rowIndex][1];
            String status = (String) loginData[rowIndex][2];

            if (status.equals("valid")) {
                // Login
                driverTest.findElement(By.id("email")).sendKeys(email);
                driverTest.findElement(By.id("password")).sendKeys(password);
                driverTest.findElement(By.xpath("//button[@type='submit']")).click();

                // Assertion
                String PageTitle = driverTest.findElement(By.cssSelector("h3.chakra-heading")).getText();
                String CurrentUrl = driverTest.getCurrentUrl();
                Assert.assertEquals(PageTitle, "kasirAja");
                Assert.assertEquals(CurrentUrl, "https://kasirdemo.belajarqa.com/dashboard");
                break;
            } else {
                rowIndex++;
            }
        }

        Thread.sleep(timeout);
        driverTest.close();
    }

    @Test
    public void TC002_login_failed_case() throws InterruptedException {
        // Open Url
        WebDriver driverTest = this.driver;
        driverTest.get(baseUrl);

        int rowIndex = 0;
        while (rowIndex < loginData.length) {
            String email = (String) loginData[rowIndex][0];
            String password = (String) loginData[rowIndex][1];
            String status = (String) loginData[rowIndex][2];

            if (status.equals("invalid")) {
                // Login
                driverTest.findElement(By.id("email")).sendKeys(email);
                driverTest.findElement(By.id("password")).sendKeys(password);
                driverTest.findElement(By.xpath("//button[@type='submit']")).click();

                // Assertion
                String AlertFailedText = driverTest.findElement(By.cssSelector("div.chakra-alert")).getText();
                String CurrentUrl = driverTest.getCurrentUrl();

                Assert.assertEquals(AlertFailedText, "Kredensial yang Anda berikan salah");
                Assert.assertEquals(CurrentUrl, "https://kasirdemo.belajarqa.com/login");
                break;
            } else {
                rowIndex++;
            }
        }

        Thread.sleep(timeout);
        driverTest.close();
    }

    @Test
    public void TC003_login_ddt_failed() throws InterruptedException {
        // Open Url
        WebDriver driverTest = this.driver;
        driverTest.get(baseUrl);

        String csvDir = System.getProperty("user.dir")+"/src/test/data/test-data.csv";

        try {
            CSVReader reader = new CSVReader(new FileReader(csvDir));
            String[] nextline;
            while ((nextline = reader.readNext()) != null){
                    String email = nextline[0];
                    String password = nextline[1];
                    String status = nextline[2];

                    if(status.equals("failed")){
                        driverTest.findElement(By.id("email")).sendKeys(email);
                        driverTest.findElement(By.id("password")).sendKeys(password);
                        driverTest.findElement(By.xpath("//button[@type='submit']")).click();
                    }
            }

        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }

        Thread.sleep(timeout);
        driverTest.close();
    }

    @Test
    public void TC004_login_ddt_success() throws InterruptedException {
        // Open Url
        WebDriver driverTest = this.driver;
        driverTest.get(baseUrl);

        String csvDir = System.getProperty("user.dir")+"/src/test/data/test-data.csv";

        try {
            CSVReader reader = new CSVReader(new FileReader(csvDir));
            String[] nextline;
            while ((nextline = reader.readNext()) != null){
                String email = nextline[0];
                String password = nextline[1];
                String status = nextline[2];

                if(status.equals("success")){
                    driverTest.findElement(By.id("email")).sendKeys(email);
                    driverTest.findElement(By.id("password")).sendKeys(password);
                    driverTest.findElement(By.xpath("//button[@type='submit']")).click();
                }
            }

        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }

        Thread.sleep(timeout);
        driverTest.close();
    }

    @Test
    public void TC005_logout_success_case() throws InterruptedException {
        // Open Url
        WebDriver driverTest = this.driver;
        driverTest.get(baseUrl);

        // Login
        driverTest.findElement(By.id("email")).sendKeys("miqbal20@mail.com");
        driverTest.findElement(By.id("password")).sendKeys("miqbal20");
        driverTest.findElement(By.xpath("//button[@type='submit']")).click();

        Thread.sleep(timeout);
        driverTest.findElement(By.id("menu-button-14")).click();
        Thread.sleep(timeout);
        driverTest.findElement(By.id("menu-list-14-menuitem-12")).click();

        // Assertion
        String PageTitle = driverTest.getTitle();
        String CurrentUrl = driverTest.getCurrentUrl();

        Assert.assertEquals(PageTitle, "kasirAja");
        Assert.assertEquals(CurrentUrl, "https://kasirdemo.belajarqa.com/login");

        Thread.sleep(timeout);
        driverTest.close();
    }
}
