package fcr;

import com.codeborne.selenide.Condition;
import com.lombardrisk.config.BeanConfig;
import com.lombardrisk.config.Config;
import com.lombardrisk.config.StepDef;
import com.lombardrisk.config.driver.ChromeHeadlessDownloadEnabler;
import com.lombardrisk.fixtures.Admin.CalcEngineAndBindingFixture;
import com.lombardrisk.fixtures.Admin.EntityConfigFixture;
import com.lombardrisk.fixtures.Admin.UsersAndGroupsFixture;
import com.lombardrisk.fixtures.Dashboard.CreateReturnFixture;
import com.lombardrisk.fixtures.LoginFixture;
import com.lombardrisk.fixtures.ReturnInstance.ReturnInstanceFixture;
import com.lombardrisk.page.LoginPage;
import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.administration.CalculationEnginesPage;
import com.lombardrisk.page.administration.ConfigPackageBindingPage;
import com.lombardrisk.page.administration.UserGroupsPage;
import com.lombardrisk.page.administration.UsersPage;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.page.dashboard.RetrieveReturnDialog;
import com.lombardrisk.page.entitysetup.EntitySetupDialog;
import com.lombardrisk.page.entitysetup.EntitySetupPage;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.JobManagerPage;
import com.lombardrisk.page.header.MainMenu;
import com.lombardrisk.page.returninstance.ReturnInstancePage;
import com.lombardrisk.stepdef.transformer.ToLocalDate;
import fcr.fcrpages.FCRHomePage;
import fcr.fcrpages.FCRLoginPage;
import fcr.fcrpages.FCRStagingJobDialog;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BeanConfig.class})
public class FCRTests extends StepDef {


    @Autowired
    private FCRHomePage fcrHomePage;
    @Autowired
    private FCRLoginPage fcrLoginPage;
    @Autowired
    private FCRStagingJobDialog fcrSetJobDialog;

    @Autowired
    private LoginPage loginPage;
    @Autowired
    private UsersPage usersPage;
    @Autowired
    private UserGroupsPage userGroupsPage;
    @Autowired
    private DashboardPage dashboardPage;
    @Autowired
    private Notification notification;
    @Autowired
    private MainMenu mainMenu;
    @Autowired
    private EntitySetupPage entitySetupPage;
    @Autowired
    private EntitySetupDialog entitySetUpDialog;
    @Autowired
    private CalculationEnginesPage calculationsEnginePage;
    @Autowired
    private ConfigPackageBindingPage configPackageBindingPage;
    @Autowired
    private RetrieveReturnDialog retrieveReturnDiag;
    @Autowired
    private JobManagerPage jobManagerPage;
    @Autowired
    private ReturnInstancePage returnInstancePage;
    @Autowired
    private ToLocalDate toLocalDate;
    @Autowired
    private ChromeHeadlessDownloadEnabler chromeHeadlessDownloadEnabler;

    private LoginFixture loginFixture;
    private UsersAndGroupsFixture usersAndGroupsFixture;
    private EntityConfigFixture entityConfigFixture;
    private CalcEngineAndBindingFixture calcEngineAndBindingFixture;
    private CreateReturnFixture createReturnFixture;
    private ReturnInstanceFixture returnInstanceFixture;
    private Config config;

    @Before
    public void setUp() {
        loginFixture = new LoginFixture(loginPage, dashboardPage, notification, mainMenu);
        usersAndGroupsFixture = new UsersAndGroupsFixture(usersPage, userGroupsPage);
        entityConfigFixture = new EntityConfigFixture(entitySetupPage, entitySetUpDialog);
        calcEngineAndBindingFixture = new CalcEngineAndBindingFixture(calculationsEnginePage, configPackageBindingPage);
        createReturnFixture = new CreateReturnFixture(dashboardPage, retrieveReturnDiag);
        returnInstanceFixture = new ReturnInstanceFixture(dashboardPage, returnInstancePage);
    }

