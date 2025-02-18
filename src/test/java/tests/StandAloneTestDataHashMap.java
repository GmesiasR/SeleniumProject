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

public class StandAloneTestDataHashMap extends BaseTest {
    String productName = "IPHONE 13 PRO";

    @Test(dataProvider = "getData",groups = {"Purchase"})
    public void submitOrder(HashMap<String,String> input) throws InterruptedException, IOException { // instead it could be like this (String email,String password, String product)
        ProductCataloguePage productCataloguePage = landingPage.loginApplication( input.get("email"),  input.get("password")); //loginApplication(email,password)
        List<WebElement> products = productCataloguePage.getProductList();
        productCataloguePage.addProductToCart(input.get("productName"));
        CartPage cartPage = productCataloguePage.goToCartPage();
        Boolean match = cartPage.VerifyProductsDisplay(input.get("productName"));
        Assert.assertTrue(match);
        CheckoutPage checkoutpage = cartPage.goToCheckoutPage();
        checkoutpage.selectCountry("Ind");
        ConfirmationPage confimationPage = checkoutpage.submitOrder();
        String confirmationMessage = confimationPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }

    @Test(dependsOnMethods = {"submitOrder"})
    public void OrderHistoryTest()
    {
        ProductCataloguePage productCataloguePage = landingPage.loginApplication("anshika@gmail.com","Iamking@000");
        OrderPage orderPage = productCataloguePage.goToOrdersPage();
        Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
    }

    @DataProvider
    public Object[][] getData()
    {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("email","anshika@gmail.com");
        map.put("password","Iamking@000");
        map.put("productName","IPHONE 13 PRO");

        HashMap<String,String> map1 = new HashMap<String,String>();
        map1.put("email","shetty@gmail.com");
        map1.put("password","Iamking@000");
        map1.put("productName","IPHONE 13 PRO");


        return new Object[][] {{map}, {map1}};
    }

}
