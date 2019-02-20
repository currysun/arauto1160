package com.lombardrisk.page.administration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.MainMenu;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

public class CalculationEnginesPage {

    private static final Logger log = LoggerFactory.getLogger(CalculationEnginesPage.class);

    private final SelenideElement addCalculationEngineButton = $(By.id("addCalcEngineDetail"));
    private final SelenideElement saveCalculationEngineButton =
            $(By.xpath("//*[@id=\"editCalcEngineForm:confirmCalcEngineEdit\"]/span[text()='Save']"));

    private final SelenideElement calcEngineConfigDropdown =
            $(By.xpath("//*[@id=\"editCalcEngineForm:selectedSystemCode\"]/div[3]/span"));

    private final SelenideElement fcrURLField = $(By.id("editCalcEngineForm:fcrHttpUrl"));
    private final SelenideElement fcrUserNameField = $(By.id("editCalcEngineForm:fcrUser"));
    private final SelenideElement fcrPasswordField = $(By.id("editCalcEngineForm:fcrPassword"));

    public SelenideElement calculationEngineOption(final String calcOption) {
        return $(By.xpath("//*[@id=\"calcEnginesInfo_data\"]//span[text()='" + calcOption + "']"));
    }

    public SelenideElement editCalculationEngineButton(final String calcOption) {
        return $(By.xpath("//*[@id=\"calcEnginesInfo_data\"]//span[text()='" + calcOption + "']/../../td[3]/a/img"));
    }

    private SelenideElement calcEngineDetails() {
        return $(By.id("editCalcEngineForm"));
    }

    private final MainMenu mainMenu;
    private final Notification notification;

    public CalculationEnginesPage(final MainMenu mainMenu, final Notification notification) {
        this.mainMenu = mainMenu;
        this.notification = notification;
    }

    public CalculationEnginesPage open() {
        mainMenu.openSettings()
                .administration().calculationEngines();

        addCalculationEngineButton
                .shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return this;
    }

    public CalculationEnginesPage editCalculationEngine(final String calcName) {
        editCalculationEngineButton(calcName).click();
        return this;
    }

    public CalculationEnginesPage addCalculationEngine(final String calcName, final String calcEngineType) {
        addCalculationEngine(calcName, calcEngineType, null);
        return this;
    }

    public CalculationEnginesPage addCalculationEngine(
            final String calcName,
            final String calcEngineType,
            final String fcrURL) {

        addCalculationEngineButton.shouldBe(Condition.enabled).click();
        selectCalculationEngineType(calcEngineType);
        selectCaculationEngineConfig(calcName);

        if (calcName.contains("FCR")){
            String existingFCR = fcrURLField.getText();
            if (!existingFCR.matches(fcrURL)) {
                log.warn("Your stated fcr url has altered, please change your configuration alias in config.bat");
            }
            fcrURLField.sendKeys(fcrURL);
            Selenide.sleep(1000);
            fcrUserNameField.sendKeys("admin");
            Selenide.sleep(1000);
            fcrPasswordField.sendKeys("password");
            Selenide.sleep(1000);
        }
        save();
        notification.loadingProgressShouldNotBeDisplayed();
        return this;
    }

    public CalculationEnginesPage selectCalculationEngineType(final String calcType) {
        calcEngineDetails().find(By.xpath("//label[contains(.,'" + calcType + "')]")).click();
        return this;
    }

    public CalculationEnginesPage selectCaculationEngineConfig(final String calcConfig) {
        calcEngineConfigDropdown.click();
        SelenideElement select = $(By.xpath("//*[@id=\"editCalcEngineForm:selectedSystemCode_panel\"]//li[text()='"
                + calcConfig
                + "']"));
        select.shouldBe(Condition.visible).click();
        return this;
    }

    public CalculationEnginesPage save() {
        saveCalculationEngineButton.click();
        notification.growlNotificationShouldBeDisplayed();
        notification.closeGrowlNotificationIfPresent();
        return this;
    }
}
