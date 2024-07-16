package testCases;

import base.BaseTest;
import com.microsoft.playwright.Browser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BankManagerLoginTest extends BaseTest {

    @Test
    public void loginAsBankManager() {

        Browser browser = getBrowser("chrome");
        navigate(browser,"https://www.way2automation.com/angularjs-protractor/banking/#/login");
        click("bmlButton_CSS");
        Assert.assertTrue(isElementPresent("addCustomerButton_CSS"), "Bank Manager not logged in");


    }

}
