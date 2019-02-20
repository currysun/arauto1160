package com.lombardrisk.page.administration;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.PageUtils;
import org.openqa.selenium.By;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.apache.commons.lang3.StringUtils.prependIfMissing;

public class AttachmentConfigurationPage {

    private final SelenideElement fileSizeLimitInput = $(By.id("attachmentSizeLimit:sizeLimit_input"));
    private final SelenideElement fileSizeUnitDropdown = $(By.id("attachmentSizeLimit:sizeUnit"));
    private final SelenideElement saveFileSizeLimitButton = $(By.id("attachmentSizeLimit:saveLimit"));
    private final SelenideElement fileExtensionInput = $(By.id("attachmentFileExtensions:fileExtension"));
    private final SelenideElement addFileExtensionLink =
            $(By.id("attachmentFileExtensions:addFileExtensionToAllowedExtensions"));
    private final SelenideElement fileExtensionTable =
            $(By.xpath("//form[@id='attachmentFileExtensions']/table/tbody"));
    private final ElementsCollection allowedFileExtensions =
            fileExtensionTable.findAll(By.className("rpImageButtonClass"));
    private final SelenideElement confirmationDialog = $(By.id("confirmDialog"));
    private final SelenideElement warningHeader = $(By.className("warning"));
    private final URL url;

    public AttachmentConfigurationPage(final URL url) {
        this.url = url;
    }

    public void open() {
        Selenide.open(url + "/core/page/admin/attachmentUpload.xhtml");
    }

    public void setFileSizeLimit(final int newFileSizeLimit, final String newFileSizeUnit) {
        PageUtils.setValue(fileSizeLimitInput, String.valueOf(newFileSizeLimit));
        fileSizeUnitDropdown.selectOption(newFileSizeUnit);
        saveFileSizeLimitButton.click();
        Selenide.refresh();
    }

    public void fileSizeLimitShouldBe(final int fileSizeLimit, final String fileSizeUnit) {
        fileSizeLimitInput.shouldBe(exactValue(String.valueOf(fileSizeLimit)));
        fileSizeUnitDropdown.shouldBe(exactValue(fileSizeUnit));
    }

    public void allowedFileExtensionsShouldBe(final List<String> allowedFileExtensions) {
        this.allowedFileExtensions.shouldHaveSize(allowedFileExtensions.size());
        allowedFileExtensions.forEach(this::allowedFileExtensionsShouldContain);
    }

    public void allowedFileExtensionsShouldContain(final String fileExtension) {
        allowedFileExtensions.findBy(exactText(fileExtension)).should(exist);
    }

    public void allowedFileExtensionsShouldNotContain(final String fileExtension) {
        allowedFileExtensions.findBy(exactText(fileExtension)).shouldNot(exist);
    }

    public void addNewFileExtension(final String fileExtension) {
        PageUtils.setValue(fileExtensionInput, fileExtension);
        addFileExtensionLink.click();
        allowedFileExtensionsShouldContain(prependIfMissing(fileExtension, "."));
    }

    public void deleteFileExtension(final String fileExtension) {
        SelenideElement fileExtensionElement = findByExtension(fileExtension);
        if (fileExtensionElement.isDisplayed()) {
            fileExtensionElement.parent().$("a").click();
            confirmationDialog.shouldBe(visible);
            confirmationDialog.find(By.id("confirm")).click();
            fileExtensionElement.shouldNotBe(visible);
        }
    }

    public void setAllowedFileExtensions(final List<String> allowedFileExtensions) {
        final List<String> toDelete = this.allowedFileExtensions
                .stream()
                .map(SelenideElement::getText)
                .filter(fileExtension -> !allowedFileExtensions.contains(fileExtension))
                .collect(Collectors.toList());

        final List<String> toAdd = allowedFileExtensions
                .stream()
                .filter(fileExtension -> !this.allowedFileExtensions.findBy(exactText(fileExtension)).exists())
                .collect(Collectors.toList());

        toDelete.forEach(this::deleteFileExtension);
        toAdd.forEach(this::addNewFileExtension);
    }

    public void isAccessDenied() {
        warningHeader.shouldBe(visible);
        warningHeader.find(By.id("noPermission")).shouldBe(visible);
        fileSizeLimitInput.shouldNotBe(visible);
        fileExtensionInput.shouldNotBe(visible);
    }

    private SelenideElement findByExtension(final String extension) {
        return fileExtensionTable.$(By.xpath("//span[text()=\"" + extension + "\"]"));
    }
}
