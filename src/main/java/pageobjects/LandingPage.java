package pageobjects;


import abstractcomponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponents {
    WebDriver driver;
    public LandingPage(WebDriver driver)
    {

        //initialization
        super(driver);
        this.driver=driver; //this brings the driver from the test
        PageFactory.initElements(driver,this);

    }

    //PageFactory
    @FindBy(id="userEmail") WebElement userEmail;

    @FindBy(id="userPassword") WebElement userPassword;

    @FindBy(id="login") WebElement submit;

    @FindBy(css="[class*='flyInOut']") WebElement errorMessage;

    public ProductCataloguePage loginApplication(String email, String password)
    {
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        submit.click();
        return new ProductCataloguePage(driver);
    }

    public String getErrorMessage()
    {
        waitForWebElementToAppear(errorMessage);
        return errorMessage.getText();
    }

    public void goTo()
    {
        driver.get("https://rahulshettyacademy.com/client");
    }
}
