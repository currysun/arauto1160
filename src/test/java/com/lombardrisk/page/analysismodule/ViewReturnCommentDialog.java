package com.lombardrisk.page.analysismodule;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.LocalDateTime;
import java.util.List;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.lombardrisk.page.analysismodule.grid.GridPage.AM_DATETIME_FORMATTER;

public class ViewReturnCommentDialog {

    private final SelenideElement appReturnCommentsManagerDialog = $("app-return-comments-manager-dialog");
    private final SelenideElement addButton = $(By.xpath("//mat-dialog-container//mat-icon"));
    private final SelenideElement confirmDelete =
            $(By.xpath("//mat-dialog-container/app-confirm-dialog/div/mat-dialog-actions/button[1]"));
    private final SelenideElement closeCommentDialog =
            $("mat-dialog-container > app-return-comments-manager-dialog > app-comments-manager-dialog > mat-dialog-actions > button > span");

    public void add() {
        addButton.click();
    }

    public void close() {
        closeCommentDialog.shouldBe(visible).click();
        closeCommentDialog.shouldNotBe(visible);
    }

    public void exists() {
        appReturnCommentsManagerDialog.should(exist);
    }

    public void shouldHaveDetails(final List<String> dataTableRows) {
        String expectedCommentUser = dataTableRows.get(0);
        String expectedAnalysisType = dataTableRows.get(1);
        String expectedAnalysisPeriod = dataTableRows.get(2);
        String expectedCommentText = dataTableRows.get(3);
        ElementsCollection appCommentCards = $$("app-return-comment-card");
        SelenideElement relevantComment = appCommentCards.get(0);

        relevantComment.$("div.header__item--grow>span[appCommentCardTitle]")
                .shouldHave(text(expectedCommentUser));
        relevantComment.$("div.header__item--grow>span[appCommentCardSubtitle]")
                .shouldHave(text(expectedAnalysisType));

        LocalDateTime expectedCommentDate = LocalDateTime.now();
        relevantComment.$("div.header__item--text-right>span[appCommentCardTimestamp]")
                .shouldHave(Condition.or(
                        "Has now timestamp or now timestamp + 1min",
                        text(AM_DATETIME_FORMATTER.format(expectedCommentDate)),
                        text(AM_DATETIME_FORMATTER.format(expectedCommentDate.plusMinutes(-1)))));
        relevantComment.$("mat-card-content>div[appCommentCardContent]>p").shouldHave(text(expectedCommentText));
        relevantComment.$("div.header__item--grow>span[appCommentCardSubtitle]")
                .shouldHave(text(expectedAnalysisPeriod));
    }

    public void shouldBeVisible() {
        appReturnCommentsManagerDialog.shouldBe(visible);
    }

    public void shouldNotHaveComment(String comment) {
        appReturnCommentsManagerDialog.shouldBe(visible).shouldNotHave(text(comment));
    }

    public void deleteTopComment() {
        ElementsCollection appCommentCards = $$("app-return-comment-card");
        SelenideElement relevantComment = appCommentCards.get(0);
        relevantComment.$("button.menu").click();
        $("button.mat-menu-item").click();
        confirmDelete.click();
    }

    public void deleteTopReturnCommentButtonIsDisabled() {
        ElementsCollection appCommentCards = $$("app-return-comment-card");
        SelenideElement relevantComment = appCommentCards.get(0);
        relevantComment.$("button.menu").click();
        $("button.mat-menu-item").shouldBe(disabled);
    }
}
