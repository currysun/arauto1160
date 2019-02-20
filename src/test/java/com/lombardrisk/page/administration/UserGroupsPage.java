package com.lombardrisk.page.administration;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.MainMenu;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.lombardrisk.page.PageUtils.setValue;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class UserGroupsPage {

    private final SelenideElement addUserGroupButton = $(By.xpath("//form/*[@class='rpImageButtonAddClass']/a/img"));
    private final SelenideElement userGroupNameInput = $(By.id("addUserGroupDlgForm:usergroupName"));
    private final SelenideElement userGroupDescriptionInput = $(By.id("addUserGroupDlgForm:description"));
    private final SelenideElement userGroupSaveButton = $(By.xpath("//*[@id='addUserGroupDlgForm:addUserGroup']"));

    private final SelenideElement addButton = $(By.id("addUserToGroupDlgForm:addUserToGroup"));

    private final MainMenu mainMenu;
    private final Notification notification;

    public UserGroupsPage(final MainMenu mainMenu, final Notification notification) {
        this.mainMenu = requireNonNull(mainMenu);
        this.notification = requireNonNull(notification);
    }

    public UserGroupsPage open() {
        mainMenu.openSettings()
                .administration().userGroups();
        return this;
    }

    public void addNewUserToGroup(final String groupName, final String userName) {
        if (findUserItem(groupName, userName).is(not(visible))) {
            findAddUserButton(groupName).click();
            findUserNameItemCheckbox(userName).click();

            addButton.click();

            notification.shouldCloseGrowlNotification();
        }
    }

    public void addNewUserGroups(final List<String> userGroups) {
        for (String groupName : userGroups) {
            if (!findUserGroupNameHeader(groupName).isDisplayed()) {

                String userGroupDescription = "UG Desc" + " - " + randomAlphanumeric(5);
                addNewUserGroup(groupName, userGroupDescription);
            }
        }
    }

    public void addNewUserGroup(final String groupName, final String groupNameDescription) {
        if (!findUserGroupNameHeader(groupName).isDisplayed()) {
            addUserGroupButton.click();
            setValue(userGroupNameInput, groupName);

            setValue(userGroupDescriptionInput, groupNameDescription);
            userGroupSaveButton.click();

            notification.shouldCloseGrowlNotification();

            findUserGroupNameHeader(groupName).shouldBe(visible);
        }
    }

    private static SelenideElement findUserGroupNameHeader(final String groupName) {
        return $(By.xpath("//*/span[text()='" + groupName + "']"));
    }

    private static SelenideElement findUserItem(final String groupName, final String userName) {
        return $(By.xpath("//div/div/span[text()='" + groupName
                + "']/../..//span[@class='rpImageButtonContentClass'][text()='" + userName + "']"));
    }

    private static SelenideElement findAddUserButton(final String groupName) {
        return $(By.xpath("//*/span[text()='" + groupName + "']/../../div/table//tr/td[last()]//a/img"));
    }

    private static SelenideElement findUserNameItemCheckbox(final String userName) {
        return $(By.xpath("//div[@id='addUserToGroupDlgForm:userInfoMenu']/div[3]//td[2][text()='"
                + userName + "']/../td/div"));
    }
}
