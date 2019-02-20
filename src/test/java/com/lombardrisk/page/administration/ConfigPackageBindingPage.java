package com.lombardrisk.page.administration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.MainMenu;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ConfigPackageBindingPage {

    private SelenideElement bindingCalToFormButton =
            $(By.xpath("//button/span[text()='Binding Calculation Engine to Form']"));

    private final MainMenu mainMenu;
    private final Notification notification;
    private final BindingCalcEngineToFormDialog bindingCalcEngineToFormDialog;

    public ConfigPackageBindingPage open() {
        mainMenu.openSettings()
                .administration().configPackageBinding();
        return this;
    }

    private final SelenideElement calcEngineDropDown = $(By.xpath("//select[contains(@id,'selectCalcEngineForm')]"));

    public ConfigPackageBindingPage(
            final MainMenu mainMenu,
            final Notification notification,
            final BindingCalcEngineToFormDialog bindingCalcEngineToFormDialog) {
        this.mainMenu = mainMenu;
        this.notification = notification;
        this.bindingCalcEngineToFormDialog = bindingCalcEngineToFormDialog;
    }

    private SelenideElement configurationPackageButton(final String productConfig) {
        return $(By.xpath("//tbody[contains(@id, 'configPackageListForm:')]/tr/td/span[text()='"
                + productConfig
                + "']"));
    }

    private SelenideElement calculationEngineActiveSet() {
        return $(By.xpath(
                "//div[contains(@id,'selectCalcEngineForm')]//tbody/tr/td[3]/div/div[2]/span/../..//div[contains(@class,'ui-state-active')]"));
    }

    private SelenideElement setDWDTForCalculationEngineType() {
        return $(By.xpath("//div[contains(@id,'selectCalcEngineForm')]//tbody/tr/td[3]/div/div[2]/span"));
    }

    private SelenideElement bindingSetRadioButton(final String configPrefix) {
        return $(By.xpath("//label[text()='" + configPrefix + "']/../../td/div//div[2]/span"));
    }

    public ConfigPackageBindingPage selectProductConfigurationPackage(final String productConfig) {
        configurationPackageButton(productConfig).click();
        notification.loadingProgressShouldNotBeDisplayed();
        return this;
    }

    public ConfigPackageBindingPage selectCalculationEngine(final String calcName, final String product) {
        SelenideElement configurationPackage =
                $(By.xpath("//tbody[contains(@id, 'configPackageListForm:')]/tr/td/span[text()='"
                        + product
                        + "']"));
        SelenideElement setCalc = $(By.xpath("//tbody[contains(@id,'calcMappingListForm')]/tr/td/span"));
        SelenideElement configExpand = $(By.xpath("//tbody[contains(@id,'calcMappingListForm')]/tr"));
        if (configExpand.isDisplayed()) {
            configExpand.click();
        }
        if (configurationPackage.isDisplayed()) {
            if (setCalc.exists()) {
                if (!setCalc.getText().equals(calcName)) {
                    SelenideElement addCalcToConfig = $(By.xpath("//a[contains(@id,'calcMappingListForm')][1]"));
                    addCalcToConfig.click();
                    calcEngineDropDown.shouldBe(Condition.visible).click();
                    calcEngineDropDown.shouldBe(Condition.visible).selectOptionContainingText(calcName);
                    notification.loadingProgressShouldNotBeDisplayed();
                }
            }
        }

        if (!$(By.xpath("//option[text()='" + calcName + "'][@selected='selected']")).isDisplayed()) {

            calcEngineDropDown.shouldBe(Condition.visible).click();
            calcEngineDropDown.shouldBe(Condition.visible).selectOptionContainingText(calcName);
            notification.loadingProgressShouldNotBeDisplayed();
        }
        return this;
    }

    public ConfigPackageBindingPage selectDWDTCalculationEngineType() {
        if (!calculationEngineActiveSet().isDisplayed()) {
            setDWDTForCalculationEngineType().click();
            notification.loadingProgressShouldNotBeDisplayed();
        }
        setDWDTForCalculationEngineType().shouldBe(Condition.visible);
        return this;
    }

    public ConfigPackageBindingPage selectDWBinding(final String configPrefix) {
        SelenideElement selection =
                $(By.xpath(
                        "//span[contains(text(),'Select a DW BINDING Set') or contains(text(), 'Select a Data Warehouse BINDING Set')]/../following-sibling::div[1]//td[3]/label[contains(text(),'"
                                + configPrefix
                                + "')]/../..//div[contains(@class,'ui-state-active')]"));

        SelenideElement required =
                $(By.xpath(
                        "//span[contains(text(),'Select a DW BINDING Set') or contains(text(), 'Select a Data Warehouse BINDING Set')]/../following-sibling::div[1]//td[3]/label[contains(text(),'"
                                + configPrefix
                                + "')]/../..//div/span"));

        if (!selection.isDisplayed()) {
            required.shouldBe(Condition.enabled).click();
            notification.loadingProgressShouldNotBeDisplayed();
        }
        selection.shouldBe(Condition.enabled);
        return this;
    }

    public ConfigPackageBindingPage selectDTBinding(final String configPrefix) {
        SelenideElement selection =
                $(By.xpath(
                        "//span[contains(text(),'Select a Decision Table Form Import Set') or contains(text(), 'Select a DT Form Import Set')]/../following-sibling::div[1]//td[3]/label[contains(text(),'"
                                + configPrefix
                                + "')]/../..//div[contains(@class,'ui-state-active')]"));

        SelenideElement required =
                $(By.xpath(
                        "//span[contains(text(),'Select a Decision Table Form Import Set') or contains(text(), 'Select a DT Form Import Set')]/../following-sibling::div[1]//td[3]/label[contains(text(),'"
                                + configPrefix
                                + "')]/../..//div/span"));

        if (!selection.isDisplayed()) {
            required.shouldBe(Condition.enabled).click();
            notification.loadingProgressShouldNotBeDisplayed();
        }
        selection.shouldBe(Condition.enabled);
        return this;
    }

    public ConfigPackageBindingPage selectTemplateBreadcrumbs() {
        SelenideElement selection = $(By.xpath(
                "//label[text()='Collect breadcrumbs for drilldown of template schedule.']/..//div/div[contains(@class,'ui-state-active')]"));
        if (!selection.isDisplayed()) {
            selection.click();
        }
        selection.shouldBe(Condition.enabled);
        return this;
    }

    public ConfigPackageBindingPage selectDrillDownBreadcrumbs() {
        SelenideElement selection =
                $(By.xpath(
                        "//label[text()='Collect breadcrumbs for drilldown of data schedule.']/..//div/div[contains(@class,'ui-state-active')]"));
        if (!selection.isDisplayed()) {
            selection.click();
        }
        selection.shouldBe(Condition.enabled);
        return this;
    }

    public ConfigPackageBindingPage saveConfigBinding() {
        SelenideElement save = $(By.xpath("//button[contains(@id,'saveForm')]"));
        if (save.isEnabled()) {
            save.click();
            notification.loadingProgressShouldNotBeDisplayed();
            notification.growlNotificationShouldBeDisplayed();
            notification.shouldCloseGrowlNotification();
        }
        return this;
    }

    public void selectProductDataSource(final String calcEngine, final String returnForm, final int version) {
        bindingCalToFormButton.click();
        bindingCalcEngineToFormDialog.sourceAllocation(calcEngine, returnForm, version);
    }
}
