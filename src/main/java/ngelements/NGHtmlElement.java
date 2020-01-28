package ngelements;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

class NGHtmlElement extends HtmlElement {

    protected static final Logger log = LogManager.getLogger(Logger.class.getName());

    public void waitUntilIsVisible(Integer secondsForTimeout, WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, secondsForTimeout);
        webDriverWait.until(ExpectedConditions.visibilityOf(getWrappedElement()));
    }

    public void waitUntilIsClickable(Integer secondsForTimeout, WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, secondsForTimeout);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(getWrappedElement()));
    }

    public void waitUntilIsNotVisible(Integer secondsForTimeout, WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, secondsForTimeout);
        webDriverWait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(getWrappedElement())));
    }

    public void waitUntilIsNotClickable(Integer secondsForTimeout, WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, secondsForTimeout);
        webDriverWait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(getWrappedElement())));
    }

    @Override
    public void click() {
        this.getWrappedElement().click();
        log.info(String.format("%s was clicked", getName()));
    }
}
