package com.lombardrisk.page.dashboard;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.analysismodule.returnselectionpanel.ReturnSelectorPanel;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.returninstance.ReturnInstancePage;
import org.openqa.selenium.By;

import java.time.LocalDate;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.lombardrisk.page.PageUtils.DATE_FORMATTER;
import static java.util.Objects.requireNonNull;

public class DashboardPage {

    private static final String TRENDS_LABEL = "Trends";
    private static final String VARIANCE_LABEL = "Variance";

    private static final String NO_STATUS_AVAILABLE_IMAGE = "/agilereporter/javax.faces" +
            ".resource/core/images/NoStatusAvailable.png.xhtml";
    private static final String STATUS_NOT_REQUIRED_IMAGE = "/agilereporter/javax.faces" +
            ".resource/core/images/StatusNotRequired.png.xhtml";
    private static final String HELP_TAB_NAME = "Analysing variance and trends using Analysis Module";
    private static final String CREATED_FROM_IMPORT = "/agilereporter/javax.faces" +
            ".resource/core/images/CreatedFromImport.png.xhtml";

    private final SelenideElement createNewDropDown = $(By.id("FormInstImpExpMenu:createNew"));
    private final SelenideElement createNewMenuButton = $(By.id("FormInstImpExpMenu:createFormInstEmpty"));
    private final SelenideElement createFromExcelMenuButton = $(By.id
            ("FormInstImpExpMenu:createFormInstFromExcel"));
    private final SelenideElement retrieveReturn =
            $(By.xpath("//button[@id='FormInstImpExpMenu:retrieve']/span[text()='Retrieve Return']"));
    private final SelenideElement openCommentLogButton = $(By.id
            ("formInstanceListForm:formInstanceListTable:0:openCommentLog"));
    private final SelenideElement regulatorDropdown = $(By.id("formFilter:regulator"));
    private final SelenideElement entitySelectDropdown = $(By.id("formFilter:selectGroup"));
    private final SelenideElement returnSelectDropdown = $(By.id("formFilter:selectForm"));
    private final SelenideElement submitReturnDropDown = $(By.id("FormInstImpExpMenu:exportToFile"));
    private final SelenideElement confirmDeleteReturnButton = $(By.id
            ("deleteReturnMessageForm:deleteReturnConfirm"));
    private final SelenideElement confirmDeleteReturnTextArea = $(By.id
            ("deleteReturnCommentForm:deleteReturnCommentTxt"));
    private final SelenideElement deleteReturnButton = $(By.id("deleteReturnCommentForm:deleteReturn"));
    private final SelenideElement analysisModuleButton = $(By.id("formHeader:analysisModuleBtn"));
    private final SelenideElement helpLink = $(By.xpath("//mat-toolbar//mat-icon"));
    private final SelenideElement amIntroductionHelp =
            $(By.cssSelector("li.tree-node.tree-node-expanded > ul > li.tree-node.tree-node-expanded > div > span > a"));
    public final SelenideElement jobManagerButton = $(By.xpath("//button/span[text()='Job Manager']"));
    private final Config config;
    private final Notification notification;
    private final ReturnInstancePage returnInstancePage;
    private final CreateReturnFromExcelDialog createReturnFromExcelDialog;
    private final ReturnSelectorPanel returnSelectorPanel;
    private final CreateNewReturnDialog createNewReturnDialog;

    public DashboardPage(
            final Config config,
            final Notification notification,
            final CreateNewReturnDialog createNewReturnDialog,
            final ReturnInstancePage returnInstancePage,
            final CreateReturnFromExcelDialog createReturnFromExcelDialog,
            final ReturnSelectorPanel returnSelectorPanel) {
        this.config = requireNonNull(config);
        this.notification = requireNonNull(notification);
        this.createNewReturnDialog = createNewReturnDialog;
        this.returnInstancePage = requireNonNull(returnInstancePage);
        this.createReturnFromExcelDialog = requireNonNull(createReturnFromExcelDialog);
        this.returnSelectorPanel = requireNonNull(returnSelectorPanel);
    }

