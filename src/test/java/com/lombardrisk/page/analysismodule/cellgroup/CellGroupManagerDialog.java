package com.lombardrisk.page.analysismodule.cellgroup;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.WebElementsCollection;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.analysismodule.AnalysisModuleDialog;
import com.lombardrisk.page.analysismodule.AnalysisModuleWaiter;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.List;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.sleep;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class CellGroupManagerDialog extends AnalysisModuleDialog {

    private static final String[] SPECIAL_CHARACTERS = ">.,<@:~{}][+=-_)(*&^%$£".split(EMPTY);
    private static final String SCROLL_TOP_JS =
            "var dialogs = document.getElementsByClassName('mat-dialog-content');"
                    + "dialogs[dialogs.length - 1].scrollTop += 70";

    private final SelenideElement cellGroupsButton = $(By.xpath("//*[contains(text(),'Cell Groups')]"));
    private final SelenideElement title =
            $(By.xpath("//*[contains(text(),'Manage Cell Groups')]"));
    private final SelenideElement addCellGroupButton =
            $(By.xpath("//app-cell-groups-manager-dialog/app-dialog-button/button"));

    private final SelenideElement duplicateNameErrorMessage = $(By.cssSelector("div>mat-error"));

    private final SelenideElement editMenuItem = $(By.cssSelector(".ag-menu .edit-option #eName"));
    private final SelenideElement deleteMenuItem = $(By.xpath("//*[@id=\"eName\"][text()='Delete']"));
    private final SelenideElement makeDefautlMenuItem = $(By.cssSelector(".ag-menu .make-default-option #eName"));

    private final SelenideElement deleteDialogMessage = $(By.cssSelector("app-confirm-dialog mat-dialog-content"));
    private final SelenideElement deleteDialogYesButton = $(By.cssSelector("app-confirm-dialog > div > mat-dialog-actions > button:nth-child(1) > span"));
    private final SelenideElement deleteDialogCancelButton =
            $(By.cssSelector("app-confirm-dialog mat-dialog-actions button:nth-child(2)"));

    private final SelenideElement closeButton = $x("//mat-dialog-actions/button/span[1]");

    private final Config config;
    private final AddCellGroupDialog addCellGroupDialog;
    private final EditCellGroupDialog editCellGroupDialog;

    public CellGroupManagerDialog(
            final Config config,
            final AnalysisModuleWaiter waiter,
            final AddCellGroupDialog addCellGroupDialog,
            final EditCellGroupDialog editCellGroupDialog) {
        super(waiter);

        this.config = requireNonNull(config);
        this.addCellGroupDialog = requireNonNull(addCellGroupDialog);
        this.editCellGroupDialog = requireNonNull(editCellGroupDialog);
    }

    public void openReturnWithAllCellGroup(
            final ProductPackage productPackage,
            final int returnId,
            final String analysisType) {
        Selenide.open(config.fullAnalysisModuleUrl() + "/dashboard?"
                + "productPrefix=" + productPackage
                + "&formInstanceId=" + returnId
                + "&analysisType=" + analysisType
                + "&pageInstanceId=1"
                + "&cellGroupId=ALL");

        waiter().waitForFetch();
    }

    public CellGroupManagerDialog open() {
        cellGroupsButton
                .shouldBe(enabled)
                .click();

        shouldBeVisible();
        return this;
    }

    @Override
    public SelenideElement getTitle() {
        return title;
    }

    public void shouldDisplayErrorMessage(final String errorMessage) {
        duplicateNameErrorMessage.shouldHave(
                text(errorMessage));
    }

    public void createCellGroup(final String cellGroupName) {
        AddCellGroupDialog()
                .addAllCellsToCellGroup()
                .setName(cellGroupName)
                .save();

        shouldHaveCellGroup(cellGroupName);
        close();
    }

    public void close() {
        closeButton
                .shouldBe(enabled)
                .click();
    }

    public void tryCreateCellGroup(final String cellGroupName) {
        AddCellGroupDialog()
                .addAllCellsToCellGroup()
                .setName(cellGroupName);
    }

    public CellGroupManagerDialog createCellGroupsWithNoCells(final String cellGroupName) {
        AddCellGroupDialog()
                .setName(cellGroupName);
        return this;
    }

    private AddCellGroupDialog AddCellGroupDialog() {
        addCellGroupButton
                .shouldBe(enabled)
                .click();

        addCellGroupDialog.shouldBeVisible();
        return addCellGroupDialog;
    }

    public void shouldNotBeIncludedInCellGroup(
            final String cellGroupName,
            final List<String> removedCellNames) {
        openEditCellGroupDialog(cellGroupName)
                .shouldNotHaveCells(removedCellNames);
    }

    public CellGroupManagerDialog deleteCellGroup(final String cellGroupName) {
        openMenu(cellGroupName);

        deleteMenuItem
                .shouldBe(visible)
                .click();
        shouldDisplayConfirmMessage(cellGroupName);
        confirmDelete();

        shouldNotHaveCellGroup(cellGroupName);
        return this;
    }

    private void shouldDisplayConfirmMessage(final String cellGroup) {
        deleteDialogMessage.shouldHave(
                text("Are you sure you want to delete \"" + cellGroup + "\" Cell Group?"));
    }

    private void confirmDelete() {
        deleteDialogYesButton
                .shouldBe(enabled)
                .click();
        deleteDialogYesButton.waitUntil(not(visible), config.maxTimeout());
    }

    private static void shouldNotHaveCellGroup(final String cellGroupName) {
        $$(By.xpath("//div[@class='ag-body-container']//div[@col-id='name']"))
                .shouldBe(notInList(cellGroupName));
    }

    public CellGroupManagerDialog shouldHaveCellGroup(final String cellGroupName) {
        SelenideElement cellGroupItem =
                $x("//div[@class='ag-body-container']//div[@col-id='name'][text()='" + cellGroupName + "']");

        waiter().waitForVisibleAndDo(
                cellGroupItem,
                ignored -> scrollDownInDialog());
        return this;
    }

    private void scrollDownInDialog() {
        executeJavaScript(SCROLL_TOP_JS);

        sleep(config.minTimeout() / 2);
    }

    public void removeCellsForGroup(final String cellGroupName, final List<String> cellNames) {
        openEditCellGroupDialog(cellGroupName)
                .excludeCells(cellNames)
                .save();
    }

    public CellGroupManagerDialog makeDefault(final String cellGroupName) {
        title.shouldBe(visible);
        openMenu(cellGroupName);
        makeDefautlMenuItem.click();
        return this;
    }

    public void checkDeleteIsDisabled(final String cellGroupName) {
        openMenu(cellGroupName);

        deleteMenuItem.parent().shouldHave(cssClass("ag-menu-option-disabled"));
        deleteMenuItem.click();
        deleteMenuItem.shouldBe(visible);
    }

    public CellGroupManagerDialog deleteCellGroupWithoutConfirmation(final String cellGroupName) {
        openMenu(cellGroupName);

        deleteMenuItem
                .shouldBe(visible)
                .click();

        shouldDisplayConfirmMessage(cellGroupName);

        deleteDialogCancelButton.click();
        shouldHaveCellGroup(cellGroupName);
        return this;
    }

    private static CollectionCondition notInList(final String cellGroupName) {
        return new CollectionCondition() {
            @Override
            public boolean apply(@Nullable final List<WebElement> webElements) {
                return !isCellGroupInList(webElements, cellGroupName);
            }

            @Override
            public void fail(
                    final WebElementsCollection webElementsCollection,
                    final List<WebElement> list,
                    final Exception e,
                    final long l) {
                throw new AssertionError(e.getMessage(), e);
            }
        };
    }

    public void blankOutCellGroupName(final String cellGroupName) {
        openEditCellGroupDialog(cellGroupName)
                .setName(EMPTY);
    }

    public void renameTheCellGroup(final String currentCellGroupName, final String newCellGroupName) {
        openEditCellGroupDialog(currentCellGroupName)
                .setName(newCellGroupName);
    }

    private EditCellGroupDialog openEditCellGroupDialog(final String cellGroupName) {
        openMenu(cellGroupName);
        editMenuItem.click();

        editCellGroupDialog.shouldBeVisible();
        return editCellGroupDialog;
    }

    public void shouldDisplaySpecialCharacterNameError() {
        AddCellGroupDialog addCellGroupDialog = AddCellGroupDialog();
        addCellGroupDialog.addAllCellsToCellGroup();

        checkSpecialCharacters("Test", addCellGroupDialog);
    }

    void checkSpecialCharacters(
            final String cellGroupName,
            final ModifiableCellGroupDialog cellGroupDialog) {

        for (String specialChar : SPECIAL_CHARACTERS) {
            cellGroupDialog.setName(cellGroupName);
            cellGroupDialog.shouldHaveValidName();
            cellGroupDialog.shouldBeSavable();

            cellGroupDialog.setName(cellGroupName + specialChar);
            cellGroupDialog.shouldHaveInvalidName();
            cellGroupDialog.shouldNotBeSavable();
        }
    }

    public void shouldShowErrorWhenNameContainsSpecialCharacter(final String cellGroupName) {
        checkSpecialCharacters(cellGroupName, openEditCellGroupDialog(cellGroupName));
    }

    public void removeAllCells(final String cellGroup) {
        openEditCellGroupDialog(cellGroup)
                .excludeAllCells()
                .shouldNotBeSavable();
    }

    public void systemCellGroupIsDefaulted(final String cellGroup) {
        cellGroupDefaultTick(cellGroup).should(visible);
        openMenu(cellGroup);
        makeDefautlMenuItem.parent().shouldHave(Condition.cssClass("ag-menu-option-disabled"));
    }

    public void shouldNotHaveOptionToDeleteSystemCellGroup() {
        openMenu("All Cells");
        deleteMenuItem.shouldNotBe(visible);
    }

    private void openMenu(final String cellGroupName) {
        findManageCellGroupsMenuButton(cellGroupName)
                .waitUntil(visible, config.maxTimeout())
                .shouldBe(enabled)
                .click();
    }

    public void verifyReturnVersion(final String cellGroup) {
        cellGroupReturnVersion(cellGroup).shouldBe(visible);
    }

    private static SelenideElement findManageCellGroupsMenuButton(final String cellGroupName) {
        return $(By.xpath("//div[text()='" + cellGroupName + "']/../div[5]"));
    }

    private static SelenideElement cellGroupDefaultTick(final String cellGroupName) {
        return $(By.xpath("//div[text()='" + cellGroupName + "']/..//mat-icon[text()='check']"));
    }

    private static SelenideElement cellGroupReturnVersion(final String cellGroup) {
        return $x("//div[text()='" + cellGroup + "']/../div[3]//app-cell-group-version-cell-renderer");
    }

    private static boolean isCellGroupInList(
            final @Nullable List<WebElement> webElements,
            final String cellGroupName) {
        try {
            return webElements != null
                    && webElements.stream()
                    .map(WebElement::getText)
                    .anyMatch(cellGroupName::equals);
        } catch (StaleElementReferenceException var3) {
            return false;
        }
    }

    public void shouldNotContainAnyIncludedCells() {
        addCellGroupDialog.shouldNotHaveIncludedCells();
    }

    public void shouldNotBeSavable() {
        addCellGroupDialog.shouldNotBeSavable();
    }
}
