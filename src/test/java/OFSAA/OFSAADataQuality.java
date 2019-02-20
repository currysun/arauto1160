package OFSAA;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lombardrisk.config.BeanConfig;
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
import OFSAA.ofsaa.OFSAADataSummary;
import OFSAA.ofsaa.OFSAAReport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test class for checking the quality of OFSAAdata backups provided by OFSAA.
 * The class asserts that all the returns we require are still in the backup
 * and highlights which returns have not be implemented in the test framework.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BeanConfig.class})
public class OFSAADataQuality extends StepDef {

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

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String product;
    private String configName;
    private String calcEngine;
    private Map<String, Object> jobsRun;

    private static String OFFSA_CONTROL_FILE = "src/test/java/OFSAA/OFSAAdata/OFSAAparameters.json";

    public OFSAADataQuality() throws IOException {
    }

    String ofsaaControlFile;
    String ofsaaOutputFolder;

    OFSAADataSummary ofsaaDataSummary;

    @Before
    public void setUp() {
        loginFixture = new LoginFixture(loginPage, dashboardPage, notification, mainMenu);
        usersAndGroupsFixture = new UsersAndGroupsFixture(usersPage, userGroupsPage);
        entityConfigFixture = new EntityConfigFixture(entitySetupPage, entitySetUpDialog);
        calcEngineAndBindingFixture = new CalcEngineAndBindingFixture(calculationsEnginePage, configPackageBindingPage);
        createReturnFixture = new CreateReturnFixture(dashboardPage, retrieveReturnDiag);
        returnInstanceFixture = new ReturnInstanceFixture(dashboardPage, returnInstancePage);

        ofsaaControlFile = System.getProperty("OFSAAControlFile");
        if (ofsaaControlFile == null || ofsaaControlFile.length() == 0) {
            ofsaaControlFile = OFFSA_CONTROL_FILE;
        }

        System.out.println("Using Control File:" + ofsaaControlFile);

        try {
            ofsaaDataSummary = objectMapper.readValue(
                    new File(ofsaaControlFile),
                    OFSAADataSummary.class);
        } catch (IOException e) {
            System.out.println(("IO Exception reading control file:" + e.getMessage()));
        }

        System.out.println("Control File:" + ofsaaDataSummary);

        if (ofsaaDataSummary.getConfigPackageName().contains("USFED")) {
            calcEngine = "OFSAA_USFED";
            configName = "FED";
            product = configName;
        }
        if (ofsaaDataSummary.getConfigPackageName().contains("EBA")) {
            calcEngine = "OFSAA_EBA";

            // TODO temporary test
            configName = "ECR";
            product = configName;
        }
        if (ofsaaDataSummary.getConfigPackageName().contains("RBI")) {
            calcEngine = "OFSAA_RBI";
            configName = "RBI";
            product = configName;
        }
        if (ofsaaDataSummary.getConfigPackageName().contains("TIC")) {
            calcEngine = "OFSAA_USTRE";
            configName = "TIC";
            product = configName;
        }

        System.out.println("Using calcEngine:" + calcEngine);
        System.out.println("Using configName:" + configName);
        System.out.println("Using product:" + product);
    }

    @Test
    public void OFSAAFlow() {

        System.out.println(">>>>>>>>>>>>>>>> OFSAAFlow >>>>>>>>>>>>>>>>");
        OFSAA1_Setup_Entity_And_Returns();
        OFSAA2_Assign_Binding();
        OFSAA3_Retrieve();
        OFSAA4_Export_To_CSV();
        System.out.println("<<<<<<<<<<<<<<<< OFSAAFlow <<<<<<<<<<<<<<<<");
    }

    public void OFSAA1_Setup_Entity_And_Returns() {
        System.out.println(">>>>>>>>>>>>>>>> OFSAAFlowSetup >>>>>>>>>>>>>>>>");

        List<String> privileges = Arrays.asList("Return Maker", "Return Viewer");

        for (OFSAAReport ofsaaReport : ofsaaDataSummary.getReports()) {
            int version = ofsaaReport.getFormInstanceRevision();
            loginFixture.loginAsAdmin();
            chromeHeadlessDownloadEnabler.enableDownloads();

            usersPage.open();
            usersAndGroupsFixture.addUser("ofsaauser");
            usersAndGroupsFixture.addUserToGroup(
                    "OFSAA",
                    "OFSAA",
                    "ofsaauser");
            entityConfigFixture.assignReturnAndConfigureEntity(
                    ofsaaReport.getEntity(),
                    ProductPackage.valueOf(product),
                    ofsaaReport.getFormInstance(),
                    version,
                    "OFSAA",
                    privileges, ofsaaReport.getType(), null);
        }
        System.out.println("<<<<<<<<<<<<<<<< OFSAAFlowSetup <<<<<<<<<<<<<<<<");
    }

