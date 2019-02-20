package com.lombardrisk.page.analysismodule.returnselectionpanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.analysismodule.AnalysisModuleWaiter;
import com.lombardrisk.page.analysismodule.grid.GridPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.lombardrisk.page.analysismodule.grid.GridPage.AM_DATE_FORMATTER;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.contains;

public class ReturnSelectorPanel {

    private static final String SELECTED_CLASS_NAME = "mat-selected";
    private static final String AGILE_REPORTER_TAB_NAME = "AgileREPORTER";
    private static final String ANALYSIS_MODULE_TAB_NAME = "Lombard Risk";
    private final SelenideElement lombardRiskLogo = $(By.className("logo"));
    private final SelenideElement regulatorDropdown = $(By.xpath("//app-product-control//mat-select"));
    private final SelenideElement entityDropdown = $(By.xpath("//app-entity-control//mat-select"));
    private final SelenideElement returnDropdown = $(By.xpath("//app-form-control//mat-select"));
    private final SelenideElement reportingDateDropdown =
            $(By.cssSelector("app-return-control[placeholdertext='Reporting Date'] mat-select"));
    private final SelenideElement variancePreviousReportingDateDropdown =
            $(By.cssSelector("app-return-control[placeholdertext='Previous Reporting Date'] mat-select"));
    private final SelenideElement adHocPreviousReportingDateDropdown =
            $(By.xpath("//app-trends-selection-control//mat-select"));
    private final SelenideElement dateRangePreviousReportingDateDropdown =
            $(By.cssSelector("app-return-control[placeholdertext='Previous Reporting Date'] mat-select"));
    private final SelenideElement panelErrorMessage = $(By.xpath("//mat-form-field//div/mat-error"));
    private final SelenideElement previousReturnsPanel = $(By.id("qa-previous-returns-group"));
    private final SelenideElement dateRangeRadioButton =
            $(By.xpath("//app-trends-type-control//mat-radio-button[1]/label"));
    private final SelenideElement numberOfPeriodsRadioButton =
            $(By.xpath("//app-trends-type-control//mat-radio-button[2]/label"));
    private final SelenideElement adHocDatesRadioButton =
            $(By.xpath("//app-trends-type-control//mat-radio-button[3]/label"));
    private final SelenideElement limitInput =
            $(By.xpath("//app-trends-frequency-control//input"));
    private final SelenideElement varianceButton = $(By.cssSelector("#qa-variance"));
    private final SelenideElement trendsButton = $(By.id("qa-trends"));
    private final SelenideElement instanceDropdown = $(By.xpath("//app-page-instance-control//mat-select"));
    private final SelenideElement reportCellGroupDropdown = $(By.xpath("//app-cell-group-control//mat-select"));
    private final SelenideElement createButton = $(By.id("qa-submit"));
    public final SelenideElement resetButton = $(By.xpath("//span[contains(text(),'Reset')]"));
    private final SelenideElement analysisModuleTitle = $(By.cssSelector("h1[class='title']"));

    private final Config config;
    private final GridPage gridPage;
    private final AnalysisModuleWaiter analysisModuleWaiter;

    public ReturnSelectorPanel(
            final Config config, final GridPage gridPage, final AnalysisModuleWaiter analysisModuleWaiter) {
        this.config = config;
        this.gridPage = gridPage;
        this.analysisModuleWaiter = analysisModuleWaiter;
    }

    private static void selectTrendsType(final SelenideElement trendsTypeRadioButton) {
        trendsTypeRadioButton.click();

        trendsTypeRadioButton.parent()
                .shouldHave(cssClass("mat-radio-checked"));
    }

    private static boolean isDropdownCheckboxSelected(final SelenideElement option) {
        String dropdownCheckboxClasses = option.attr("class");

        return contains(dropdownCheckboxClasses, "mat-selected");
    }

    private static void selectOptionFromDropdown(final String value, final SelenideElement dropdown) {
        dropdown.shouldBe(visible).click();
        findOption(value).shouldBe(visible).click();
        dropdown.shouldBe(Condition.matchText(value));
    }

    private static SelenideElement findOption(final String value) {
        return $(By.xpath("//mat-option//span[contains(text(), '" + value + "')]"));
    }

    private static ElementsCollection findAllOptions() {
        return $$(By.xpath("//mat-option"));
    }

    public void shouldBeVisible() {
        analysisModuleWaiter.waitForFetch();

        regulatorDropdown.shouldBe(visible);

        analysisModuleTitle.shouldBe(visible);
        analysisModuleTitle.shouldHave(text("Analysis Module"));
    }

    public void shouldHaveNoSelections() {
        regulatorDropdown.shouldHave(attribute("aria-label", "Regulator"));
        regulatorDropdown.shouldHave(cssClass("ng-invalid"));
    }

