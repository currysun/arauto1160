package com.lombardrisk.page.header;

import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import java.util.Optional;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class MainMenu {

    private final SettingsMenuItems settingsMenuItems;
    private final CurrentUserMenuItems currentUserMenuItems;

    private final SelenideElement settingsButton = $(By.id("formHeader:giSetting"));
    private final SelenideElement currentUserDropdown = $(By.id("formHeader:lblUser_button"));

    public MainMenu(final UserPreferencesDialog userPreferencesDialog) {
        currentUserMenuItems = new CurrentUserMenuItems(requireNonNull(userPreferencesDialog));
        settingsMenuItems = new SettingsMenuItems();
    }

    public SettingsMenuItems openSettings() {
        settingsButton.click();

        return settingsMenuItems;
    }

    public boolean isLoggedIn(final String username) {
        return currentUserDropdown.isDisplayed()
                && equalsIgnoreCase("hi " + username, currentUserDropdown.getText());
    }

    public CurrentUserMenuItems openCurrentUser() {
        currentUserDropdown.click();
        currentUserMenuItems.shouldBeDisplayed();
        return currentUserMenuItems;
    }

    public Optional<String> getCurrentUsername() {
        if (currentUserDropdown.isDisplayed()) {
            return Optional.of(StringUtils.removeStart(currentUserDropdown.getText(), "hi "));
        } else {
            return Optional.empty();
        }
    }

    public static class CurrentUserMenuItems {

        private final SelenideElement logoutButton = $(By.id("formHeader:btnLogout"));
        private final UserPreferencesDialog userPreferencesDialog;
        private final SelenideElement preferencesMenuItem = $(By.id("formHeader:btnPreferences"));

        CurrentUserMenuItems(final UserPreferencesDialog userPreferencesDialog) {
            this.userPreferencesDialog = userPreferencesDialog;
        }

        public void logOut() {
            logoutButton.click();
        }

        public UserPreferencesDialog openUserPreferences() {
            preferencesMenuItem.click();
            userPreferencesDialog.shouldBeOpen();

            return userPreferencesDialog;
        }

        public void shouldBeDisplayed() {
            logoutButton.shouldBe(visible);
        }
    }

    public static class SettingsMenuItems {

        private final AdministrationSubMenu administrationSubMenu = new AdministrationSubMenu();

        private final SelenideElement administrationMenuItem = $(By.linkText("Administration"));
        private final SelenideElement entitySetupMenuItem = $(By.linkText("Entity Setup"));

        public AdministrationSubMenu administration() {
            administrationMenuItem.click();

            return administrationSubMenu;
        }

        public void entitySetup() {
            entitySetupMenuItem.click();
        }

        public static class AdministrationSubMenu {

            private final SelenideElement approvalTemplatesMenuItem = $(By.linkText("Approval Workflow Templates"));
            private final SelenideElement userGroupsMenuItem = $(By.linkText("User Groups"));
            private final SelenideElement usersMenuItem = $(By.linkText("Users"));
            private final SelenideElement manageUsersMenuItem = $(By.linkText("Manage Users"));
            private final SelenideElement commentsItem = $(By.linkText("Comments"));
            private final SelenideElement calcEnginesMenuItem = $(By.linkText("Calculation Engines"));
            private final SelenideElement configPackageBindingItem = $(By.linkText("Config Package Binding"));
            private final SelenideElement securitySettings = $(By.linkText("Security Settings"));

            public void approvalWorkflowTemplates() {
                approvalTemplatesMenuItem.click();
            }

            public void userGroups() {
                userGroupsMenuItem.click();
            }

            public void users() {
                usersMenuItem.click();
                manageUsersMenuItem.click();
            }

            public void calculationEngines() {
                calcEnginesMenuItem.click();
            }

            public void configPackageBinding() {
                configPackageBindingItem.click();
            }

            public void comments() {
                commentsItem.click();
            }

            public void shouldNotHaveComments() {
                commentsItem.shouldNotBe(visible);
            }

            public void securitySettings(){
                securitySettings.click();
            }
        }
    }
}
