package com.lombardrisk.page.analysismodule.cellgroup;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.analysismodule.AnalysisModuleDialog;
import com.lombardrisk.page.analysismodule.AnalysisModuleWaiter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;

public abstract class ModifiableCellGroupDialog<T> extends AnalysisModuleDialog {

    protected final SelenideElement nameInput = $(By.xpath("//input[@formcontrolname='name']"));

    public ModifiableCellGroupDialog(final AnalysisModuleWaiter waiter) {
        super(waiter);
    }

    protected abstract T self();

    T setName(final String cellGroupName) {
        nameInput.shouldBe(Condition.visible);
        if ( cellGroupName.isEmpty()) {
            nameInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), cellGroupName);
        }
        else {
            nameInput.setValue(cellGroupName);
        }
        //$(By.xpath("//*[text()='" + cellGroupName + "'"));
        return self();
    }

    protected SelenideElement saveButton() {
        return $$x("//mat-dialog-actions/button").last();
    }

    void save() {
        saveButton()
                .shouldBe(enabled)
                .click();
    }

    void saveAndClose() {
        save();
        close();
    }

    void shouldHaveValidName() {
        nameInput.shouldHave(
                attribute("aria-invalid", "false"));
    }

    void shouldBeSavable() {
        saveButton().shouldBe(enabled);
    }

    void shouldNotBeSavable() {
        saveButton().shouldBe(disabled);
    }

    void shouldHaveInvalidName() {
        nameInput.shouldHave(
                attribute("aria-invalid", "true"));
    }

    void shouldNotHaveIncludedCells() {
        $$x("//*[text()='Included Cells']/..//*[@class='ag-body-container']/div")
                .shouldHaveSize(0);
    }
}
