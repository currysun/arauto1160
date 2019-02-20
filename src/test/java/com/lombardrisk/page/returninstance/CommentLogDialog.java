package com.lombardrisk.page.returninstance;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static com.lombardrisk.page.PageUtils.clickCheckbox;
import static com.lombardrisk.page.approval.ApprovalStatus.toApprovalStatus;
import static java.util.Objects.requireNonNull;

public class CommentLogDialog {

    private static final int FIRST_ROW = 1;
    private static final int SECOND_ROW = 2;

    private static final int ATTACHMENT_COLUMN = 1;
    private static final int COMMENT_COLUMN = 2;
    private static final int USER_COLUMN = 3;
    private static final int TIME_COLUMN = 4;
    private static final int ACTION_COLUMN = 5;
    private static final int REVISION_COLUMN = 6;
    private static final int DELETE_COLUMN = 7;

    private final SelenideElement title = $(By.id("commentLogDialog_title"));
    private final SelenideElement addCommentButton = $(By.id("commentLogForm:addCommentBtn"));
    private final SelenideElement closeButton = $(By.id("commentLogForm:closeCommentLog"));
    private final SelenideElement commentLogFilterDropdown = $(By.id("commentLogForm:selectFilter"));
    private final SelenideElement commentLogFilterLabel = $(By.id("commentLogForm:selectFilter_label"));
    private final SelenideElement showDeletedCommentsCheckbox = $(By.id("commentLogForm:showDeletedCheckbox"));
    private final SelenideElement showHistoricalRevisionsCheckbox = $(By.id("commentLogForm:initToZeroCheck"));
    private final SelenideElement commentLogTable = $(By.id("commentLogForm:commentLogTable"));

    private final AddCommentDialog addCommentDialog;
    private final Config config;

    @SuppressWarnings("unused")
    public enum Filter {
        ALL,
        APPROVAL,
        COMMENT
    }

    public CommentLogDialog(final AddCommentDialog addCommentDialog, final Config config) {
        this.addCommentDialog = requireNonNull(addCommentDialog);
        this.config = requireNonNull(config);
    }

    public void shouldBeDisplayed() {
        addCommentButton.shouldBe(visible);
    }

    public void addComment(final String comment) {
        openAddCommentDialog()
                .addComment(comment)
                .submit();
    }

    public void addCommentWithAttachment(final String comment, final String fileName) {
        openAddCommentDialog()
                .addAttachment(comment, fileName)
                .submit();
    }

    public void shouldHaveTopAction(final String expectedApprovalStatus) {
        findAction(FIRST_ROW).waitUntil(toApprovalStatus(expectedApprovalStatus).text(), config.minTimeout());
    }

    public CommentLogDialog shouldHaveEmptyTopComment() {
        findComment(FIRST_ROW).shouldBe(empty);
        return this;
    }

    public void shouldBeTopComment(final String comment) {
        findComment(FIRST_ROW).shouldHave(text(comment));
    }

    public CommentLogDialog shouldBeTopActionedBy(final String user) {
        findActionedBy(FIRST_ROW).shouldHave(text(user));
        return this;
    }

    public void shouldHaveTopTimeStamp() {
        findTimeStamp(FIRST_ROW).shouldNotBe(empty);
    }

    public void shouldBeTopAttachment(final Condition condition) {
        findAttachment(FIRST_ROW).shouldBe(condition);
    }

    public void close() {
        if (closeButton.isDisplayed()) {
            closeButton.click();
            title.shouldNotBe(visible);
        }
    }

    public void attachmentUploadShouldBeDisabled() {
        openAddCommentDialog()
                .attachmentUploadShouldBeDisabled();
    }

    public void shouldBePreventedFromAddingAttachment(final String fileName) {
        openAddCommentDialog()
                .tryToAddAttachment("", fileName)
                .attachmentShouldNotBeAdded()
                .shouldHaveNotificationDisplayed();
    }

    public void shouldHaveTopComments(final List<Comment> expectedComments) {
        getAllLogs().shouldHave(sizeGreaterThanOrEqual(expectedComments.size()));

        for (int i = 0; i < expectedComments.size(); i++) {
            Comment expectedComment = expectedComments.get(i);
            int rowIndex = FIRST_ROW + i;

            if (expectedComment.comment().isPresent()) {
                findComment(rowIndex).shouldHave(text(expectedComment.comment().get()));
            }
            if (expectedComment.user().isPresent()) {
                findActionedBy(rowIndex).shouldHave(text(expectedComment.user().get()));
            }
            if (expectedComment.action().isPresent()) {
                findAction(rowIndex).shouldHave(text(expectedComment.action().get()));
            }
            if (expectedComment.revision().isPresent()) {
                findRevision(rowIndex).shouldHave(exactText(expectedComment.revision().get()));
            }
            findTimeStamp(rowIndex).shouldNotBe(empty);
        }
    }

    public void shouldNotHaveComment(final String comment) {
        ElementsCollection allLogs = getAllLogs();
        allLogs.findBy(text(comment)).shouldNot(exist);
    }

    public void deleteTopComment() {
        String existingComment = findComment(SECOND_ROW).text();

        findComment(FIRST_ROW).shouldNotHave(exactText(existingComment));
        findDeleteButton(FIRST_ROW).find(By.tagName("img")).click();
        findComment(FIRST_ROW).shouldNotHave(exactText(existingComment));
    }

    public void showDeletedComments() {
        clickCheckbox(showDeletedCommentsCheckbox);
    }

    public void showHistoricalRevisions() {
        clickCheckbox(showHistoricalRevisionsCheckbox);
    }

    public void filterBy(final Filter filter) {
        commentLogFilterDropdown.click();
        selectCommentTypeFilter(filter);
    }

    private AddCommentDialog openAddCommentDialog() {
        addCommentButton.click();
        sleep(500);
        addCommentDialog.shouldBeDisplayed();
        return addCommentDialog;
    }

    private static SelenideElement findComment(final int index) {
        return findEntry(index, COMMENT_COLUMN);
    }

    private static SelenideElement findAction(final int index) {
        return findEntry(index, ACTION_COLUMN);
    }

    private static SelenideElement findRevision(final int index) {
        return findEntry(index, REVISION_COLUMN);
    }

    private static SelenideElement findActionedBy(final int index) {
        return findEntry(index, USER_COLUMN);
    }

    private static SelenideElement findTimeStamp(final int index) {
        return findEntry(index, TIME_COLUMN);
    }

    private static SelenideElement findAttachment(final int index) {
        return findEntry(index, ATTACHMENT_COLUMN);
    }

    private static SelenideElement findDeleteButton(final int index) {
        return findEntry(index, DELETE_COLUMN);
    }

    private static SelenideElement findEntry(final int rowIndex, final int columnIndex) {
        return $(By.xpath("//*[@id='commentLogForm:commentLogTable_data']" +
                "/tr[" + rowIndex + "]/td[" + columnIndex + "]"));
    }

    private void selectCommentTypeFilter(final Filter filter) {
        SelenideElement selectFilter =
                $(By.xpath("//*[@id='commentLogForm:selectFilter_panel']/div/ul/li[text()='" + filter.name() + "']"));
        selectFilter.shouldBe(visible);
        selectFilter.click();
        commentLogFilterLabel.shouldHave(text(filter.name()));
    }

    private ElementsCollection getAllLogs() {
        commentLogTable.shouldBe(visible);
        return commentLogTable
                .find(By.cssSelector("tbody"))
                .findAll(By.cssSelector("tr"));
    }
}
