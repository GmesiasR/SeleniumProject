package tests;


import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageobjects.CartPage;
import pageobjects.ProductCataloguePage;
import testcomponents.BaseTest;
import testcomponents.Retry;

import java.io.IOException;
import java.util.List;


public class ErrorValidationTest extends BaseTest {

    @Test(groups = {"ErrorHandling"},retryAnalyzer=Retry.class)
    public void LoginErrorValidation () throws InterruptedException, IOException
    {
        String productName = "IPHONE 13 PRO";
        landingPage.loginApplication("anshika@gmail.com", "Iamkig@000");
        Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
    }

    @Test(groups = {"ErrorHandling"},retryAnalyzer=Retry.class)
    public void ProductErrorValidation() throws InterruptedException, IOException
     {
        String productName = "IPHONE 13 PRO";

        ProductCataloguePage productCataloguePage = landingPage.loginApplication("anshika@gmail.com", "Iamking@000");

        List<WebElement> products = productCataloguePage.getProductList();
        productCataloguePage.addProductToCart(productName);
        CartPage cartPage = productCataloguePage.goToCartPage();
        Boolean match = cartPage.VerifyProductsDisplay(productName);
        try {Assert.assertFalse(match);} catch (Exception e){e.printStackTrace();}
    }
}
