package fcr.fcrpages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class FCRStagingJobDialog {

    private static final DateTimeFormatter FCR_STAGING_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MMM/yyyy");

    private SelenideElement jobNameField = $(By.xpath("//div/input[@formcontrolname='jobName']"));
    private SelenideElement fileNameDropDown = $(By.xpath("//div/input[@formcontrolname='filePath']"));
    private SelenideElement schemaDropDown = $(By.xpath("//div/input[@formcontrolname='schemaName']"));
    private SelenideElement addSchemaButton = $(By.xpath("//app-schema/div/button[2]/span/mat-icon"));
    private SelenideElement datasetNameField = $(By.xpath("//div/input[@formcontrolname='datasetName']"));
    private SelenideElement entityCodeField = $(By.xpath("//div/input[@formcontrolname='entityCode']"));
    private SelenideElement uploadButton = $(By.xpath("//span[text()='Upload']"));

    private final FCRHomePage fcrHomePage;
    private final Config config;

    public FCRStagingJobDialog(final FCRHomePage fcrHomePage, final Config config) {
        this.fcrHomePage = fcrHomePage;
        this.config = config;
    }

    private SelenideElement dropOption(final String selection) {
        SelenideElement option = $(By.xpath("//span/small[text()='" + selection + "']"));
        return option;
    }

    private FCRStagingJobDialog headerIncluded(boolean header) {
        SelenideElement yes = $(By.xpath("//*[@formcontrolname='header']//mat-radio-button[1][@checked]"));
        SelenideElement no = $(By.xpath("//*[@formcontrolname='header']//mat-radio-button[2][@checked]"));

        if (header == true) {
            if (!yes.isDisplayed()) {
                $(By.xpath("//*[@formcontrolname='header']//mat-radio-button[1]")).click();
            }
        } else {
            $(By.xpath("//*[@formcontrolname='header']//mat-radio-button[2]")).click();
            no.shouldBe(Condition.visible);
        }
        return this;
    }

    public FCRStagingJobDialog setJob(
            final String jobName,
            final String fileName,
            final String schemaName,
            final String physicalBinding,
            final boolean header,
            final String entityCode,
            final LocalDate referenceDate) {

        Selenide.sleep(500);
        jobNameField.shouldBe(Condition.enabled).sendKeys(jobName);

        Selenide.sleep(1000);

        fileNameDropDown.shouldBe(Condition.visible).click();
        dropOption(fileName).shouldBe(Condition.visible).click();
        schemaDropDown.shouldBe(Condition.visible).click();
        dropOption(schemaName).shouldBe(Condition.visible).click();
        datasetNameField.shouldBe(Condition.visible).sendKeys(physicalBinding);
        headerIncluded(header);
        entityCodeField.shouldBe(Condition.visible).sendKeys(entityCode);

        setRefDate(FCR_STAGING_DATE_FORMAT.format(referenceDate));
        uploadButton.shouldBe(Condition.visible).click();

        Selenide.sleep(1000);
        return this;
    }

    private void setRefDate(final String refDate) {
        String[] dateParts = refDate.split("/");
        String day = dateParts[0].startsWith("0") ? dateParts[0].substring(1) : dateParts[0];
        String month = dateParts[1].toUpperCase();
        String year = dateParts[2];
        SelenideElement datetoggle =
                $(By.xpath("//mat-card/mat-card-content/mat-form-field[5]/div/div[1]/div[2]/mat-datepicker-toggle"));
        SelenideElement monthAndYearButton =
                $(By.xpath("//button[contains(@class,'mat-calendar-period-button')]/span"));
        SelenideElement setYear = $(By.xpath("//td/div[text()='" + year + "']"));
        SelenideElement setMonth = $(By.xpath("//td/div[text()='" + month + "']"));
        SelenideElement setDay = $(By.xpath("//td/div[text()='" + day + "']"));

        datetoggle.shouldBe(visible).click();
        monthAndYearButton.shouldBe(visible).click();
        setYear.shouldBe(visible).click();
        setMonth.shouldBe(visible).click();
        setDay.shouldBe(visible).click();
    }

    public void jobStatusHistory(final String jobName, final String physicalBinding) {
        SelenideElement completedJob = $(By.xpath("//div[text()='" + jobName + "']"));
        SelenideElement detailsDialog =
                $(By.xpath("//div[@class='ag-menu-option details-option ag-menu-option-active']"));
        SelenideElement detailsJobTitle =
                $(By.xpath("//app-job-detail-dialog/mat-dialog-content//*[contains(text(),'" + jobName + "')]"));
        SelenideElement datasetStatus = $(By.xpath("//app-job-detail-dialog/mat-dialog-content//*[contains(text(),'"
                + physicalBinding
                + "')]"));

        completedJob.parent()
                .$(By.xpath("//app-status-renderer/span/mat-icon[contains(@class,'success')]"))
                .shouldBe(visible);

        if (completedJob.isDisplayed()) {
            if (completedJob.parent()
                    .$(By.xpath("//app-status-renderer/span/mat-icon[contains(@class,'success')]"))
                    .isDisplayed()) {
                $(By.xpath("//div[text()='" + jobName + "']/../div[5]/span/span")).click();
                $(By.xpath("//div[@class='ag-theme-material'][1]/div[@class='ag-menu']")).shouldBe(visible).click();
                $(By.xpath("//app-job-detail-dialog/mat-dialog-content//*[contains(text(),'"
                        + jobName
                        + "')]")).shouldBe(visible).click();
                if (datasetStatus.parent()
                        .parent()
                        .$(By.xpath("//mat-icon[contains(@class,'success')]"))
                        .isDisplayed()) {
                    System.out.println("The " + jobName + " job has been successfully processed and validated");
                } else {
                    System.out.println("The " + jobName + " was unsuccessful, please check the input file and schema");
                }
            }
        }
    }
}
