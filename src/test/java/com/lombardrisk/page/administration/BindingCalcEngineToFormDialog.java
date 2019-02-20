package com.lombardrisk.page.administration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BindingCalcEngineToFormDialog {

    private SelenideElement title = $(By.id("formCalcEngineBindingForm:configPackageNamePanel"));
    private SelenideElement closeButton = $(By.xpath(
            "//div/span[@id='formCalcEngineBindingDialog_title']/../a[contains(@class,'ui-dialog-titlebar-close')]"));
    private SelenideElement bindAllDropDown = $(By.id("formCalcEngineBindingForm:selectCE_label"));
    private SelenideElement searchFormField = $(By.id("formCalcEngineBindingForm:formFilter"));

    private int findCalcEngineColumn(final String calcEngine) {
        int columnCount = 3;
        ElementsCollection columns =
                $$(By.xpath("//tbody[@id='calcMappingListForm:calcMappingTable_data']/tr"));
        for (SelenideElement column : columns) {
            columnCount++;
            if (column.getValue() == calcEngine) {
                break;
            }
        }
        return columnCount;
    }

    public BindingCalcEngineToFormDialog sourceAllocation(
            final String callEngine,
            final String returnForm,
            final int version) {
        int calcColumn = findCalcEngineColumn(callEngine);
        SelenideElement formAndSourceAssignment =
                $(By.xpath("//td[text()='" + returnForm + " v" + version + "']/../td[" + calcColumn + "]/div/div[2]"));
        Selenide.sleep(1000);
        searchFormField.shouldBe(Condition.visible).sendKeys(returnForm);
        Selenide.sleep(500);
        searchFormField.shouldBe(Condition.visible).sendKeys(Keys.ENTER);
        Selenide.sleep(1000);
        SelenideElement uncheckedBox = $(By.xpath("//tbody[@id='formCalcEngineBindingForm:formCalcEngineTable_data']/tr/td[4]/div/div[2]/span[@class='ui-chkbox-icon ui-c']"));
        if (uncheckedBox.exists()) {
            formAndSourceAssignment.shouldBe(Condition.visible).click();
        }
        return this;
    }
}