    public ReturnSelectorPanel switchToAnalysisModuleTab() {
        switchTo().window(AGILE_REPORTER_TAB_NAME).close();
        switchTo().window(ANALYSIS_MODULE_TAB_NAME);
        return this;
    }

    public ReturnSelectorPanel open() {
        Selenide.open(config.fullAnalysisModuleUrl() + "/dashboard?");

        analysisModuleTitle.shouldBe(visible);
        return this;
    }

    public ReturnSelectorPanel selectRegulator(final String regulator) {
        selectOptionFromDropdown(regulator, regulatorDropdown);

        analysisModuleWaiter.waitForFetch();
        return this;
    }

    public ReturnSelectorPanel selectEntity(final String entity) {
        selectOptionFromDropdown(entity, entityDropdown);

        analysisModuleWaiter.waitForFetch();
        return this;
    }

    public ReturnSelectorPanel selectReturn(final String targetReturn) {
        selectOptionFromDropdown(targetReturn, returnDropdown);

        analysisModuleWaiter.waitForFetch();
        return this;
    }

    public void selectCellGroup(final String cellGroupName) {
        selectOptionFromDropdown(cellGroupName, reportCellGroupDropdown);
    }

    public void selectInstance(final String instance) {
        selectOptionFromDropdown(instance, instanceDropdown);
    }

    public void selectReferenceDate(final String referenceDate) {
        selectOptionFromDropdown(referenceDate, reportingDateDropdown);

        analysisModuleWaiter.waitForFetch();
    }

    public ReturnSelectorPanel selectReferenceDate(final LocalDate referenceDate) {
        selectReferenceDate(AM_DATE_FORMATTER.format(referenceDate));
        return this;
    }

    private void selectPreviousReferenceDate(final String date) {
        selectOptionFromDropdown(date, variancePreviousReportingDateDropdown);
        analysisModuleWaiter.waitForFetch();
    }

    private void selectPreviousReferenceDate(final LocalDate referenceDate) {
        selectPreviousReferenceDate(AM_DATE_FORMATTER.format(referenceDate));
    }

    private void selectDateRangePreviousReferenceDate(final LocalDate date) {
        selectOptionFromDropdown(
                AM_DATE_FORMATTER.format(date),
                dateRangePreviousReportingDateDropdown);

        analysisModuleWaiter.waitForFetch();
    }

    public void checkRegulatorDropdownOptions(final List<String> expectedRegulators) {
        regulatorDropdown.click();
        ElementsCollection regulatorDropdownEntries = $$(By.xpath("//span[@class='mat-option-text']"));

        regulatorDropdownEntries.shouldHave(texts(expectedRegulators));
    }

    public void checkEntityDropdownOptions(final List<String> expectedEntities) {
        entityDropdown.click();
        ElementsCollection entityDropdownEntries = $$(By.xpath("//span[@class='mat-option-text']"));

        entityDropdownEntries.shouldHave(texts(expectedEntities));
    }

    public void checkReturnDropdownOptions(final List<String> expectedReturns) {
        returnDropdown.click();
        ElementsCollection returnDropdownEntries = $$(By.xpath("//span[@class='mat-option-text']"));

        returnDropdownEntries.shouldHave(texts(expectedReturns));
    }

    public void checkReportingDateOptions(final List<String> expectedReportingDates) {
        reportingDateDropdown.click();
        $$(By.xpath("//span[@class='mat-option-text']")).shouldHave(texts(expectedReportingDates));
    }

    public void checkInstanceDropdown(final List<String> expectedInstances) {
        instanceDropdown.shouldBe(visible).click();
        analysisModuleWaiter.waitForFetch();
        $$(By.xpath("//span[@class='mat-option-text']")).shouldHave(texts(expectedInstances));
        instanceDropdown.sendKeys(Keys.TAB);
    }

    public void checkVariancePreviousReportingDateErrorMessage() {
        SelenideElement previousReportingDateDropdownEntries = $(By.xpath("//div/p"));

        previousReportingDateDropdownEntries.shouldHave(
                text(" No previous dates are available for this Reporting Date. "
                        + "You can try a different Reporting Date or Return."));
    }

    public void checkTrendsPreviousReportingDateErrorMessage() {
        SelenideElement trendsPreviousReportingDateDropdownEntries = $(By.xpath("//div/p"));

        trendsPreviousReportingDateDropdownEntries.shouldHave(
                text(" Trends need at least two previous dates - "
                        + "not enough exist for this Return. "));
    }

