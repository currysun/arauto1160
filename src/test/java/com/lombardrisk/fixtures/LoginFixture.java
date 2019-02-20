package com.lombardrisk.fixtures;

import com.lombardrisk.page.LoginPage;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.MainMenu;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class LoginFixture {

    private final LoginPage loginPage;
    private final DashboardPage dashboardPage;
    private final Notification notification;
    private final MainMenu mainMenu;

    public LoginFixture(
            final LoginPage loginPage,
            final DashboardPage dashboardPage,
            final Notification notification,
            final MainMenu mainMenu) {
        this.loginPage = requireNonNull(loginPage);
        this.dashboardPage = requireNonNull(dashboardPage);
        this.notification = requireNonNull(notification);
        this.mainMenu = requireNonNull(mainMenu);
    }

    public void loginAsAdmin() {
        login("admin", "password");
    }

    public void loginAaOFSAA() {login("ofsaauser","password");}

    public void login(final String username, final String password) {
        dashboardPage.open();

        Optional<String> currentUsername = mainMenu.getCurrentUsername();

        if (currentUsername.filter(name -> !name.equals(username))
                .isPresent()) {
            mainMenu.openCurrentUser().logOut();
            notification.loadingProgressShouldNotBeDisplayed();
            loginPage.shouldBeDisplayed();
        }

        if (!mainMenu.isLoggedIn(username)) {
            loginPage
                    .open()
                    .login(username, password);
        }
    }
}
