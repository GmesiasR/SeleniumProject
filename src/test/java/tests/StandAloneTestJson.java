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

public class StandAloneTestJson extends BaseTest {
    String productName = "IPHONE 13 PRO";

    @Test(dataProvider = "getData", groups = {"Purchase"})
    public void submitOrder(HashMap<String, String> input) throws InterruptedException { // instead it could be like this (String email,String password, String product)
        ProductCataloguePage productCataloguePage = landingPage.loginApplication(input.get("email"), input.get("password")); //loginApplication(email,password)
        List<WebElement> products = productCataloguePage.getProductList();
        productCataloguePage.addProductToCart(input.get("product"));
        CartPage cartPage = productCataloguePage.goToCartPage();
        Boolean match = cartPage.VerifyProductsDisplay(input.get("product"));
        Assert.assertTrue(match);
        CheckoutPage checkoutpage = cartPage.goToCheckoutPage();
        checkoutpage.selectCountry("Ind");
        ConfirmationPage confimationPage = checkoutpage.submitOrder();
        String confirmationMessage = confimationPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }

    @Test(dependsOnMethods = {"submitOrder"})
    public void OrderHistoryTest() {
        ProductCataloguePage productCataloguePage = landingPage.loginApplication("anshika@gmail.com", "Iamking@000");
        OrderPage orderPage = productCataloguePage.goToOrdersPage();
        Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
    }


    //Extent Reports


    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "/src/test/java/data/PurchaseOrder.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }

}
