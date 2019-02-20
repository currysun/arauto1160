package com.lombardrisk.page.administration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.PageUtils;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.MainMenu;
import org.openqa.selenium.By;

import java.util.*;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.lombardrisk.page.PageUtils.setValue;
import static java.util.Objects.requireNonNull;

public class UsersPage {

    private static final String ULTRA_SECURE_UNBREAKABLE_PASS = "password";

    private final SelenideElement addUserButton = $(By.xpath("//span[text()='Add user']/../span[1]"));

    private final MainMenu mainMenu;
    private final Notification notification;

    public UsersPage(final MainMenu mainMenu, final Notification notification) {
        this.mainMenu = requireNonNull(mainMenu);
        this.notification = requireNonNull(notification);
    }

    public UsersPage open() {
        mainMenu.openSettings()
                .administration().users();
        return this;
    }

    public UsersPage addUsers(final List<String> userNames) {
        List<String> currentUserNames = getCurrentUsers();
        for (String userName : userNames) {
            boolean userDoesNotExist = currentUserNames
                    .stream()
                    .noneMatch(s -> s.equalsIgnoreCase(userName));

            if (userDoesNotExist) {
                String email = userName + "@lrm.com";
                addUser(userName, email);
            }
        }
        return this;
    }

    public UsersPage addUser(final String user, final String email) {
        List<String> currentUserNames = getCurrentUsers();
        boolean userDoesNotExist = currentUserNames
                .stream()
                .noneMatch(s -> s.equalsIgnoreCase(user));

        if (userDoesNotExist) {
            addUserButton.click();

            setValue(userNameInput(), user);
            setValue(userEmailInput(), email);

            //createPasswordCheckbox().click();
            setValue(passwordInput(), ULTRA_SECURE_UNBREAKABLE_PASS);
            setValue(confirmPasswordInput(), ULTRA_SECURE_UNBREAKABLE_PASS);

            activationCheckbox().shouldBe(Condition.visible);

            saveButton().click();
            notification.shouldCloseGrowlNotification();
        }
        return this;
    }

    private static SelenideElement saveButton() {
        return $(By.id("userDetailForm:confirmUserEdit"));
    }

    private static SelenideElement activationCheckbox() {
        return $(By.xpath("//div[@id='userDetailForm:userActive']" +
                "/div[@class='ui-chkbox-box ui-widget ui-corner-all ui-state-default ui-state-active']"));
    }

    private static SelenideElement confirmPasswordInput() {
        return $(By.id("userDetailForm:userConfirmPassword"));
    }

    private static SelenideElement passwordInput() {
        return $(By.id("userDetailForm:userNewPassword"));
    }

    private static SelenideElement createPasswordCheckbox() {
        return $(By.id("userDetailForm:createPassword"));
    }

    private static SelenideElement userEmailInput() {
        return $(By.id("userDetailForm:userEmail"));
    }

    private static SelenideElement userNameInput() {
        return $(By.id("userDetailForm:user"));
    }

    private static SelenideElement accountUnlock() {
        return $(By.id("userDetailForm:unlockBtn"));
    }
    private static SelenideElement userActive() {
        return $(By.id("userDetailForm:userActive"));
    }

    private static SelenideElement removeSuspension() {
        return $(By.id("userDetailForm:activateBtn"));
    }




    private static SelenideElement nextPage = $(By.xpath("//span[contains(@class,'ui-paginator-next')]"));
    private static SelenideElement firstPage = $(By.xpath("//*[@id='userInfo_paginator_bottom']/span[3]/span[1]"));


    public List<String> getCurrentUsers() {
        List<String> currentUsers = new ArrayList<>();

        //ElementsCollection pages = $$(By.xpath("//*[@id='userInfo_paginator_bottom']/span[3]//span"));
        boolean flag = true;

        do{
            notification.loadingProgressShouldNotBeDisplayed();
            currentUsers.addAll(getCurrentUserNames().texts());
            flag = false;
            if(!PageUtils.isDisabled(nextPage)){
                nextPage.click();
                notification.loadingProgressShouldNotBeDisplayed();
                flag = true;
            }
        }while (!PageUtils.isDisabled(nextPage) || flag);

//        firstPage.click();
//        notification.loadingProgressShouldNotBeDisplayed();
//        currentUsers.addAll(getCurrentUserNames().texts());
//        while (!nextPage.attr("class").contains("disabled")) {
//            nextPage.click();
//            notification.loadingProgressShouldNotBeDisplayed();
//            currentUsers.addAll(getCurrentUserNames().texts());
//        }

//        for (SelenideElement page : pages) {
//            page.click();
//            notification.loadingProgressShouldNotBeDisplayed();
//            currentUsers.addAll(getCurrentUserNames().texts());
//            while (nextPage.isDisplayed()) {
//                nextPage.click();
//                notification.loadingProgressShouldNotBeDisplayed();
//                currentUsers.addAll(getCurrentUserNames().texts());
//            }
//        }

        return currentUsers;
    }

