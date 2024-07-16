package rough;

import base.BaseTest;
import com.microsoft.playwright.Browser;
import org.testng.annotations.Test;

public class UserRegistrationTest extends BaseTest {

    @Test
    public void login2() {
        Browser browser = getBrowser("webkit");
        navigate(browser,"http://google.com");
        click("reject");
        type("searchBox", "Hello");
    }

    @Test
    public void gmailLogin2() {
        Browser browser = getBrowser("webkit");
        navigate(browser,"http://gmail.com");
        type("username", "test@gmail.com");
    }
}
