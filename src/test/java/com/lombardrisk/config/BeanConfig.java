package com.lombardrisk.config;

import com.codeborne.selenide.impl.Waiter;
import com.lombardrisk.config.driver.ChromeHeadlessDownloadEnabler;
import com.lombardrisk.fixtures.Admin.CalcEngineAndBindingFixture;
import com.lombardrisk.fixtures.Admin.EntityConfigFixture;
import com.lombardrisk.fixtures.Admin.UsersAndGroupsFixture;
import com.lombardrisk.fixtures.AnalysisModule.CreateChartFixture;
import com.lombardrisk.fixtures.Dashboard.CreateReturnFixture;
import com.lombardrisk.fixtures.ReturnInstance.ReturnInstanceFixture;
import com.lombardrisk.page.LoginPage;
import com.lombardrisk.page.administration.*;
import com.lombardrisk.page.returninstance.ImportDialog;
import com.lombardrisk.rest.gv.DatasetContent;
import fcr.fcrpages.FCRLoginPage;
import fcr.fcrpages.FCRHomePage;
import fcr.fcrpages.FCRStagingJobDialog;
import fcr.fcrpages.FCRUploadSchemaDialog;
import com.lombardrisk.page.analysismodule.AddCommentsDialog;
import com.lombardrisk.page.analysismodule.AnalysisModuleWaiter;
import com.lombardrisk.page.analysismodule.RedirectPage;
import com.lombardrisk.page.analysismodule.ReturnAnalysisReportPanel;
import com.lombardrisk.page.analysismodule.ViewCellCommentDialog;
import com.lombardrisk.page.analysismodule.ViewReturnCommentDialog;
import com.lombardrisk.page.analysismodule.cellgroup.AddCellGroupDialog;
import com.lombardrisk.page.analysismodule.cellgroup.CellGroupManagerDialog;
import com.lombardrisk.page.analysismodule.cellgroup.EditCellGroupDialog;
import com.lombardrisk.page.analysismodule.grid.ChartsPage;
import com.lombardrisk.page.analysismodule.grid.GridPage;
import com.lombardrisk.page.analysismodule.grid.TrendsAnalysisPage;
import com.lombardrisk.page.analysismodule.grid.VarianceAnalysisPage;
import com.lombardrisk.page.analysismodule.returnselectionpanel.ReturnSelectorPanel;
import com.lombardrisk.page.dashboard.CreateNewReturnDialog;
import com.lombardrisk.page.dashboard.CreateReturnFromExcelDialog;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.page.dashboard.DeleteReturnDialog;
import com.lombardrisk.page.dashboard.RetrieveReturnDialog;
import com.lombardrisk.page.entitysetup.AddNewEntityDialog;
import com.lombardrisk.page.entitysetup.EntitySetupDialog;
import com.lombardrisk.page.entitysetup.EntitySetupPage;
import com.lombardrisk.page.entitysetup.ImportSettingsDialog;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.JobManagerPage;
import com.lombardrisk.page.header.MainMenu;
import com.lombardrisk.page.header.UserPreferencesDialog;
import com.lombardrisk.page.returninstance.AddCommentDialog;
import com.lombardrisk.page.returninstance.ApprovalDialog;
import com.lombardrisk.page.returninstance.CommentLogDialog;
import com.lombardrisk.page.returninstance.ExportDialog;
import com.lombardrisk.page.returninstance.ReadyForApprovalDialog;
import com.lombardrisk.page.returninstance.RejectDialog;
import com.lombardrisk.page.returninstance.ReturnInstancePage;
import com.lombardrisk.stepdef.analysismodule.grid.checker.DownloadedFilesChecker;
import com.lombardrisk.stepdef.analysismodule.grid.checker.ExportedVarianceCsvChecker;
import com.lombardrisk.stepdef.analysismodule.grid.checker.ExportedVarianceExcelChecker;
import com.lombardrisk.stepdef.analysismodule.grid.converter.ExpectedCsvVarianceConverter;
import com.lombardrisk.stepdef.transformer.ToLocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Configuration
@Import(PropertiesConfig.class)
@Component
public class BeanConfig {

