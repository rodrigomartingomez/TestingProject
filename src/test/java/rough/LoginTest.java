package rough;

import base.BaseTest;
import com.microsoft.playwright.Browser;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void login() {
        Browser browser = getBrowser("chrome");
        navigate(browser,"http://google.com");
        click("reject");
        type("searchBox", "Hello");
    }

    @Test
    public void gmailLogin() {
        Browser browser = getBrowser("chrome");
        navigate(browser,"http://gmail.com");
        type("username", "test@gmail.com");
    }
}
