package com.lombardrisk.page.analysismodule;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.LocalDateTime;
import java.util.List;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.lombardrisk.page.analysismodule.grid.GridPage.AM_DATETIME_FORMATTER;

public class ViewCellCommentDialog {

    private final SelenideElement appCellCommentsManagerDialog = $("app-cell-comments-manager-dialog");
    private final SelenideElement addButton =
            $("app-cell-comments-manager-dialog app-dialog-button[icon=\"add\"] button");
    private final SelenideElement confirmDelete =
            $(By.xpath("//mat-dialog-container/app-confirm-dialog/div/mat-dialog-actions/button[1]"));
    private final SelenideElement closeButton = $(By.xpath(
            "//*[@id=\"mat-dialog-0\"]/app-cell-comments-manager-dialog/app-comments-manager-dialog/mat-dialog-actions/button/span"));

    public void add() {
        $("app-spinner").shouldNotBe(visible);
        addButton.shouldBe(enabled).click();
    }

    public void exists() {
        appCellCommentsManagerDialog.should(exist);
    }

    public void close() {
        closeButton.shouldBe(visible).click();
        closeButton.shouldNotBe(visible);
    }

    public void shouldHaveDetails(final List<String> dataTableRows) {
        String expectedCommentUser = dataTableRows.get(0);
        String expectedAnalysisType = dataTableRows.get(1);
        String expectedAnalysisPeriod = dataTableRows.get(2);
        String expectedCommentText = dataTableRows.get(3);

        ElementsCollection appCommentCards = $$("app-cell-comment-card");
        SelenideElement relevantComment = appCommentCards.get(0);

        relevantComment.$("div.header__item--grow>span[appCommentCardTitle]").shouldHave(text(expectedCommentUser));
        relevantComment.$("div.header__item--grow>span[appCommentCardSubtitle]").shouldHave(text(expectedAnalysisType));

        LocalDateTime expectedCommentDate = LocalDateTime.now();
        relevantComment.$("div.header__item--text-right>span[appCommentCardTimestamp]")
                .shouldHave(Condition.or(
                        "Has now timestamp or now timestamp - 1min",
                        text(AM_DATETIME_FORMATTER.format(expectedCommentDate)),
                        text(AM_DATETIME_FORMATTER.format(expectedCommentDate.plusMinutes(-1)))));
        relevantComment.$("mat-card-content>div[appCommentCardContent]>p").shouldHave(text(expectedCommentText));
        relevantComment.$("div.header__item--grow>span[appCommentCardSubtitle]")
                .shouldHave(text(expectedAnalysisPeriod));
    }

    public void shouldBeVisible() {
        appCellCommentsManagerDialog.shouldBe(visible);
    }

    public void deleteTopComment() {
        ElementsCollection appCommentCards = $$("app-cell-comment-card");
        SelenideElement relevantComment = appCommentCards.get(0);
        relevantComment.$("button.menu").click();
        $("button.mat-menu-item").click();
        confirmDelete.click();
    }

    public void shouldHavePeriod(List<String> dataTable) {
        $("app-cell-comment-card").shouldBe(visible);
        ElementsCollection appCommentCards = $$("app-cell-comment-card");
        String expectedVarianceAnalysisPeriod = dataTable.get(0);
        String expectedTrendAnalysisPeriod = dataTable.get(1);
        for (SelenideElement comment : appCommentCards) {
            String period = comment.$("div.header__item--grow>[appCommentCardSubtitle]").getText();
            if (period.contains("Previous")) {
                comment.$("div.header__item--grow>[appCommentCardSubtitle]")
                        .shouldHave(text(expectedVarianceAnalysisPeriod));
            } else {
                comment.$("div.header__item--grow>[appCommentCardSubtitle]")
                        .shouldHave(text(expectedTrendAnalysisPeriod));
            }
        }
    }

    public void shouldNotHaveComment(String comment) {
        appCellCommentsManagerDialog.shouldBe(visible).shouldNotHave(text(comment));
    }

    public void DeleteButtonShouldBeEnabled() {
        ElementsCollection appCommentCards = $$("app-cell-comment-card");
        SelenideElement relevantComment = appCommentCards.get(0);
        relevantComment.$("button.menu").shouldBe(enabled);
    }

    public void deleteTopCellCommentButtonIsDisabled() {
        ElementsCollection appCommentCards = $$("app-cell-comment-card");
        SelenideElement relevantComment = appCommentCards.get(0);
        relevantComment.$("button.menu").shouldNotBe(disabled);
    }
}
