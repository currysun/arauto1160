package com.lombardrisk.page.returninstance;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.PageUtils;
import org.openqa.selenium.By;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import java.io.File;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static java.util.Objects.requireNonNull;

public class ImportDialog {

    private final Config config;

    public ImportDialog(final Config config) {
        this.config = requireNonNull(config);
    }

    public void upload(final String fileName) {
        $("div.fileUpload span.ui-fileupload-choose").click();
        try {
            UploadFile(fileName);
        } catch (Exception e) {
            // do nothing
        }
    }

    public void selectScaled() {
        $("table#importFileForm\\:applyScaleRadio td:nth-child(1) span.ui-radiobutton-icon").click();
    }

    public void selectNoScale() {
        $("table#importFileForm\\:applyScaleRadio td:nth-child(3) span.ui-radiobutton-icon").click();
    }

    public void importFile() {
        $("#importFileForm\\:importBtn").click();
    }

    public void UploadFile(final String fileName)
            throws InterruptedException, AWTException {

        Robot robotObj = new Robot();

        // This is the upload file directory, it should be exist.
        String uploadFileDir = config.importDir().toString();
        // Navigate to upload file located directory input text box.
        //this.pressTabKey(robotObj, 4);
        // This is the upload file name, it should also exist.
        String uploadFile = uploadFileDir + "\\" + fileName;
        // Copy file directory.
        this.copyStringToTargetInputBox(robotObj, uploadFile);
        // Click tab key six time to goto upload file input text box.
        //this.pressTabKey(robotObj, 6);
        // Copy file name
        this.copyStringToTargetInputBox(robotObj, uploadFile);

        // Navigate to Open button.
        this.pressTabKey(robotObj, 1);
        // Click the Open button.
        this.pressEnterKey(robotObj);
    }

    /* This method use Robot and system clipboard to copy text to the input text box.*/
    private void copyStringToTargetInputBox(Robot robot, String text) throws InterruptedException {
        // First copy text to system clipboard.
        StringSelection stringSel = new StringSelection(text);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Clipboard cb = tk.getSystemClipboard();
        cb.setContents(stringSel, stringSel);

        // Press enter to make it focus before copy text to it.

        this.pressEnterKey(robot);

        // Copy the text from system clipboard to input text box.
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    /* Implement press enter key keyboard action.*/
    private void pressEnterKey(Robot robotObj) throws InterruptedException {
        robotObj.keyPress(KeyEvent.VK_ENTER);
        robotObj.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(1000);
    }

    /* Implement press tab key keyboard action.*/
    private void pressTabKey(Robot robotObj, int pressCount) throws InterruptedException {
        for (int i = 0; i < pressCount; i++) {
            Thread.sleep(2000);
            robotObj.keyPress(KeyEvent.VK_TAB);
        }
    }
}