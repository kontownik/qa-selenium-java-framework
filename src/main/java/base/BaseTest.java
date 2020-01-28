package base;

import base.api.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import utilities.PropertiesLoader;

public class BaseTest {
    protected static final Logger log = LogManager.getLogger(Logger.class.getName());

    @BeforeSuite(alwaysRun = true)
    public void baseSetup() {
        RestAssured.requestSpecification = getRequestSpecification();
    }

    public RequestSpecification getRequestSpecification() {
        return new RequestBuilder().getBasicRequestSpecBuilder()
                .setBaseUri(new PropertiesLoader().getBaseApiUrl())
                .setConfig(RequestBuilder.getBasicRequestConfig())
                .build();
    }

}