    public void OFSAA2_Assign_Binding() {
        System.out.println(">>>>>>>>>>>>>>>> OFSAAFlowCalcAndBinding >>>>>>>>>>>>>>>>");

        loginFixture.loginAsAdmin();

        calcEngineAndBindingFixture
                .createCalculationEngine(
                        "Data WareHouse",
                        calcEngine, null)
                .assignBinding(
                        calcEngine,
                        configName,
                        ofsaaDataSummary.getConfigPackagePrefix(),
                        product);
        System.out.println("<<<<<<<<<<<<<<<< OFSAAFlowCalcAndBinding <<<<<<<<<<<<<<<<");
    }

    public void OFSAA3_Retrieve() {
        System.out.println(">>>>>>>>>>>>>>>> OFSAAFlowRetrieve >>>>>>>>>>>>>>>>");
        jobsRun = new HashMap<>();
        loginFixture
                .loginAaOFSAA();
        for (OFSAAReport ofsaaReport : ofsaaDataSummary.getReports()) {
            Date inputDate = ofsaaReport.getReferenceDate();
            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");

            String scorrectDateFormat = newFormat.format(inputDate);

            LocalDate refDate = toLocalDate.transform(scorrectDateFormat);
            createReturnFixture
                    .filterReturns(
                            product,
                            ofsaaReport.getEntity(),
                            ofsaaReport.getFormInstance(),
                            ofsaaReport.getFormInstanceRevision())
                    .retrieveReturn(
                            ofsaaReport.getEntity(),
                            refDate,
                            ofsaaReport.getFormInstanceRevision(),
                            ofsaaReport.getFormInstance(),
                            ofsaaReport.getFormInstance() + " v" + ofsaaReport.getFormInstanceRevision());
            jobManagerPage
                    .open();
            String jobName = product
                    + "|"
                    + ofsaaReport.getEntity()
                    + "|"
                    + ofsaaReport.getFormInstance()
                    + "|"
                    + ofsaaReport.getFormInstanceRevision();
            String status = jobManagerPage.retrieveStatus(jobName);
            jobsRun.put(jobName, status);
            System.out.println("Job named: " + jobName + " completed with status: " + status);
            jobManagerPage.backToDashboardButton();
        }

        System.out.println("[OFSAA Retrieve Report Status] " + jobsRun);
        System.out.println("<<<<<<<<<<<<<<<< OFSAAFlowRetrieve <<<<<<<<<<<<<<<<");
    }

    public void OFSAA4_Export_To_CSV() {
        System.out.println(">>>>>>>>>>>>>>>> OFSAAFlowExports >>>>>>>>>>>>>>>>");
        loginFixture
                .loginAaOFSAA();

        for (OFSAAReport ofsaaReport : ofsaaDataSummary.getReports()) {
            Date refDate = ofsaaReport.getReferenceDate();
            LocalDate lrefdate =
                    Instant.ofEpochMilli(refDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            System.out.println("Opening Return:"
                    + ofsaaReport.getFormInstance()
                    + ":"
                    + ofsaaReport.getFormInstanceRevision());
            returnInstanceFixture
                    .openReturn(
                            product,
                            ofsaaReport.getEntity(),
                            ofsaaReport.getFormInstance(),
                            ofsaaReport.getFormInstanceRevision(),
                            lrefdate)
                    .exportToFile("Export to CSV")
                    .closeReturn();

            System.out.println("Return processed:"
                    + ofsaaReport.getFormInstance()
                    + ":"
                    + ofsaaReport.getFormInstanceRevision());
        }
        System.out.println("<<<<<<<<<<<<<<<< OFSAAFlowExports <<<<<<<<<<<<<<<<");
    }
}
