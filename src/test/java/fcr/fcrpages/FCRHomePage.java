package fcr.fcrpages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class FCRHomePage {

    private final FCRStagingJobDialog fcrSetJobDialogPage;
    private final FCRUploadSchemaDialog fcrUploadSchemaDialog;

    private SelenideElement title = $(By.xpath("//h1[@class='title'][text()='fcr Engine']"));
    public SelenideElement jobsDropDown = $(By.xpath("//span[text()=' JOBS ']/../span[contains(@class,'ng-trigger')]"));
    public SelenideElement addJobButton = $(By.cssSelector("app-jobs > div.action-buttons > button.action-buttons_button.mat-fab.mat-accent > span > mat-icon"));
    private SelenideElement datasetsDropDown = $(By.xpath("//span[text()=' DATASETS ']/../span[contains(@class,'ng-trigger')]"));
    private SelenideElement schemasDropDown = $(By.xpath("//span[text()='SCHEMAS']/../../span[contains(@class,'ng-trigger')]"));
    private SelenideElement addSchemaButton = $(By.cssSelector("app-schema > div > button.action-buttons_button.mat-fab.mat-accent > span > mat-icon"));
    private SelenideElement refreshJobButton = $(By.cssSelector("app-jobs > div.action-buttons > button.action-buttons_button.mat-fab.mat-basic > span > mat-icon"));

    private SelenideElement clearedJobStatus = $(By.xpath("//app-jobs-list/p[text()='No jobs are currently running']"));

    public FCRHomePage jobStatus(final String jobName, final String physicalBinding){
        SelenideElement job = $(By.xpath("//app-jobs-list/ul/li/span[3]/button[2]"));
        job.waitUntil(not(visible),120000,10000);

        clearedJobStatus.shouldBe(visible);
        fcrSetJobDialogPage.jobStatusHistory(jobName,physicalBinding);
        return this;
    }


    public FCRHomePage(
            final FCRStagingJobDialog fcrSetJobDialogPage,
            final FCRUploadSchemaDialog fcrUploadSchemaDialog) {
        this.fcrSetJobDialogPage = fcrSetJobDialogPage;
        this.fcrUploadSchemaDialog = fcrUploadSchemaDialog;
    }

    public FCRHomePage addScema(final String schemaPath){
            schemasDropDown.shouldBe(Condition.visible).click();
            addSchemaButton.shouldBe(Condition.visible).click();
            fcrUploadSchemaDialog.uploadSchema(schemaPath);
            return this;
    }

}
