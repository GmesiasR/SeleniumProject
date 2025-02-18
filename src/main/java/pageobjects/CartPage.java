package pageobjects;

import abstractcomponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends AbstractComponents {

    WebDriver driver;
    public CartPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
    }

    @FindBy(css=".totalRow Button") WebElement checkoutEle;
    @FindBy(css=".cartSection h3") private List<WebElement> productTitles;

    public boolean VerifyProductsDisplay(String productName)
    {
        return  productTitles.stream().anyMatch(product-> product.getText().equalsIgnoreCase(productName));
    }

    public CheckoutPage goToCheckoutPage(){
        checkoutEle.click();
        return new CheckoutPage(driver);
    }

}
