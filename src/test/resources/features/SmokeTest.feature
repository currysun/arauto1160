@AgileREPORTER @Smoke @AR-Admin
Feature: As a developer I want to see if AgileREPORTER is installed and running correctly

  Scenario: I can login
    When a user is logged in with username "admin" and password "password"
    Then I see the dashboard

  Scenario: I can administer settings
    Given a user is logged in with username "admin" and password "password"
    When I am on the Entity setup page
    Then I should see "Entity and Return Administration"
    When I am on the Users page
    Then I should see "Add user"
    When I am on the UserGroups page
    Then I should see "Manage user groups"