    private static ElementsCollection getCurrentUserNames() {
        return $$(By.xpath("//*[@id='userInfo_data']/tr/td[1]"));
    }

    private static ElementsCollection currentUsersInfo() {
        return $$(By.xpath("//*[@id='userInfo_data']/tr"));
    }

    private static Map<String,String> addUserInfo(final SelenideElement userInfo){
        Map<String,String> userStatus = new HashMap<>();

        userStatus.put("User ID",userInfo.find("td",1).getText());
        userStatus.put("Active",userInfo.find("td",2).getText());
        userStatus.put("Account Expiration Date",userInfo.find("td",3).getText());
        userStatus.put("Last Login Date",userInfo.find("td",4).getText());
        userStatus.put("Suspended",userInfo.find("td",5).getText());
        userStatus.put("Password Reset Date",userInfo.find("td",6).getText());
        userStatus.put("Locked",userInfo.find("td",7).getText());
        userStatus.put("Locked Date",userInfo.find("td",8).getText());

        return userStatus;
    }


    public Map<String,String> getUserStatus(final String user){
        Map<String,String> userStatus = new HashMap<>();

        firstPage.click();
        notification.loadingProgressShouldNotBeDisplayed();

        boolean userExist = currentUsersInfo().texts().stream().anyMatch(s -> s.equalsIgnoreCase(user));

        if(userExist){
            SelenideElement userInfo = currentUsersInfo().find(text(user));
            userStatus= addUserInfo(userInfo);
            return userStatus;
        }
        while (!nextPage.attr("class").contains("disabled")){
            nextPage.click();
            notification.loadingProgressShouldNotBeDisplayed();
            if(currentUsersInfo().texts().stream().anyMatch(s -> s.equalsIgnoreCase(user))){
                SelenideElement userInfo = currentUsersInfo().find(text(user));
                userStatus= addUserInfo(userInfo);
                return userStatus;
            }
        }
        return userStatus;
    }


    private static Optional<SelenideElement> findUserRowByName(final String user){
            return Optional.of(currentUsersInfo().find(text(user)));
    }

    public Optional<SelenideElement> getUser(final String user){
//        firstPage.click();
//        notification.loadingProgressShouldNotBeDisplayed();
        //boolean userExist = currentUsersInfo().texts().stream().anyMatch(s -> s.equalsIgnoreCase(user));
//        boolean userExist = getCurrentUserNames().texts().stream().anyMatch(s -> s.equalsIgnoreCase(user));
//        if(userExist){
//            return currentUsersInfo().find(text(user));
//        }
        //return findUserRowByName(user).get();


//        while (!PageUtils.isDisabled(nextPage)){
//            nextPage.click();
//            notification.loadingProgressShouldNotBeDisplayed();
//
//        }
//        return null;


        boolean flag = true;
        do{
            boolean userExist = getCurrentUserNames().texts().stream().anyMatch(s -> s.equalsIgnoreCase(user));
            if(userExist){
                return Optional.of(currentUsersInfo().find(text(user)));
            }

            flag = false;
            if(!PageUtils.isDisabled(nextPage)){
                nextPage.click();
                notification.loadingProgressShouldNotBeDisplayed();
                flag = true;
            }
        }while (!PageUtils.isDisabled(nextPage) || flag);

        return Optional.empty();

    }



    public UsersPage editLockStatus(final String user){
        getUser(user).get().find("img").click();
        notification.loadingProgressShouldNotBeDisplayed();
        accountUnlock().shouldBe(enabled).click();
        saveButton().click();
        notification.loadingProgressShouldNotBeDisplayed();
        notification.shouldCloseGrowlNotification();
        return this;
    }

    public UsersPage removeSuspensionStatus(final String user){
        getUser(user).get().find("img").click();
        notification.loadingProgressShouldNotBeDisplayed();
        removeSuspension().shouldBe(enabled).click();

        saveButton().click();
        notification.loadingProgressShouldNotBeDisplayed();
        notification.shouldCloseGrowlNotification();
        return this;
    }


}