    @Test
    public void fcrIntegrationWithAR() {
        /*This test requires FED product package ARforFED_v1.12.2.6 and config package FCR_REG_REP_USFED_80510 */
        String jobName = "DEADJ001" + RandomStringUtils.randomAlphanumeric(3);
        String fileName = "DEADJ001_FRY14CIL_FCR_060618.csv";
        String bindingName = "DEADJ001";
        Boolean header = true;
        String entityCode = "FCR";
        LocalDate referenceDate = LocalDate.of(2018, Month.JUNE, 6);

        String product = "FED";
        String returnForm = "FRY14QCIL";
        int version = 1;
        String userName = "fcruser";
        String userGroup = entityCode;
        String calcEngine = "FCR_USFED";
        String configName = "FED";
        String configPrefix = "FCR_REG_REP_USFED_80511";
        String calcType = "FCR Engine";
        String fcrURL = "https://i-08bf7fed60e664856.internal.aws.lombardrisk.com:8443/fcrengine/";

        chromeHeadlessDownloadEnabler.enableDownloads();

        runFCRjob(jobName, fileName, bindingName, header, entityCode, referenceDate);
        FCR_Setup_Entity_And_Returns(product, returnForm, version, userName, userGroup, entityCode);
        FCR_Assign_Binding(calcEngine, configName, configPrefix, calcType, fcrURL, product, returnForm, version);
        FCR_Retrieve(referenceDate, product, entityCode, returnForm, version);
    }

    public void runFCRjob(
            final String jobName,
            final String fileName,
            final String bindingName,
            boolean header,
            final String entityCode,
            final LocalDate referenceDate) {
        fcrLoginPage.loginWithAdmin();
        fcrHomePage.jobsDropDown.shouldBe(Condition.visible).click();
        fcrHomePage.addJobButton.shouldBe(Condition.visible).click();
        fcrSetJobDialog.setJob(jobName, fileName, bindingName, bindingName, header, entityCode, referenceDate);
        fcrHomePage.jobStatus(jobName, bindingName);
    }

    public void FCR_Setup_Entity_And_Returns(
            final String product,
            final String returnForm,
            final int version,
            final String userName,
            final String userGroup,
            final String entityCode) {

        System.out.println(">>>>>>>>>>>>>>>> FCRRetrieveSetup >>>>>>>>>>>>>>>>");

        List<String> privileges = Arrays.asList("Return Maker", "Return Viewer");

        loginFixture.loginAsAdmin();

        usersPage.open();
        usersAndGroupsFixture.addUser(userName);
        usersAndGroupsFixture.addUserToGroup(
                userGroup,
                userGroup,
                userName);
        entityConfigFixture.assignReturnAndConfigureEntity(
                entityCode,
                ProductPackage.valueOf(product),
                returnForm,
                version,
                userGroup,
                privileges,
                "Solo", null);

        System.out.println("<<<<<<<<<<<<<<<< FCRRetrieveSetup <<<<<<<<<<<<<<<<");
    }

    public void FCR_Assign_Binding(
            final String calcEngine,
            final String configName,
            final String configPrefix,
            final String calcType,
            final String fcrURL,
            final String product,
            final String returnForm,
            final int version) {
        System.out.println(">>>>>>>>>>>>>>>> FCRFlowCalcAndBinding >>>>>>>>>>>>>>>>");

        loginFixture.loginAsAdmin();

        calcEngineAndBindingFixture
                .createCalculationEngine(
                        calcType,
                        calcEngine, fcrURL)
                .assignBinding(
                        calcEngine,
                        configName,
                        configPrefix, product)
                .assignReturnToSource(product,calcEngine,returnForm,version);
        System.out.println("<<<<<<<<<<<<<<<< FCRFlowCalcAndBinding <<<<<<<<<<<<<<<<");
    }

    public void FCR_Retrieve(
            final LocalDate referenceDate,
            final String product,
            final String entityCode,
            final String returnForm,
            final int version) {
        System.out.println(">>>>>>>>>>>>>>>> FCRFlowRetrieve >>>>>>>>>>>>>>>>");
        Map<String, Object> jobsRun = new HashMap<>();
        loginFixture
                .loginAsAdmin();

        createReturnFixture
                .filterReturns(product, entityCode, returnForm,version)
                .retrieveReturn(
                        entityCode,
                        referenceDate,
                        version,
                        returnForm,
                        returnForm + " v" + version);
        jobManagerPage
                .open();

        String jobName = product
                + "|"
                + entityCode
                + "|"
                + returnForm
                + "|"
                + version;

        String status = jobManagerPage.retrieveStatus(jobName);
        jobsRun.put(jobName, status);
        System.out.println("Job named: " + jobName + " completed with status: " + status);
        jobManagerPage.backToDashboardButton();

        System.out.println("[FCR Retrieve Report Status] " + jobsRun);
        System.out.println("<<<<<<<<<<<<<<<< FCRFlowRetrieve <<<<<<<<<<<<<<<<");
    }
}