    public void checkPreviousReportingDate(final List<String> expectedPreviousReportingDates) {
        variancePreviousReportingDateDropdown.click();
        ElementsCollection previousReportingDateDropdownEntries = $$(By.xpath("//span[@class='mat-option-text']"));

        previousReportingDateDropdownEntries.shouldHave(texts(expectedPreviousReportingDates));
    }

    public void checkAdHocSelectionPreviousReportingDates(final List<CheckboxEntry> adHocSelectionPreviousDateList) {
        adHocPreviousReportingDateDropdown.click();

        ElementsCollection checkedAdHocSelectionPreviousReportingDates =
                $$x("//mat-pseudo-checkbox[contains(@class, 'mat-pseudo-checkbox-checked')]/../span");

        checkedAdHocSelectionPreviousReportingDates.shouldHave(texts(
                adHocSelectionPreviousDateList.stream()
                        .filter(CheckboxEntry::isChecked)
                        .map(CheckboxEntry::getDescription)
                        .collect(toList())));

        ElementsCollection adHocSelectionPreviousReportingDates =
                $$(By.xpath("//span[@class='mat-option-text']"));

        adHocSelectionPreviousReportingDates.shouldHave(texts(
                adHocSelectionPreviousDateList.stream()
                        .map(CheckboxEntry::getDescription)
                        .collect(toList())));
    }

    public void checkTrendsDateRangePreviousReportingDateDropdown(final LocalDate prepopulatedPreviousReportingDate) {
        dateRangePreviousReportingDateDropdown.shouldBe(
                text(AM_DATE_FORMATTER.format(prepopulatedPreviousReportingDate)));
    }

    public void checkVarianceDateRangePreviousReportingDateDropdown(final String prepopulatedPreviousReportingDate) {
        variancePreviousReportingDateDropdown.shouldBe(text(prepopulatedPreviousReportingDate));
    }

    public void addAdHocSelectionPreviousReportingDate(final List<String> dateList) {
        adHocPreviousReportingDateDropdown.click();
        dateList.forEach(e -> findOption(e).click());
        lombardRiskLogo.doubleClick();
        lombardRiskLogo.click();
    }

    public void selectVariance() {
        varianceButton
                .shouldBe(enabled)
                .click();

        varianceButton.shouldHave(cssClass("mat-button-toggle-checked"));
    }

    public void selectTrends() {
        trendsButton
                .shouldBe(enabled)
                .click();
        trendsButton.shouldHave(cssClass("mat-button-toggle-checked"));
    }

    public void create() {
        createButton
                .shouldBe(enabled)
                .click();
        analysisModuleWaiter.waitForFetch();
        gridPage.shouldBeVisible();
    }

    public void reset() {
        resetButton
                .shouldBe(enabled)
                .click();
        analysisModuleWaiter.waitForFetch();
    }

    public void createVarianceAnalysis(final LocalDate date) {
        selectPreviousReferenceDate(date);
        selectVariance();
        create();
        analysisModuleWaiter.waitForFetch();
        gridPage.shouldBeVisible();
    }

    public void createDateRangeTrendAnalysis(final LocalDate date) {
        selectTrends();
        selectDateRangePreviousReferenceDate(date);
        create();
        analysisModuleWaiter.waitForFetch();
        gridPage.shouldBeVisible();
    }

    public void createAdHocTrendAnalysis(final List<LocalDate> adHocDates) {
        selectTrends();
        selectAdHocDates(adHocDates);
        create();
        analysisModuleWaiter.waitForFetch();
        gridPage.shouldBeVisible();
    }

    private void checkDropdownOptions(final List<String> optionsToSelect, final SelenideElement dropdown) {
        dropdown.click();
        analysisModuleWaiter.waitForQuickAnimation();

        for (SelenideElement option : findAllOptions()) {
            String valueText = option.text();

            if (optionsToSelect.contains(valueText)) {
                selectDropdownCheckbox(option);
            } else {
                deselectDropdownCheckbox(option);
            }
        }
        analysisModuleWaiter.waitForQuickAnimation();
        dropdown.pressTab();
    }

    private void selectDropdownCheckbox(final SelenideElement option) {
        if (!isDropdownCheckboxSelected(option)) {
            option.click();
            analysisModuleWaiter.waitForQuickAnimation();
        }
        option.shouldHave(cssClass(SELECTED_CLASS_NAME));
    }

    private void deselectDropdownCheckbox(final SelenideElement option) {
        if (isDropdownCheckboxSelected(option)) {
            option.click();
            analysisModuleWaiter.waitForQuickAnimation();
        }
        option.shouldNotHave(cssClass(SELECTED_CLASS_NAME));
    }

