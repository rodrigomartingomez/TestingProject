package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.SelectOption;
import extentlisteners.ExtentListeners;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class BaseTest {

    private Playwright playwright;
    public Browser browser;
    public Page page;
    private static Properties OR = new Properties();
    private static FileInputStream fis;
    private Logger log = Logger.getLogger(this.getClass());

    private static ThreadLocal<Playwright> pw = new ThreadLocal<>();
    private static ThreadLocal<Browser> br = new ThreadLocal<>();
    private static ThreadLocal<Page> pg = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return pw.get();
    }

    public static Browser getBrowser() {
        return br.get();
    }

    public static Page getPage() {
        return pg.get();
    }

    @BeforeSuite
    public void setup() {

        PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");
        log.info("TEST EXECUTION STARTED");

        try {
            fis = new FileInputStream("./src/test/resources/properties/OR.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            OR.load(fis);
            log.info("OR PROPERTIES LOADED");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void click(String locatorKey) {
        try {
            getPage().locator(OR.getProperty(locatorKey)).click();
            log.info("clicking the element: " + locatorKey);
            ExtentListeners.getExtent().info("clicking the element: " + locatorKey);
        } catch (Throwable t) {
            log.error("Error when clicking: " + t.getMessage());
            ExtentListeners.getExtent().fail("Error when clicking: " + locatorKey + " error message is: " + t.getMessage());
            Assert.fail(t.getMessage());
        }
    }

    public void type(String locatorKey, String value) {
        try {
            getPage().locator(OR.getProperty(locatorKey)).fill(value);
            log.info("typing in element: " + locatorKey + " the value: " + value);
            ExtentListeners.getExtent().info("typing in element: " + locatorKey + " the value: " + value);
        } catch (Throwable t) {
            log.error("Error when typing in element: " + t.getMessage());
            ExtentListeners.getExtent().fail("Error when typing in element: " + locatorKey + " error message is: " + t.getMessage());
            Assert.fail(t.getMessage());
        }
    }

    public void select(String locatorKey, String value) {
        try {
            getPage().selectOption(OR.getProperty(locatorKey), new SelectOption().setLabel(value));
            log.info("selecting element: " + locatorKey + " the value: " + value);
            ExtentListeners.getExtent().info("selecting element: " + locatorKey + " the value: " + value);
        } catch (Throwable t) {
            log.error("Error when selecting element: " + t.getMessage());
            ExtentListeners.getExtent().fail("Error when selecting element: " + locatorKey + " error message is: " + t.getMessage());
            Assert.fail(t.getMessage());
        }
    }

    public boolean isElementPresent(String locatorKey) {

        try {
        getPage().waitForSelector(OR.getProperty(locatorKey), new Page.WaitForSelectorOptions().setTimeout(2000));
        log.info("Finding the element: " + locatorKey);
        ExtentListeners.getExtent().info("Finding the element: " + locatorKey);
        return true;
        } catch (Throwable t) {
            ExtentListeners.getExtent().fail("Error when finding: " + locatorKey);
            return false;
        }

    }

    public Browser getBrowser(String browserName) {

        playwright = Playwright.create();
        pw.set(playwright);

        switch (browserName) {
            case "chrome":
                log.info("LAUNCHING CHROME");
                return getPlaywright().chromium().launch(
                        new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
            case "headless":
                log.info("LAUNCHING HEADLESS");
                return getPlaywright().chromium().launch(
                        new BrowserType.LaunchOptions().setHeadless(true));
            case "firefox":
                log.info("LAUNCHING FIREFOX");
                return getPlaywright().firefox().launch(
                        new BrowserType.LaunchOptions().setChannel("firefox").setHeadless(false));
            case "webkit":
                log.info("LAUNCHING WEBKIT");
                return getPlaywright().webkit().launch(
                        new BrowserType.LaunchOptions().setHeadless(false));
            default:
                throw new IllegalArgumentException();
        }
    }

    public void navigate(Browser browser, String url) {

        this.browser = browser;
        br.set(browser);
        page = getBrowser().newPage();
        pg.set(page);
        page.navigate(url);
        log.info("Navigated to: " + url);

        getPage().onDialog(dialog -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dialog.accept();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(dialog.message());
        });

    }

    @AfterMethod
    public void quit() {
        if (getPage() != null) {
            getBrowser().close();
            getPage().close();
         //   getPlaywright().close();

        }
    }

    @AfterSuite
    public void quitPlaywright() {
        if (getPage() != null) {
            getPlaywright().close();
        }
    }
}
