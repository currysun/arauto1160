@AnalysisModule @AnalysisModule-Privileges

Feature: General User access Privileges
  AR-2370/AR-3955/AR-2639
  As a User with appropriate privileges, I should be able to access Analysis Module
  As a User with appropriate privilege, I should be able to delete comments added by other users
  As an Administrator, I want to be able to allow certain Users the ability to delete comments/attachments created by other users

  @Pipeline @AR-4327
  Scenario: As a User with Privilege to access AM, I can access AM through Analysis button on Dashboard
    Given a user is logged in with username "AMUser" and password "password"
    When I click Analysis Module button from the header
    Then I should be able to access Analysis Module

  Scenario: As a User with Privilege to access AM, I can access AM through Variance button on Dashboard
    Given a user is logged in with username "AMUser" and password "password"
    And I select regulator "ECR", entity "ECR2999", return type "MKIR" on the dashboard
    When I click the Variance button on the return "MKIR" v5 with reference date 02/09/2017 from the dashboard
    Then I should be able to access Analysis Module
    And I close Analysis Module

  Scenario: As a User with Privilege to access AM, I can access AM through Trend button on Dashboard
    Given a user is logged in with username "AMUser" and password "password"
    And I select regulator "ECR", entity "ECR2999", return type "MKIR" on the dashboard
    When I click the Variance button on the return "MKIR" v5 with reference date 02/09/2017 from the dashboard
    Then I should be able to access Analysis Module
    And I close Analysis Module

  Scenario: As a User with Privilege to access AM, I can access AM through Variance button within a return
    Given a user is logged in with username "AMUser" and password "password"
    When I select regulator "ECR", entity "ECR2999", return type "MKIR" on the dashboard
    And I select the return "MKIR" v5 with reference date 02/09/2017
    And I click the Variance button from within a return
    Then I should be able to access Analysis Module
    And I close Analysis Module

  Scenario: As a User with Privilege to access AM, I can access AM through Trend button within a return
    Given a user is logged in with username "AMUser" and password "password"
    When I select regulator "ECR", entity "ECR2999", return type "MKIR" on the dashboard
    And I select the return "MKIR" v5 with reference date 02/09/2017
    And I click the Trends button from within a return
    Then I should be able to access Analysis Module
    And I close Analysis Module


  @Pipeline @AR-4328
  Scenario: As a User without privileges to access AM, then all AM buttons are disabled
    Given a user is logged in with username "NonAMUser" and password "password"
    Then I should not see Analysis Module button
    When I select regulator "ECR", entity "ECR2999", return type "MKIR" on the dashboard
    Then the Variance button is disabled for the return "MKIR" v5 with reference date 02/09/2017 from the dashboard
    And the Trend button is disabled for the return "MKIR" v5 with reference date 02/09/2017 from the dashboard
    And I select the return "MKIR" v5 with reference date 02/09/2017
    Then the Variance button is disabled within a return
    And the Trends button is disabled within a return
    And I close the return
    And I am logged out


  @Pipeline @AR-4376
  Scenario: User without delete comment privilege cannot delete another users return level comment Then User with delete comment privilege can delete the comment
    Given a user is logged in with username "AddComment" and password "password"
    And I click Analysis Module button from the header
    When Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 28/02/2017
    And I create a Variance Analysis for previous reference date 31/01/2017
    And I add a comment "Can I delete this"
    And a user is logged in with username "CantDelete" and password "password"
    And I click Analysis Module button from the header
    When Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 28/02/2017
    And I create a Variance Analysis for previous reference date 31/01/2017
    When I Click on Comments button
    Then the "Can I delete this" appears with the following details
      | ADDCOMMENT                               |
      | VARIANCE                                 |
      | Previous 31/01/2017 - Current 28/02/2017 |
      | Can I delete this                        |
    And the delete comment button is disabled for the return
    When a user is logged in with username "DeleteComment" and password "password"
    And I click Analysis Module button from the header
    When Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 28/02/2017
    And I create a Variance Analysis for previous reference date 31/01/2017
    When I Click on Comments button
    And I delete top comment from the list
    And I close Analysis Module

  @Pipeline @AR-4376
  Scenario: User without delete comment privilege cannot delete another users cell level comment Then User with delete comment privilege can delete the comment
    Given a user is logged in with username "AddComment" and password "password"
    And I click Analysis Module button from the header
    When Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 28/02/2017
    And I create a Variance Analysis for previous reference date 31/01/2017
    When I add a cell comment "Can I delete this" on Variance Analysis Grid
    And a user is logged in with username "CantDelete" and password "password"
    And I click Analysis Module button from the header
    When Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 28/02/2017
    And I create a Variance Analysis for previous reference date 31/01/2017
    When I view the cell comments for a cell on the grid
    Then the cell comment "Can I delete this" appears with the following details
      | ADDCOMMENT                               |
      | VARIANCE                                 |
      | Previous 31/01/2017 - Current 28/02/2017 |
      | Can I delete this                        |
    And the delete comment button is disabled for the cell
    When a user is logged in with username "DeleteComment" and password "password"
    And I click Analysis Module button from the header
    When Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 28/02/2017
    And I create a Variance Analysis for previous reference date 31/01/2017
    When I view the cell comments for a cell on the grid
    And the delete comment button is enabled
    And I delete top cell comment from the list

