package com.lombardrisk.page.analysismodule.cellgroup;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.analysismodule.AnalysisModuleWaiter;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$x;

public final class AddCellGroupDialog extends ModifiableCellGroupDialog<AddCellGroupDialog> {

    private final SelenideElement title = $x("//*[contains(text(),'Add Cell Group')]");

    private final SelenideElement addAllCellsButton =
            $x("//*[@class='controls']//div[2]//*[1][text()='chevron_right']");

    public AddCellGroupDialog(final AnalysisModuleWaiter waiter) {
        super(waiter);
    }

    @Override
    protected AddCellGroupDialog self() {
        return this;
    }

    @Override
    public SelenideElement getTitle() {
        return title;
    }

    AddCellGroupDialog addAllCellsToCellGroup() {
        addAllCellsButton
                .shouldBe(enabled)
                .click();
        return this;
    }

    @Override
    public SelenideElement getCloseSelenideElement() {
        return $x("//app-cell-group-editor-dialog/form/app-dialog-header/mat-toolbar/button");
    }
}
