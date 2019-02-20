package com.lombardrisk.page.returninstance;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.PageUtils;
import org.openqa.selenium.By;

import java.time.LocalDate;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class ExportDialog {

    private final SelenideElement title = $(By.id("transmitDialog4Fed_title"));
    private final SelenideElement entityDropDown =
            $(By.xpath("//form[contains(@id,'transmitForm')]//select[contains(@id,'selectGroup')]"));
    private final SelenideElement moduleDropDown = $(By.xpath("//select[@id='transmitForm4Fed:selectModule']"));
    private final SelenideElement referenceDateDropDown =
            $(By.xpath("//form[contains(@id,'transmitForm')]//select[contains(@id,'selectProcessDate')]"));
    private final SelenideElement exportButton =
            $(By.xpath("//button[contains(@id,'transmit')]/span[text()='Export']"));
    private final SelenideElement forceExportButton =
            $(By.xpath("//button[contains(@id,'transmit')]/span[text()='Force export']"));
    private final SelenideElement forceSubmitCommentField = $(By.id("forceSubmitCommonForm:commentTextarea"));
    private final SelenideElement forceCommentDialogSubmitButton =
            $(By.xpath("//button[@id='forceSubmitCommonForm:exportStreamBtn']/span"));
    private final SelenideElement forceCommentDialogCancelButton =
            $(By.xpath("//button[@id='forceSubmitCommonForm:cancelBtn']/span"));
    private final SelenideElement closeButton = $(By.xpath(
            "//div[contains(@id,'transmitDialog')][2]/div[contains(@class,'ui-dialog-titlebar')]/span[contains(text(),'Export to')]/../a"));

    public void selectAReturnAndExport(final String returnToExport) {
        sleep(1000);
        if (!findReturnCheckbox(returnToExport).isEnabled()) {
            findReturnCheckbox(returnToExport).click();
        }
        sleep(1000);
        exportButton.click();
    }

    public void shouldBeDisplayed(final String exportTitle) {
        title.shouldHave(Condition.text(exportTitle));
    }

    public ExportDialog selectAReturnAndForceExport(final String returnToExport) {
        sleep(1000);
        if (!PageUtils.isChecked(findReturnCheckbox(returnToExport))) {
            findReturnCheckbox(returnToExport).click();
        }
        sleep(1000);
        forceExportButton.click();
        return this;
    }

    public void returnItemShouldBeDisabled(final String returnToExport) {
        sleep(1000);
        PageUtils.shouldBeDisabled(findReturnCheckbox(returnToExport));
        exportButton.shouldNotBe(disabled);
    }

    public void selectModule(final String returnName) {
        moduleDropDown.click();
        moduleDropDown.selectOptionContainingText(returnName);
        sleep(500);
        moduleDropDown.shouldHave(value(returnName));
    }

    public void selectEntity(final String entity) {
        sleep(2000);
        entityDropDown.shouldBe(visible);
        entityDropDown.click();
        entityDropDown.selectOption(entity);
    }

    public void selectReferenceDate(final LocalDate referenceDate) {
        final String result = PageUtils.DATE_FORMATTER.format(referenceDate);
        final String actual = referenceDateDropDown.getSelectedText();
        if (!result.equals(actual)) {
            referenceDateDropDown.selectOptionContainingText(PageUtils.DATE_FORMATTER.format(referenceDate));
        }
    }

    public void forceSubmitComment(final String comment) {
        forceSubmitCommentField.setValue(comment);
        forceCommentDialogSubmitButton.click();
        forceCommentDialogSubmitButton.shouldNotBe(visible);
    }

    public void cancelForceSubmit() {
        forceCommentDialogCancelButton.click();
        forceCommentDialogCancelButton.shouldNotBe(visible);
    }

    public void close() {
        closeButton.click();
    }

    private static SelenideElement findReturnCheckbox(final String returnToExport) {
        return $(By.xpath("//tr/td[@role][text()='" + returnToExport + "']/../td[1]/div/div[2]"));
    }
}
