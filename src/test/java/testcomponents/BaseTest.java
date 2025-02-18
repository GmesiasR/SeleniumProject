package testcomponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pageobjects.LandingPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public LandingPage landingPage;
    protected static String systemPath = System.getProperty("user.dir");
    protected static String reportsPath = System.getProperty("user.dir")+"//reports//";
    protected static String screenshotImageExtention = ".png";


    public WebDriver InitializeDriver() throws IOException
    {
        //properties class
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/resources/GlobalData.properties");
        prop.load(fis);

        String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
        //String browserName = prop.getProperty("browser");

        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver();
                driver = new ChromeDriver();

                break;
            case "edge":
                WebDriverManager.edgedriver();
                driver = new EdgeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver();
                driver = new FirefoxDriver();
                break;

            default:
                System.out.println("Día no válido");
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }
1ff7b3152bd2427fbf16087ff01005b8
    public List<HashMap<String, String>> getJsonDataToMap(String filepath) throws IOException {

        //reading json to string
        String jsonContent = FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);

        //String to hashmap Jackson Datbind

        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        return data;

    }

    public String getScreenshoot(String testCaseName,WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir")+"//reports//"+testCaseName+".png");
        FileUtils.copyFile(source, file );
        return reportsPath+testCaseName+screenshotImageExtention;
    }

    @BeforeMethod(alwaysRun = true)
    public  LandingPage launchApplication() throws IOException {
        driver = InitializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }


}
