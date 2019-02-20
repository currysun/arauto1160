package com.lombardrisk.page.entitysetup;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.sleep;
import static java.util.Objects.requireNonNull;

public class EntitySetupDialog {

    private static final List<String> ALL_DEFAULT_PRIVILEGES =
            Arrays.asList("Return Maker", "Return Viewer", "Return Approver", "Return Submitter");
    private static final List<String> NO_APPROVER_DEFAULT_PRIVILEGES =
            Arrays.asList("Return Maker", "Return Viewer", "Return Submitter");

    private final SelenideElement addGroupButton =
            $(By.xpath("//div[@class='ui-tabs-panels']/div/div/div[@class='rpImageButtonAddClass']/a/img"));

    private final SelenideElement privilegesFilterInput = $(By.id("addUserGroupDlgForm:userInfoMenu_filter"));

    private final SelenideElement addUserGroupFilterInput = $(By.xpath("//div[@id='addUserGroupDlgForm:userInfoMenu']" +
            "/div[@class='ui-selectlistbox-filter-container']/input"));
    private final SelenideElement addUserGroupsButton =
            $(By.xpath("//*[@id=\"addUserGroupDlgForm:addUserGroup\"]/span"));

    private final SelenideElement configureReturnDialogSaveButton =
            $(By.xpath("//button[@id='configureUserPrivilegesforReturnsDlgForm:" +
                    "configureUserPrivilegesForReturnsDlgTabContainer:configureUserPrivilegesforReturns']/span"));
    private final SelenideElement addPrivilegeToGroupDialogButton =
            $(By.xpath("//*[@id='addPrivilegesToGroupDlgForm:addPrivilegesToGroup']/span"));

    private final SelenideElement assignReturnsTab = $(By.linkText("Assign Returns"));

    private final Config config;
    private final Notification notification;

    public EntitySetupDialog(final Config config, final Notification notification) {
        this.config = requireNonNull(config);
        this.notification = requireNonNull(notification);
    }

    public void open(final String entityName) {
        sleep(config.minTimeout());
        findEntity(entityName).click();
    }

    void openAssignReturnsTab() {
        assignReturnsTab
                .waitUntil(visible, config.maxTimeout())
                .click();
        notification.loadingProgressShouldNotBeDisplayed();

        assignReturnsTab
                .waitUntil(visible, config.maxTimeout())
                .shouldBe(visible);
        notification.loadingProgressShouldNotBeDisplayed();
    }

    EntitySetupDialog configureReturn(final ProductPackage productPackage, final String returnName) {
        findConfigureLink(productPackage, returnName)
                .waitUntil(visible, config.maxTimeout())
                .click();
        while (notification.loadingDialog().isDisplayed()) {
            notification.loadingProgressShouldNotBeDisplayed();
        }
        return this;
    }

    public EntitySetupDialog selectReturnToConfigure(final ProductPackage productPackage, final String returnName) {
        if (isNotActive(productPackage, returnName)) {
            findSelectableReturnCheckbox(productPackage, returnName).click();
        }
        return this;
    }

    public void setUpUserGroupsWithPrivileges(final List<String> userGroupNames) {
        setUpUserGroupsWithPrivileges(userGroupNames, ALL_DEFAULT_PRIVILEGES);
    }

    public void setUpUserGroupsWithPrivileges(
            final List<String> userGroupNames,
            final List<String> privileges) {
        addUserGroups(userGroupNames);

        List<String> defaultPrivileges = privileges;

        for (String group : userGroupNames) {
            if (group.contains("analysts")) {
                defaultPrivileges = NO_APPROVER_DEFAULT_PRIVILEGES;
            }
            configurePrivilegesToGroup(group, defaultPrivileges);
        }
        configureReturnDialogSaveButton.click();
    }

    private void configurePrivilegesToGroup(final String group, final List<String> privilegeNames) {
        addPrivilegeGroupButton(group).shouldBe(visible);
            addPrivilegeGroupButton(group).click();

            for (String privilegeName : privilegeNames) {
                if (isPrivilegeAvailable(privilegeName)) {
                    findPrivilegeCheckbox(privilegeName).click();
                }
            }
            addPrivilegeToGroupDialogButton.click();
        }


    private static boolean isPrivilegeAvailable(final String privilegeName) {
        $(By.xpath("//*[@id='addPrivilegesToGroupDlgForm:privilegeGroupsMenu']/div[1]"))
                .shouldBe(visible);
        List<String> privilegeRows = $$(By.xpath(
                "//*[@id='addPrivilegesToGroupDlgForm:privilegeGroupsMenu']/div[3]/table/tbody/tr")).texts();
        for (String privilegeRow : privilegeRows) {
            if (privilegeRow.contains(privilegeName)) {
                return true;
            }
        }
        return false;
    }

    private void addUserGroups(final List<String> userGroupNames) {
        addGroupButton.click();
        privilegesFilterInput.shouldBe(visible);

        List<String> nonConfiguredUserGroups = nonConfiguredGroupsList().texts();

        for (String groupName : userGroupNames) {
            if (nonConfiguredUserGroups.contains(groupName)) {
                addUserGroupCheckbox(groupName).click();
            }
        }
        if (addUserGroupFilterInput.isDisplayed()) {
            addUserGroupsButton.click();
        }
    }

    private boolean isNotActive(final ProductPackage productPackage, final String returnName) {
        return findSelectableReturnCheckbox(productPackage, returnName)
                .has(not(cssClass("ui-state-active")));
    }

    private SelenideElement findSelectableReturnCheckbox(final ProductPackage productPackage, final String returnName) {
        notification.loadingProgressShouldNotBeDisplayed();

        return findProduct(productPackage)
                .parent()
                .parent()
                .parent()
                .$(By.xpath("//td[text()='" + returnName + "']/../td/div[@class='ui-chkbox ui-widget']/div[2]"));
    }

    private static SelenideElement findConfigureLink(final ProductPackage productPackage, final String returnName) {
        return findProduct(productPackage)
                .parent().parent()
                .$(By.xpath("//td[text()='" + returnName + "']/../td[last()]"));
    }

    private static SelenideElement findProduct(final ProductPackage productPackage) {
        return $(By.xpath("//span[text()='" + productPackage + "']"));
    }

    private static SelenideElement addUserGroupCheckbox(final String userGroup) {
        return $(By.xpath("//td[contains(text(),'" + userGroup
                + "')]/../td/div/div[@class='ui-chkbox-box ui-widget ui-corner-all ui-state-default']"));
    }

    private static SelenideElement addPrivilegeGroupButton(final String groupName) {
        return $(By.xpath("//span[@class='ui-panel-title'][text()='" + groupName
                + "']/../..//span[@class='rpImageButtonContentClass'][text()='Add Privilege Group']/..//img"));
    }

    private static SelenideElement findPrivilegeCheckbox(final String privilegeName) {
        return $(By.xpath("//td[text()='" + privilegeName + "']/../td/div/div"));
    }

    private static ElementsCollection nonConfiguredGroupsList() {
        return $$(By.xpath("//*[@id='addUserGroupDlgForm:userInfoMenu']/div/table/tbody/tr/td[2]"));
    }

    private static SelenideElement findEntity(final String entityName) {
        return $(By.xpath("//label[normalize-space(text())='" + entityName + "']/../img"));
    }
}
