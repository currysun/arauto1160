package com.lombardrisk.page.analysismodule.cellgroup;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.analysismodule.AnalysisModuleWaiter;
import com.lombardrisk.page.analysismodule.grid.GridPage;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_STRING_ARRAY;
import static org.assertj.core.api.Assertions.assertThat;

public final class EditCellGroupDialog extends ModifiableCellGroupDialog<EditCellGroupDialog> {

    private final SelenideElement title = $x("//*[contains(text(),'Edit Cell Group')]");

    private final SelenideElement excludeCellButton = $x("//div[@class='controls']/div[3]/button");
    private final SelenideElement excludeAllCellsButton = $x("//div[@class='controls']/div[4]/button");

    private final GridPage gridPage;

    public EditCellGroupDialog(final GridPage gridPage, final AnalysisModuleWaiter waiter) {
        super(waiter);
        this.gridPage = requireNonNull(gridPage);
    }

    @Override
    protected EditCellGroupDialog self() {
        return this;
    }

    @Override
    public SelenideElement getCloseSelenideElement() {
        return $x("//app-cell-group-editor-dialog/form/app-dialog-header/mat-toolbar/button");
    }

    @Override
    public SelenideElement getTitle() {
        return title;
    }

    public EditCellGroupDialog excludeAllCells() {
        excludeAllCellsButton.click();
        shouldNotHaveIncludedCells();

        return this;
    }

    EditCellGroupDialog excludeCells(final List<String> cellNames) {
        cellNames.forEach(this::excludeCell);

        return this;
    }

    private void excludeCell(final String cellName) {
        findIncludedCell(cellName).shouldBe(
                Condition.enabled).click();
        excludeCellButton.click();
    }

    private SelenideElement findIncludedCell(final String cellReference) {
        SelenideElement includedCells = $(By.xpath("//*[text()='Included Cells']/.."));
        gridPage.filterRightGridByCellReference(cellReference, 1);
        return includedCells.$(".ag-body div.ag-row div.ag-cell");
    }

    void shouldNotHaveCells(final List<String> removedCellNames) {
        gridPage.filterRightGridByCellReference(removedCellNames.get(0), 0);
    }

    private static ElementsCollection findAllIncludedCells() {
        return $$x("//app-cell-group-cells-select/div[3]//div[@class='ag-body-container']//div[@col-id='name']");
    }
}
