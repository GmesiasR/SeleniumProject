package pageobjects;


import abstractcomponents.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCataloguePage extends AbstractComponents {
    WebDriver driver;
    public ProductCataloguePage(WebDriver driver)
    {
        super(driver);
        //initialization
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    //PageFactory
    @FindBy(css=".mb-3") List<WebElement> products;
    @FindBy(css=".ng-animating") WebElement spinner;

    By productsBy = By.cssSelector(".mb-3");
    By addToCart = By.cssSelector(".card-body button:last-of-type");
    By toastMessage = By.cssSelector("#toast-container");

    public List<WebElement> getProductList()
    {
        waitForElementToAppear(productsBy);
        return products;
    }

    public WebElement  getProductByName(String productName)
    {
        return  getProductList().stream().filter(product
                -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
    }

    public void addProductToCart(String productName) throws InterruptedException {
        WebElement prod = getProductByName( productName);
        prod.findElement(addToCart).click();
        waitForElementToAppear(toastMessage);
        //waitForWebElementToDissapear(spinner);
        Thread.sleep(5000);
    }
}