    private void setNumberOfPeriods(final int periods) {
        selectTrendsType(numberOfPeriodsRadioButton);
        limitInput.click();

        int currentLimit = Integer.parseInt(limitInput.getValue());
        int arrowKeyPresses = periods - currentLimit;
        Keys arrowKey = arrowKeyPresses > 0 ? Keys.ARROW_UP : Keys.ARROW_DOWN;

        for (int i = 0; i < Math.abs(arrowKeyPresses); i++) {
            limitInput.sendKeys(arrowKey);
        }
        limitInput.shouldHave(value(String.valueOf(periods)));

        previousReturnsPanel.click();
        panelErrorMessage.shouldNotBe(visible);
    }

    public void createTrendAnalysisForNumberOfPeriods(final int periods) {
        selectTrends();
        setNumberOfPeriods(periods);
        create();
        analysisModuleWaiter.waitForFetch();
        gridPage.shouldBeVisible();
    }

    private void selectAdHocDates(final List<LocalDate> adHocDates) {
        selectTrendsType(adHocDatesRadioButton);

        List<String> dates =
                adHocDates.stream()
                        .map(adHocLocalDate -> adHocLocalDate.format(AM_DATE_FORMATTER))
                        .collect(toList());

        checkDropdownOptions(dates, adHocPreviousReportingDateDropdown);
    }

    public void checkRegulator(final String prepopulatedRegulator) {
        analysisModuleWaiter.waitForFetch();
        regulatorDropdown.shouldBe(visible).shouldBe(text(prepopulatedRegulator));
    }

    public void checkEntity(final String prepopulatedEntity) {
        analysisModuleWaiter.waitForFetch();
        entityDropdown.shouldBe(visible).shouldBe(text(prepopulatedEntity));
    }

    public void checkReturn(final String prepopulatedReturn) {
        analysisModuleWaiter.waitForFetch();
        returnDropdown.shouldBe(visible).shouldBe(text(prepopulatedReturn));
    }

    public void checkReportingDate(final LocalDate date) {
        analysisModuleWaiter.waitForFetch();
        reportingDateDropdown.shouldBe(visible).shouldBe(text(AM_DATE_FORMATTER.format(date)));
    }

    public void checkReportCellGroup(final String prepopulatedReportCellGroup) {
        reportCellGroupDropdown.shouldBe(visible).shouldBe(text(prepopulatedReportCellGroup));
    }

    public void checkNotReportCellGroup(final String prepopulatedReportCellGroup) {
        reportCellGroupDropdown.shouldNotBe(text(prepopulatedReportCellGroup));
    }

    public void checkInstance(final String prepopulatedInstance) {
        instanceDropdown.shouldBe(text(prepopulatedInstance));
    }

    public void checkInstanceIsHidden() {
        instanceDropdown.shouldNotBe(visible);
    }

    public void clickNumberOfPeriods() {
        numberOfPeriodsRadioButton.click();
    }

    public void checkLimitsNumber(final String numberOfPeriods) {
        limitInput.shouldHave(value(numberOfPeriods));
    }

    public void clickAdHocRadioButton() {
        adHocDatesRadioButton.click();
    }

    public void updateTheNumberOfPeriods(final String numberOfPeriods) {
        limitInput.setValue(numberOfPeriods);
        limitInput.pressTab();

        limitInput.shouldHave(value(numberOfPeriods));
    }

    public void checkTooManyPeriodsError() {
        panelErrorMessage.shouldHave(text("A maximum of 12 periods is permitted"));
    }

    public void checkTooFewPeriodsError() {
        panelErrorMessage.shouldHave(text("A minimum of 2 periods is required"));
    }

    public void clickLombardRiskLogoToResetFocusBackToApp() {
        lombardRiskLogo.doubleClick();
    }

    public void checkAdHocSelectionErrorMessage() {
        panelErrorMessage.shouldHave(text("A maximum of 12 selected options is permitted"));
    }

    public void checkAdHocSelectionMinErrorMessage() {
        panelErrorMessage.shouldHave(text("A minimum of 2 selected options is required"));
    }

    public void shouldHaveDateRangeSelected() {
        dateRangeRadioButton.parent()
                .shouldHave(cssClass("mat-radio-checked"));
    }

    public void checkRadioButtonOptions(final List<String> expectedRadioButtons) {
        for (int i = 0; i < expectedRadioButtons.size(); i++) {
            SelenideElement radioButtonOptions = $(By.id("mat-radio-" + (2 + i)));
            radioButtonOptions.shouldHave(text(expectedRadioButtons.get(i)));
        }
    }

    public void analysisModuleIsVisible() {
        analysisModuleTitle.shouldBe(visible);
    }

    public void analysisModuleIsNotVisible() {
        analysisModuleTitle.shouldNotBe(visible);
    }
}
