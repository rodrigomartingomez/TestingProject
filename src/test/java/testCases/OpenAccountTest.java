package testCases;

import base.BaseTest;
import org.testng.annotations.Test;
import utilities.Constants;
import utilities.DataProviders;
import utilities.DataUtil;
import utilities.ExcelReader;

import java.util.Hashtable;

public class OpenAccountTest extends BaseTest {

    @Test(dataProviderClass = DataProviders.class, dataProvider = "bankManagerDP")
    public void openAccountTest(Hashtable<String, String> data) {

        ExcelReader excel = new ExcelReader(Constants.SUITE1_XL_PATH);
        DataUtil.checkExecution("BankManagerSuite", "OpenAccountTest", data.get("Runmode"), excel) ;
        browser = getBrowser(data.get("browser"));
        navigate(browser, Constants.URL);

        click("bmlButton_CSS");
        click("openAccount_CSS");
        select("customer_CSS", data.get("customer"));
        select("currency_CSS", data.get("currency"));
        click("process_CSS");



    }


}
