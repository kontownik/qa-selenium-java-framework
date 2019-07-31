package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import utilities.PropertiesLoader;

public abstract class BasePage extends LoadableComponent<BasePage> {
    protected static final Logger log = LogManager.getLogger(Logger.class.getName());
    private static String baseUrl;
    protected String relativeUrl;
    protected PropertiesLoader propertiesLoader;
    protected WebDriver driver;
    private JavascriptExecutor js;
    private By.ByXPath passwordInput = new By.ByXPath("//input[@type='password']");
    private By.ByXPath submitButton = new By.ByXPath("//button[@type='submit']");

    public BasePage(WebDriver driver, String relativeUrl) {
        propertiesLoader = new PropertiesLoader();
        baseUrl = propertiesLoader.getBaseUrl();
        this.relativeUrl = validateAndFormatRelativeUrl(relativeUrl);
        this.driver = driver;

        js = (JavascriptExecutor) driver;

        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver)), this);

        log.debug(getClass().getName() + " -> Initializing elements");
    }

    public BasePage(WebDriver driver) {
        this(driver, "");
    }

    @Override
    public BasePage get() {
        log.info("Navigating to: " + getClass().getName());
        return super.get();
    }

    public String getUrl() {
        return baseUrl + relativeUrl;
    }

    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        log.info("Scrolled to bottom of the page");
    }

    public void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0)");
        log.info("Scrolled to top of the page");
    }

    public void scrollTo(int pointX, int pointY) {
        js.executeScript(String.format("window.scrollTo(%d, %d)", pointX, pointY));
        log.info(String.format("Scrolled to point (%d, %d) on the page", pointX, pointY));
    }

    public void loginIntoStaging() {
        String password = propertiesLoader.getStagingPassword();
        if (password == null) {
            log.warn("Trying to login into staging without password");
            return;
        }
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(submitButton).click();
        log.info("Logged in into staging");
    }

    private String validateAndFormatRelativeUrl(String relativeUrl) {
        if (relativeUrl.contains("\\")) {
            // Relative url: "relativeUrl" in class "className" contains "\" instead of "/". Make sure it's valid!
            log.warn("Relative url: \"" + relativeUrl + "\" in class \"" + getClass().getName() +
                    "\" contains \"\\\" instead of \"/\". Make sure it's valid!");
            relativeUrl.replace("\\", "/");
        }
        if (!relativeUrl.startsWith("/")) {
            // Relative url: "relativeUrl" in class "className" does not start with "/". Make sure it's valid!
            log.warn("Relative url: \"" + relativeUrl + "\" in class \"" + getClass().getName() +
                    "\" does not start with \"/\". Make sure it's valid!");
            relativeUrl = "/" + relativeUrl;
        }
        if (relativeUrl.endsWith("/")) {
            log.warn("Relative url: \"" + relativeUrl + "\" in class \"" + getClass().getName() +
                    "ends with a slash. Make sure it's valid!");
            relativeUrl = relativeUrl.substring(0, relativeUrl.length() - 1);
        }

        return relativeUrl;
    }
}
