package com.lombardrisk.regressiontests.fortestsuit;

import com.codeborne.selenide.Condition;
import com.lombardrisk.regressiontests.BaseTest;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ForBaseTest extends BaseTest {


    @Test
    public void createApprovalTemplate() {
        mainMenu.openSettings().administration().securitySettings();
        $(By.id("securitySettingsForm")).has(Condition.text("Security Settings"));
        System.out.println("11111111111111111111");
    }

    @Test
    public void openReturn(){
        dashboardPage.selectEntity("0020");
        System.out.println("2222222222222222222");
    }

    @Test
    public void createApprovalTemplate111() {
        //loginFixture.loginAsAdmin();

        mainMenu.openSettings().entitySetup();
        System.out.println("333333333333333333333");
    }
}
