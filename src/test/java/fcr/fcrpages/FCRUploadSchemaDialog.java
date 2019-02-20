package fcr.fcrpages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class FCRUploadSchemaDialog {

    private SelenideElement title = $(By.xpath("//h3[text()='Upload schema']"));
    private SelenideElement uploadLinkInput =
            $(By.xpath("//app-schema-upload-dialog/mat-dialog-content/input"));

    public FCRUploadSchemaDialog uploadSchema(final String schemPath) {
        uploadLinkInput.shouldBe(Condition.visible).sendKeys(schemPath);
        return this;
    }
}
