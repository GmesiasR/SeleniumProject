package pageobjects;

import abstractcomponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class OrderPage extends AbstractComponents {

    WebDriver driver;
    public OrderPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
    }

    @FindBy(css="tr td:nth-child(3)") private List<WebElement> producNames; //

    public boolean VerifyOrderDisplay(String productName)
    {
        return  producNames.stream().anyMatch(product-> product.getText().equalsIgnoreCase(productName));
    }



}
