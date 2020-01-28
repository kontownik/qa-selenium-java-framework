package tests.gui;

import base.BaseGui;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaceholderTest2 extends BaseGui {

    @Test
    public void PH2SuccessTest() {
        pages.getHomePage().get();
        Assert.assertTrue(true);
    }

    @Test
    public void PH2FailTest() {
        pages.getHomePage().get();
        Assert.fail("forced fail");
    }
}