    private static final String NO_DESTROY_METHOD = "";

    @Autowired
    private Config config;

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ChromeHeadlessDownloadEnabler chromeHeadlessDownloadEnabler() {
        return new ChromeHeadlessDownloadEnabler(config);
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public Notification notification() {
        return new Notification(config);
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public MainMenu mainMenu() {
        return new MainMenu(userPreferencesDialog());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public LoginPage loginPage() {
        return new LoginPage(config, mainMenu(),notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public UserPreferencesDialog userPreferencesDialog() {
        return new UserPreferencesDialog(notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public CreateReturnFromExcelDialog createReturnFromExcelDialog() {
        return new CreateReturnFromExcelDialog(notification(), config);
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ImportDialog importDialog() {
        return new ImportDialog(config);
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public CreateReturnFixture createReturnFixture() {
        return new CreateReturnFixture(dashboardPage(), retrieveReturnDialog());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public DeleteReturnDialog deleteReturnDialog() {
        return new DeleteReturnDialog();
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public RetrieveReturnDialog retrieveReturnDialog() {
        return new RetrieveReturnDialog(notification(), dashboardPage(), deleteReturnDialog());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ToLocalDate toLocalDate() {
        return new ToLocalDate();
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public DashboardPage dashboardPage() {
        return new DashboardPage(
                config,
                notification(),
                createNewReturnDialog(),
                returnInstancePage(),
                createReturnFromExcelDialog(),
                returnSelectorPanel());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public CreateNewReturnDialog createNewReturnDialog() {
        return new CreateNewReturnDialog(notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ApprovalTemplatePage approvalTemplatePage() {
        return new ApprovalTemplatePage(mainMenu(), notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public AddNewEntityDialog addNewEntityDialog() {
        return new AddNewEntityDialog(notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public EntitySetupDialog entitySetupDialog() {
        return new EntitySetupDialog(config, notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ImportSettingsDialog importSettingsDialog() {
        return new ImportSettingsDialog(config, notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public EntitySetupPage entitySetupPage() {
        return new EntitySetupPage(
                config,
                notification(),
                mainMenu(),
                addNewEntityDialog(),
                entitySetupDialog(),
                importSettingsDialog());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public UserGroupsPage userGroupsPage() {
        return new UserGroupsPage(mainMenu(), notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public UsersPage usersPage() {
        return new UsersPage(mainMenu(), notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public AttachmentConfigurationPage attachmentConfigurationPage() {
        return new AttachmentConfigurationPage(config.arFullUrl());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public SecuritySettingsPage securitySettingsPage(){
        return new SecuritySettingsPage(mainMenu(),notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ReadyForApprovalDialog readyForApprovalDialog() {
        return new ReadyForApprovalDialog(config, notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ApprovalDialog approvalDialog() {
        return new ApprovalDialog(config, notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public RejectDialog rejectDialog() {
        return new RejectDialog(config, notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public AddCommentDialog addCommentDialog() {
        return new AddCommentDialog(config, notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public CommentLogDialog commentLogDialog() {
        return new CommentLogDialog(addCommentDialog(), config);
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ExportDialog exportDialog() {
        return new ExportDialog();
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public JobManagerPage jobManagerPage() {
        return new JobManagerPage(dashboardPage(), notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ReturnInstancePage returnInstancePage() {
        return new ReturnInstancePage(
                notification(),
                readyForApprovalDialog(),
                approvalDialog(),
                rejectDialog(),
                commentLogDialog(),
                returnSelectorPanel());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ReturnInstanceFixture returnInstanceFixture() {
        return new ReturnInstanceFixture(dashboardPage(), returnInstancePage());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public AnalysisModuleWaiter analysisModuleWaiter() {
        return new AnalysisModuleWaiter(config, waiter());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ReturnSelectorPanel returnSelectorPanel() {
        return new ReturnSelectorPanel(config, gridPage(), analysisModuleWaiter());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public GridPage gridPage() {
        return new GridPage(analysisModuleWaiter(), config);
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ViewReturnCommentDialog viewReturnCommentDialog() {
        return new ViewReturnCommentDialog();
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ViewCellCommentDialog viewCellCommentDialog() {
        return new ViewCellCommentDialog();
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public AddCommentsDialog addCommentsDialog(
            final Config config,
            final Notification notification) {
        return new AddCommentsDialog(config, notification);
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public VarianceAnalysisPage varianceAnalysisPage() {
        return new VarianceAnalysisPage(gridPage());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ExpectedCsvVarianceConverter expectedCsvVarianceConverter() {
        return new ExpectedCsvVarianceConverter(config);
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public TrendsAnalysisPage trendsAnalysisPage() {
        return new TrendsAnalysisPage(gridPage());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ReturnAnalysisReportPanel returnAnalysisReportPanel() {
        return new ReturnAnalysisReportPanel(config, gridPage());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public DownloadedFilesChecker downloadedFilesChecker() {
        return new DownloadedFilesChecker(config, waiter());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public Waiter waiter() {
        return new Waiter();
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ExportedVarianceCsvChecker exportedCsvVarianceGridChecker() {
        return new ExportedVarianceCsvChecker(downloadedFilesChecker());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ExportedVarianceExcelChecker exportedExcelVarianceGridChecker() {
        return new ExportedVarianceExcelChecker(downloadedFilesChecker());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public RedirectPage redirect() {
        return new RedirectPage();
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ChartsPage chartsPage() {
        return new ChartsPage(config, gridPage(), analysisModuleWaiter());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public CreateChartFixture createChartFixture() {
        return new CreateChartFixture(returnSelectorPanel(), chartsPage(), gridPage());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public UsersAndGroupsFixture usersAndGroupsFixture() {
        return new UsersAndGroupsFixture(usersPage(), userGroupsPage());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public EntityConfigFixture entityConfigFixture() {
        return new EntityConfigFixture(entitySetupPage(), entitySetupDialog());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public CalculationEnginesPage calculationEnginePage() {
        return new CalculationEnginesPage(mainMenu(), notification());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public CalcEngineAndBindingFixture calcEngineAndBindingFixture() {
        return new CalcEngineAndBindingFixture(calculationEnginePage(), configPackageBindingPage());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public ConfigPackageBindingPage configPackageBindingPage() {
        return new ConfigPackageBindingPage(mainMenu(), notification(), bindingCalcEngineToFromDialog());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public BindingCalcEngineToFormDialog bindingCalcEngineToFromDialog() {
        return new BindingCalcEngineToFormDialog();
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public AddCellGroupDialog addCellGroupDialog() {
        return new AddCellGroupDialog(analysisModuleWaiter());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public EditCellGroupDialog editCellGroupDialog() {
        return new EditCellGroupDialog(gridPage(), analysisModuleWaiter());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public CellGroupManagerDialog cellGroupManagerDialog() {
        return new CellGroupManagerDialog(config, analysisModuleWaiter(), addCellGroupDialog(), editCellGroupDialog());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public FCRLoginPage fcrLoginPage() {
        return new FCRLoginPage(config);
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public FCRHomePage fcrHomePage() {
        return new FCRHomePage(fcrSetJobDialog(), fcrUploadSchemaDialog());
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public FCRStagingJobDialog fcrSetJobDialog() {
        return new FCRStagingJobDialog(null, config);
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public FCRUploadSchemaDialog fcrUploadSchemaDialog() {
        return new FCRUploadSchemaDialog();
    }

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public DatasetContent AllocationReporter() {
        return new DatasetContent(config);
    }

}
