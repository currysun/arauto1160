@AM-CommentsAndAttachments
Feature: As a User, I want to be able to create/view/delete Return/Cell level comments when in Analysis Module

  AR-3310/AR-3314/AR-3315/AR-3316
  As a User, I want to be able to add attachment to comments
  when in Analysis Module performing analysis on a return at Return level

  AR-4185 As a User, I want to be able to open cell details on any comment

  Background:
    Given a user is logged in with username "admin" and password "password"
    And I click Analysis Module button from the header

  @AR-3048
  @AM-ReturnCommentsAndAttachments
  Scenario: View comment for a Return where there are no existing comments on Variance Analysis Grid
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "AL" and reference date 31/08/2017
    And I create a Variance Analysis for previous reference date 28/02/2017
    When I Click on Comments button
    Then I should see Return-level comments dialog
    And I should see "There aren't any comments yet"

  @AR-3048
  @Pipeline-WIP @AM-ReturnCommentsAndAttachments
  Scenario: Create first comment for a Return where there are no existing comments on Variance Analysis Grid
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "AD" and reference date 31/07/2017
    And I create a Variance Analysis for previous reference date 30/04/2017
    When I add a comment "First comment"
    Then the "First comment" appears with the following details
      | ADMIN                                    |
      | VARIANCE                                 |
      | Previous 30/04/2017 - Current 31/07/2017 |
      | First comment                            |
    And I delete top comment from the list

  @AR-3049
  @Pipeline-WIP @AM-ReturnCommentsAndAttachments
  Scenario: Create,view and delete comment for a Return where there are existing comments on Variance Analysis Grid
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "AD" and reference date 31/07/2017
    And I create a Variance Analysis for previous reference date 30/04/2017
    When I add a comment "Second comment"
    Then the "Second comment" appears with the following details
      | ADMIN                                    |
      | VARIANCE                                 |
      | Previous 30/04/2017 - Current 31/07/2017 |
      | Second comment                           |
    And I delete top comment from the list
    Then "Second comment" is not shown in the list of comments

  @AR-3050
  @AM-ReturnCommentsAndAttachments
  Scenario: Comments are not shown under a different Reporting period on Variance Analysis Grid
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "AD" and reference date 31/01/2017
    And I create a Variance Analysis for previous reference date 30/11/2007
    When I Click on Comments button
    Then I should see Return-level comments dialog
    And I should see "There aren't any comments yet"

  @AR-3051
  @AM-ReturnCommentsAndAttachments
  Scenario: Create/Edit a comment but not save on Variance Analysis Grid
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "AD" and reference date 30/04/2017
    And I create a Variance Analysis for previous reference date 31/01/2017
    When I Click on Comments button
    When I type a comment "My Unsaved comment"
    And I close the comment dialog
    Then I should see "There aren't any comments yet"

  @AR-3052
  @AM-ReturnCommentsAndAttachments
  Scenario: View comments for a Return where there are no existing comments on Trend Analysis Grid
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "BE" and reference date 30/06/2017
    And I create a Date Range Trend Analysis for the previous reference date 31/01/2017
    When I Click on Comments button
    Then I should see Return-level comments dialog
    And I should see "There aren't any comments yet"

  @AR-3052
  @Pipeline-WIP @AM-ReturnCommentsAndAttachments
  Scenario: Create first comment for a Return where there are no existing comments on Trend Analysis Grid
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "BE" and reference date 31/05/2017
    And I create a Date Range Trend Analysis for the previous reference date 31/01/2017
    When I add a comment "First comment"
    Then the "First comment" appears with the following details
      | ADMIN                         |
      | TRENDS                        |
      | From 31/01/2017 to 31/05/2017 |
      | First comment                 |

  @AR-3053
  @Pipeline-WIP @AM-ReturnCommentsAndAttachments
  Scenario: Create view and delete comment for a Return where there are existing comments on Trend Analysis Grid
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "BE" and reference date 31/05/2017
    And I create a Date Range Trend Analysis for the previous reference date 31/01/2017
    When I add a comment "Second comment"
    Then the "Second comment" appears with the following details
      | ADMIN                         |
      | TRENDS                        |
      | From 31/01/2017 to 31/05/2017 |
      | Second comment                |
    And I delete top comment from the list
    Then "Second comment" is not shown in the list of comments

  @AR-3054
  @AM-ReturnCommentsAndAttachments
  Scenario: Comments are not shown under a different Reporting period on Trend Analysis Grid
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "BE" and reference date 30/04/2017
    And I create a Date Range Trend Analysis for the previous reference date 31/01/2017
    When I Click on Comments button
    Then I should see Return-level comments dialog
    And I should see "There aren't any comments yet"

  @AR-3055
  @AM-ReturnCommentsAndAttachments
  Scenario: Create/Edit a comment but not save on Trend Analysis Grid
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "BE" and reference date 30/06/2017
    And I create a Date Range Trend Analysis for the previous reference date 31/01/2017
    When I Click on Comments button
    And I type a comment "My Unsaved comment"
    And I close the comment dialog
    Then I should see "There aren't any comments yet"

  @AR-3336
  @Cell
  Scenario: View comments for a cell where there are no existing comments on a return
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "AD" and reference date 30/04/2017
    And I create a Variance Analysis for previous reference date 31/01/2017
    When No comments are available
    Then I should see "There aren't any comments yet"

  @AR-3337
  @Pipeline-WIP @Cell
  Scenario: Create and view comment for a cell on a return on Variance Analysis Grid and verify Open Cell Details
    Given Analysis Module's selected return details are regulator "ECR", entity "ECR2999", return "CAR" and reference date 30/09/2015
    And I create a Variance Analysis for previous reference date 31/08/2015
    When I add a cell comment "Comment" on Variance Analysis Grid
    Then the cell comment "Comment" appears with the following details
      | ADMIN                                    |
      | VARIANCE                                 |
      | Previous 31/08/2015 - Current 30/09/2015 |
      | Comment                                  |
    And I click on Open Cell Details on the comment
    Then the cell details grid contains
      | DATE        | 30/09/2015 |
      | VALUE       | 1,834.00   |
      | DIFFERENCE  | 120.00     |
      | %DIFFERENCE | 7.00%      |
    And I delete top cell comment from the list

  @AR-3338
  @Cell
  Scenario: View comments for a cell where there are existing comments on a return
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "BE" and reference date 30/04/2017
    And I create a Variance Analysis for previous reference date 31/01/2017
    When I view the cell comments for a cell on the grid
    Then I should be presented with a list of comments for that cell for that period
      | Previous 31/01/2017 - Current 30/04/2017 |
      | From 31/01/2017 to 30/04/2017            |

  @AR-3339
  @Pipeline-WIP @Cell
  Scenario: Create a second comment for a cell and check that the second comment is displayed on top
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "BE" and reference date 30/04/2017
    And I create a Variance Analysis for previous reference date 31/01/2017
    When I add a cell comment "Second Comment" on Variance Analysis Grid
    Then the cell comment "Second Comment" appears with the following details
      | ADMIN                                    |
      | VARIANCE                                 |
      | Previous 31/01/2017 - Current 30/04/2017 |
      | Second Comment                           |
    And I delete top cell comment from the list
    Then "Second comment" is not shown in the list of cell comments

  @AR-3340
  @Pipeline-WIP @Cell
  Scenario: Create a comment for a cell on a return on Trend Analysis Grid
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "BE" and reference date 30/04/2017
    And I create a Date Range Trend Analysis for the previous reference date 31/01/2017
    When I add a cell comment "Comment" on Trend Analysis Grid
    Then the cell comment "Comment" appears with the following details
      | ADMIN                         |
      | TRENDS                        |
      | From 31/01/2017 to 30/04/2017 |
      | Comment                       |

  @AR-3341
  @Pipeline-WIP @Cell
  Scenario: Create a second comment for a cell on a return on Trend Analysis Grid
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "BE" and reference date 30/04/2017
    And I create a Date Range Trend Analysis for the previous reference date 31/01/2017
    When I add a cell comment "Second Comment" on Trend Analysis Grid
    Then the cell comment "Second Comment" appears with the following details
      | ADMIN                         |
      | TRENDS                        |
      | From 31/01/2017 to 30/04/2017 |
      | Second Comment                |
    And I delete top cell comment from the list
    Then "Second comment" is not shown in the list of cell comments

  @AR-3434
  @Pipeline @AM-ReturnCommentsAndAttachments @AnalysisModule
  Scenario: AM Commentary - Attachments - Add -Remove all the attachments that are uploaded successfully
    Given Analysis Module's selected return details are regulator "ECR", entity "ECR2999", return "CAR" and reference date 30/09/2015
    And I click the create button
    And I add a return level comment "Attachment comment" with attachments
    When I remove all attachments
    And I save my comment
    Then no attachments are available to view

  @AR-3434
  @Pipeline @AM-ReturnCommentsAndAttachments @AnalysisModule
  Scenario: AM Commentary - Attachments - Add -Remove all the attachments that are uploaded successfully
    Given Analysis Module's selected return details are regulator "ECR", entity "ECR2999", return "CAR" and reference date 30/09/2015
    And I click the create button
    And I add a return level comment "Attachment comment" with attachments
    Then I remove an attachment others should remain

  @AR-3320
  @Pipeline-WIP @AM-ReturnCommentsAndAttachments @Download
  Scenario: AM Commentary - Attachments - Download - Should be able to download and name should be default name
    Given Analysis Module's selected return details are regulator "ECR", entity "ECR2999", return "CAR" and reference date 30/09/2015
    And I click the create button
    Given I add a return level comment "Attachment comment" with attachments and save
    When I download the "documentAttachment.txt" attachment
    Then the attachment "documentAttachment.txt" be downloaded successfully