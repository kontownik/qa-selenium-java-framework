package utilities;

import managers.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Set;

public class UtilitiesFunctions {

    private static final Logger log = LogManager.getLogger(Logger.class.getName());

    public static WebElement getWebElement(String xpath) {
        return Context.driverManager.getDriver().findElement(By.xpath(xpath));
    }

    public static void switchToOtherTab() {
        WebDriver driver = Context.driverManager.getDriver();

        try {
            Thread.sleep(2000);
        } catch (java.lang.InterruptedException e) {
            log.error(e);
        }

        String currentTab = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        if (handles.size() < 2) {
            log.error("There is only one tab opened");
        }

        for (String tab : handles) {
            if (!tab.equals(currentTab)) {
                driver.switchTo().window(tab);
            }
        }
    }

}