package base;

import managers.DriverManager;
import managers.PageObjectManager;
import managers.ZAPManager;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.FailureHandler;
import utilities.PropertiesLoader;

import java.lang.reflect.Method;

public class BaseGui extends BaseTest {
    protected DriverManager driverManager;
    protected FailureHandler failureHandler;
    protected PageObjectManager pages;
    protected ZAPManager zapManager;
    protected PropertiesLoader propertiesLoader;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        propertiesLoader = new PropertiesLoader();
        String zapAddress = propertiesLoader.getZAPAddress();
        String zapPort = propertiesLoader.getZAPPort();
        String zapApiKey = propertiesLoader.getZAPApiKey();
        if (zapAddress != null && zapPort != null && zapApiKey != null) {
            zapManager = new ZAPManager(zapAddress, zapPort, zapApiKey);
        }
        driverManager = new DriverManager();
        failureHandler = new FailureHandler(driverManager.getDriver());
        pages = new PageObjectManager(driverManager.getDriver());
        failureHandler.setUpAndStartScreenRecorder(method.getName());
        log.info(String.format("Starting test: `%s.%s`", this.getClass().getName(), method.getName()));
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result, Method method) {
        failureHandler.stopVideoRecord();
        if (result.getStatus() == ITestResult.FAILURE) {
            failureHandler.takePageSource(method.getName());
            failureHandler.takeScreenshot(method.getName());
            failureHandler.takeBrowserLogs(method.getName());
            failureHandler.encodeVideoToFlv(method.getName());
        }
        failureHandler.removeVideo();
        driverManager.close();
    }

    @AfterClass
    public void afterClass() {
        driverManager.quit();
    }
}
