package com.lombardrisk.regressiontests;

import com.lombardrisk.config.BeanConfig;
import com.lombardrisk.config.Config;
import com.lombardrisk.config.StepDef;
import com.lombardrisk.fixtures.LoginFixture;
import com.lombardrisk.fixtures.Workflow.ApprovalWorkflowFixture;
import com.lombardrisk.fixtures.Workflow.WorkflowTemplateFixture;
import com.lombardrisk.page.LoginPage;
import com.lombardrisk.page.administration.ApprovalTemplatePage;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.page.entitysetup.EntitySetupPage;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.MainMenu;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

// 配置Spring中的测试环境
@RunWith(SpringJUnit4ClassRunner.class)
// 指定Spring的配置文件路径
@ContextConfiguration(classes = {BeanConfig.class})
public class BaseTest extends StepDef {

    @Autowired
    protected LoginPage loginPage;
    @Autowired
    protected DashboardPage dashboardPage;
    @Autowired
    protected Notification notification;
    @Autowired
    protected MainMenu mainMenu;
    @Autowired
    protected EntitySetupPage entitySetupPage;
    @Autowired
    protected ApprovalTemplatePage approvalTemplatePage;

    @Autowired
    protected Config config;

    protected LoginFixture loginFixture;
    protected WorkflowTemplateFixture workflowTemplateFixture;
    protected ApprovalWorkflowFixture approvalWorkflowFixture;

    @Before
    public void setUp() {
        //loginFixture = new LoginFixture(loginPage, dashboardPage, notification, mainMenu);
        //String url = config.arFullUrl() + "/core/page/login.xhtml";
        //LoginPage loginPage = open("https://172.20.31.41:8443/agilereporter/core/page/login.xhtml", LoginPage.class);
        //LoginPage loginPage = Selenide.open("https://172.20.31.41:8443/agilereporter/core/page/login.xhtml", LoginPage.class);
        loginPage.open().login("admin","password");
        System.out.println("-----------------------------------");
//        workflowTemplateFixture = new WorkflowTemplateFixture(approvalTemplatePage);
//        approvalWorkflowFixture = new ApprovalWorkflowFixture(entitySetupPage);
    }

    @After
    public void close(){
        closeWebDriver();

    }



}
