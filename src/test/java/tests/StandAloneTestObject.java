package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.*;
import testcomponents.BaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class StandAloneTestObject extends BaseTest {
    String productName = "IPHONE 13 PRO";

    @Test(dataProvider = "getData",groups = {"Purchase"})
    public void SubmitOrder(String email, String password, String productName) throws InterruptedException, IOException { // instead it could be like this (String email,String password, String product)
        ProductCataloguePage productCataloguePage = landingPage.loginApplication(email,  password); //loginApplication(email,password)
        List<WebElement> products = productCataloguePage.getProductList();
        productCataloguePage.addProductToCart(productName);
        CartPage cartPage = productCataloguePage.goToCartPage();
        Boolean match = cartPage.VerifyProductsDisplay(productName);
        Assert.assertTrue(match);
        CheckoutPage checkoutpage = cartPage.goToCheckoutPage();
        checkoutpage.selectCountry("Ind");
        ConfirmationPage confimationPage = checkoutpage.submitOrder();
        String confirmationMessage = confimationPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }

    @Test(dependsOnMethods = {"SubmitOrder"})
    public void OrderHistoryTest()
    {
        ProductCataloguePage productCataloguePage = landingPage.loginApplication("anshika@gmail.com","Iamking@000");
        OrderPage orderPage = productCataloguePage.goToOrdersPage();
        Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
    }



    @DataProvider
    public Object[][] getData() throws IOException {

        return new Object[][] {{"shetty@gmail.com","Iamking@000","IPHONE 13 PRO"}, {"anshika@gmail.com","Iamking@000","IPHONE 13 PRO"}};
    }

}