    public static boolean hasAnyActiveStates(
            final String returnName,
            final int returnVersion,
            final LocalDate returnDate) {
        SelenideElement returnInstanceRow = returnInstanceRow(returnName, returnVersion, returnDate);

        String xpath = String.format(
                "td[12]/img[not(@src='%s' or @src='%s')]",
                NO_STATUS_AVAILABLE_IMAGE,
                STATUS_NOT_REQUIRED_IMAGE,
                CREATED_FROM_IMPORT
        );
        ElementsCollection activeStatuses = returnInstanceRow.findAll(By.xpath(xpath));
        return activeStatuses.size() > 0;
    }

    private static SelenideElement submitType(final String submitType) {
        return $(By.xpath("//div[@id='FormInstImpExpMenu:exportToFile_menu']//span[text()='" + submitType + "']"));
    }

    private static ElementsCollection returnInstanceRows() {
        return $$(By.xpath("//tbody[@id='formInstanceListForm:formInstanceListTable_data']/tr"));
    }

    public static SelenideElement returnInstanceRow(
            final String returnName,
            final int returnVersion,
            final LocalDate returnDate) {
        String referenceDate = returnDate.format(DATE_FORMATTER);
        return $x("//tbody[@id='formInstanceListForm:formInstanceListTable_data']"
                + "//td/a[text()='" + returnName + "']"
                + "/../../td[2][text()='" + returnVersion + "']"
                + "/../td[3][text()='" + referenceDate + "']/..");
    }

    public void selectRetrieveReturn() {
        retrieveReturn.shouldBe(visible).shouldBe(enabled).click();
        while (notification.loadingDialog().isDisplayed()) {
            notification.loadingProgressShouldNotBeDisplayed();
        }
    }

    public DashboardPage selectEntity(final String entity) {
        entitySelectDropdown.selectOptionContainingText(entity);
        while (notification.loadingDialog().isDisplayed()) {
            notification.loadingProgressShouldNotBeDisplayed();
        }
        return this;
    }

    public DashboardPage selectReturn(final String returns) {
        returnSelectDropdown.selectOptionContainingText(returns);
        while (notification.loadingDialog().isDisplayed()) {
            notification.loadingProgressShouldNotBeDisplayed();
        }
        return this;
    }

    public DashboardPage selectRegulator(final ProductPackage targetProductPackage) {
        String currentProductPackage = regulatorDropdown.getText();

        if (targetProductPackage.isDifferentFrom(currentProductPackage)) {
            String currentFirstReturnInstanceRow = returnInstanceRows().first().text();

            regulatorDropdown.selectOption(targetProductPackage.fullName());
            returnInstanceRows().first()
                    .waitUntil(not(text(currentFirstReturnInstanceRow)), config.maxTimeout());
        }
        return this;
    }

    public void createReturn() {
        createNewDropDown.click();
        createNewMenuButton.click();
        createNewReturnDialog.createAnyReturn(entitySelectDropdown.getText());
    }

    public ReturnInstancePage openReturn(final String returnName, final int returnVersion, final LocalDate date) {
        notification.loadingProgressShouldNotBeDisplayed();
        returnInstanceRow(returnName, returnVersion, date).find(By.xpath("td[1]/a")).click();
        returnInstancePage.shouldBeOpen();
        notification.loadingProgressShouldNotBeDisplayed();
        notification.loadingProgressShouldNotBeDisplayed();
        notification.loadingProgressShouldNotBeDisplayed();
        if ($(By.xpath("//div[@id='xSumReferencedUpdateDialog']//span[text()='Confirm']")).isDisplayed()) {
            $(By.xpath("//div[@id='xSumReferencedUpdateDialog']//span[text()='OK']")).shouldBe(visible).click();
            notification.loadingProgressShouldNotBeDisplayed();
            notification.loadingProgressShouldNotBeDisplayed();
        }
        return returnInstancePage;
    }

    public void shouldHaveReturnWithApprovalStatus(
            final ProductPackage expectedProductPackage,
            final String expectedReturnName,
            final int expectedReturnVersion,
            final LocalDate expectedReturnDate,
            final String expectedApprovalStatusAndCount) {

        selectRegulator(expectedProductPackage);
        returnInstanceRow(expectedReturnName, expectedReturnVersion, expectedReturnDate)
                .shouldHave(text(expectedApprovalStatusAndCount));
    }

    public boolean returnIsDisplayed(
            final String returnName,
            final int returnVersion,
            final LocalDate returnDate) {
        return returnInstanceRow(returnName, returnVersion, returnDate).isDisplayed();
    }

