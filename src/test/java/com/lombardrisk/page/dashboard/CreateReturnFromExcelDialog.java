package com.lombardrisk.page.dashboard;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.event.Notification;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.io.FileUtils.listFiles;
import static org.junit.Assert.fail;

public class CreateReturnFromExcelDialog {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateReturnFromExcelDialog.class);

    private final SelenideElement regulatorDropdown = $(By.id("formFilter:regulator"));

    private final SelenideElement uploadButton =
            $(By.xpath("//div[@id='createFromExcelForm:importFileUpload']/div/span"));
    private final SelenideElement uploadButtonInput = $(By.id("createFromExcelForm:importFileUpload_input"));
    private final SelenideElement importButton = $(By.id("createFromExcelForm:listimportBtn"));
    private final SelenideElement importFileLabel = $(By.id("createFromExcelForm:listuploadedFileName"));
    private final SelenideElement overrideButton = $(By.id("replaceconfirm"));
    private final SelenideElement importSuccessOkButton = $(By.id("createInformForm:confirmBtn"));

    private final Notification notification;
    private final Config config;

    public CreateReturnFromExcelDialog(final Notification notification, final Config config) {
        this.notification = requireNonNull(notification);
        this.config = requireNonNull(config);
    }

    public CreateReturnFromExcelDialog uploadReturn(final String returnName) {
        String productPackage = regulatorDropdown.getValue();
        Path productPackageDir = config.populatedReturnsDir().resolve(productPackage);
        uploadReturn(returnName, productPackageDir);
        return this;
    }

    public CreateReturnFromExcelDialog uploadReturn(final String returnName, final String subDirectory) {
        String productPackage = regulatorDropdown.getValue();
        uploadReturn(returnName, config.populatedReturnsDir().resolve(productPackage).resolve(subDirectory));
        return this;
    }

    public void importReturn() {
        notification.loadingProgressShouldNotBeDisplayed();
        importButton.click();
        notification.loadingProgressShouldNotBeDisplayed(config.importTimeout());

        if (overrideButton.isDisplayed()) {
            overrideButton.click();
            LOGGER.warn("Override existing return with file {}", importFileLabel.getText());
        }
        notification.loadingProgressShouldNotBeDisplayed(config.importTimeout());
        importSuccessOkButton.click();
        importSuccessOkButton.shouldNotBe(visible);
        notification.loadingProgressShouldNotBeDisplayed(config.importTimeout());
    }

    private void uploadReturn(final String returnName, final Path pathToTheReturn) {
        IOFileFilter returnFilter = new WildcardFileFilter(returnName + "*", IOCase.INSENSITIVE);
        File productPackageDir = pathToTheReturn.toAbsolutePath().toFile();
        Collection<File> foundReturns = listFiles(productPackageDir, returnFilter, null);

        if (foundReturns.isEmpty()) {
            fail(
                    String.format(
                            "Populated return with name matching '%s*' was not found under dir '%s'",
                            returnName, productPackageDir));
        }
        File returnToImport = foundReturns.iterator().next();
        uploadButton.shouldBe(visible);
        uploadButtonInput.sendKeys(returnToImport.toString());
        importFileLabel.waitUntil(text(returnToImport.getName()), config.importTimeout());
    }
}
