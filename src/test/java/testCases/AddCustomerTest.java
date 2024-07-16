package testCases;

import base.BaseTest;
import com.microsoft.playwright.Browser;

import org.testng.annotations.Test;
import utilities.Constants;
import utilities.DataProviders;
import utilities.DataUtil;
import utilities.ExcelReader;

import java.util.Hashtable;

public class AddCustomerTest extends BaseTest {


    @Test(dataProviderClass = DataProviders.class, dataProvider = "bankManagerDP")
    public void addCustomerTest(Hashtable <String, String> data) {

        ExcelReader excel = new ExcelReader(Constants.SUITE1_XL_PATH);
        DataUtil.checkExecution("BankManagerSuite", "AddCustomerTest", data.get("Runmode"), excel) ;
        browser = getBrowser(data.get("browser"));
        navigate(browser, Constants.URL);
        click("bmlButton_CSS");
        click("addCustomerButton_CSS");
        type("firstName_CSS", data.get("firstname"));
        type("lastNme_XPATH", data.get("lastname"));
        type("postCode_CSS", data.get("postcode"));
        click("addButton_CSS");

    }
}