    public SelenideElement findReturnCommentsLink(
            final String returnName,
            final int returnVersion,
            final LocalDate returnDate) {
        return returnInstanceRow(returnName, returnVersion, returnDate).$(By.xpath(
                "//td/button[contains(@id,'openCommentLog')]/span"));
    }

    public CreateReturnFromExcelDialog openCreateFromExcelDialog() {
        createNewDropDown.click();
        createFromExcelMenuButton.click();

        notification.loadingProgressShouldNotBeDisplayed();
        return createReturnFromExcelDialog;
    }

    public DashboardPage open() {
        notification.loadingProgressShouldNotBeDisplayed();
        Selenide.open(config.arFullUrl() + "/reporter/page/reporter.xhtml");
        return this;
    }

    public void openCommentLog() {
        openCommentLogButton.click();
    }

    public void openReturnCommentLog(
            final String returnName,
            final int returnVersion,
            final LocalDate referenceDate) {

        findReturnCommentsLink(returnName, returnVersion, referenceDate).click();
    }

    public DashboardPage deleteReturn(final String returnName, final int returnVersion, final LocalDate returnDate) {
        SelenideElement returnInstanceRow = returnInstanceRow(returnName, returnVersion, returnDate);
        if (returnInstanceRow.isDisplayed()) {
            openReturn(returnName, returnVersion, returnDate)
                    .lock()
                    .unlock()
                    .close();

            returnInstanceRow.findAll(By.tagName("td")).last().shouldBe(visible).find(By.tagName("img")).click();
            confirmDeleteReturnButton.click();
            confirmDeleteReturnTextArea.setValue("deleting return");
            deleteReturnButton.click();
            while (notification.loadingDialog().isDisplayed()) {
                notification.loadingProgressShouldNotBeDisplayed();
            }

            returnInstanceRow.shouldNot(exist);
        }
        return this;
    }

    public void shouldBeOpen() {
        regulatorDropdown.shouldBe(visible);
    }

    public void submitToExportType(final String submitType) {
        submitReturnDropDown.click();
        submitType(submitType).click();
        notification.loadingProgressShouldNotBeDisplayed();
    }

    public DashboardPage openAnalysisModule() {
        analysisModuleButton.click();
        returnSelectorPanel
                .switchToAnalysisModuleTab()
                .shouldBeVisible();
        return this;
    }

    public DashboardPage clickTrendsButtonForReturn(
            final String returnName, final int returnVersion, final LocalDate date) {
        SelenideElement trendsButton =
                findAnalysisButtonWithLabel(returnName, returnVersion, date, TRENDS_LABEL);
        trendsButton
                .shouldBe(enabled)
                .click();
        returnSelectorPanel
                .switchToAnalysisModuleTab()
                .shouldBeVisible();
        return this;
    }

    public DashboardPage clickVarianceButtonForReturn(
            final String returnName, final int returnVersion, final LocalDate date) {
        SelenideElement varianceButton =
                findAnalysisButtonWithLabel(returnName, returnVersion, date, VARIANCE_LABEL);
        varianceButton
                .shouldBe(enabled)
                .click();
        returnSelectorPanel
                .switchToAnalysisModuleTab()
                .shouldBeVisible();
        return this;
    }

    public void varianceButtonIsDisabledForReturn(
            final String returnName, final int returnVersion, final LocalDate date) {
        SelenideElement varianceButton =
                findAnalysisButtonWithLabel(returnName, returnVersion, date, VARIANCE_LABEL);
        varianceButton
                .shouldBe(disabled);
    }

    public void clickOnHelpLink() {
        helpLink.click();
    }

    public DashboardPage switchToHelpTab() {
        switchTo().window(HELP_TAB_NAME);
        return this;
    }

    public void shouldHaveAnalysisModuleIntroductionHelpLinkOpen() {
        amIntroductionHelp.isDisplayed();
    }

    private SelenideElement findAnalysisButtonWithLabel(
            final String returnName,
            final int returnVersion,
            final LocalDate date,
            final String analysisType) {
        return returnInstanceRow(returnName, returnVersion, date)
                .$x("*//button/span[text()='" + analysisType + "']")
                .parent();
    }

    public void trendButtonIsDisabledForReturn(
            final String returnName, final int returnVersion, final LocalDate date) {
        SelenideElement trendButton =
                findAnalysisButtonWithLabel(returnName, returnVersion, date, TRENDS_LABEL);
        trendButton
                .shouldBe(disabled);
    }
}
