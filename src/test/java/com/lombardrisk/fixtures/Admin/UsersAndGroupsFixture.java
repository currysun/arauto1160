package com.lombardrisk.fixtures.Admin;

import com.lombardrisk.page.administration.UserGroupsPage;
import com.lombardrisk.page.administration.UsersPage;
import org.apache.commons.lang3.RandomStringUtils;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class UsersAndGroupsFixture {

    private final UsersPage usersPage;
    private final UserGroupsPage userGroupsPage;

    public UsersAndGroupsFixture(final UsersPage usersPage,final UserGroupsPage userGroupsPage) {
        this.usersPage = usersPage;
        this.userGroupsPage = userGroupsPage;
    }

    public UsersAndGroupsFixture addUser(final String userName) {
        usersPage
                .open()
                .addUser(userName, userName + "@" + RandomStringUtils.randomAlphanumeric(5) + ".com");

        return this;
    }

    public UsersAndGroupsFixture addUserToGroup(final String newUserGroup, final String userGroup, final String newUser){
        userGroupsPage
                .open()
                .addNewUserGroup(newUserGroup, userGroup + randomAlphanumeric(5));
        userGroupsPage
                .addNewUserToGroup(newUserGroup, newUser);
        return this;
    }
}
