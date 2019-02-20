@AgileREPORTER @AR-CommentLog
Feature: Create Ad Hoc Comments
  Pre-requisite work in support of testing AR-12 workflow
  NOTE: creating users, user groups and entities, dependent on ECR and RBI product packages

  Background: Log in as an administrator and navigate to the Entity setup page
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"

  @AR-1482
  Scenario: Add Comments to Return from return instance page
    Given I open or create the return "CICDP" v1 with reference date 21/04/2017
    And I open the comment log from the return instance page
    And I submit a comment "Numbers need checking"
    Then the top comment should be "Numbers need checking"
    And it should be actioned by "wkapprover1_1"
    And it should have a timestamp
    And it should not have an attachment

  @AR-1482
  Scenario: Add Comments to Return from the dashboard page
    Given I open the comment log from the dashboard page
    And I submit a comment "Fields missing"
    Then the top comment should be "Fields missing"
    And it should be actioned by "wkapprover1_1"
    And it should have a timestamp
    And it should not have an attachment

  @AR-1483
  Scenario: Add Attachment to Return from the return instance page
    Given I open or create the return "CICDP" v1 with reference date 21/04/2017
    And I open the comment log from the return instance page
    And I submit a comment "Needs approving urgently" with an attachment
    Then the top comment should be "Needs approving urgently"
    And it should have an attachment
    And it should be actioned by "wkapprover1_1"
    And it should have a timestamp
  @AR-1482
  Scenario: Add Attachment to Return from the dashboard page
    Given I open the comment log from the dashboard page
    And I submit a comment "Deadline approaching!" with an attachment
    Then the top comment should be "Deadline approaching!"
    And it should have an attachment
    And it should be actioned by "wkapprover1_1"
    And it should have a timestamp

  @AR-1857
  Scenario: Filter for all logs
    Given I open or create the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "making this return ready for approval"
    And I open the comment log from the return instance page
    And I submit a comment "this is some comment"
    When I filter the comment log by "ALL"
    Then the top comments should be:
      | comment                               | user          | action             |
      | this is some comment                  | wkapprover1_1 | Comment            |
      | making this return ready for approval | wkapprover1_1 | Ready for Approval |
    When I filter the comment log by "APPROVAL"
    Then the top comments should be:
      | comment                               | user          | action             |
      | making this return ready for approval | wkapprover1_1 | Ready for Approval |

  @AR-1858
  Scenario: Filter for approval logs
    Given I open or create the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "making this return ready for approval"
    And I open the comment log from the return instance page
    And I submit a comment "this is some comment"
    When I filter the comment log by "APPROVAL"
    Then the top comments should be:
      | comment                               | user          | action             |
      | making this return ready for approval | wkapprover1_1 | Ready for Approval |

  @AR-1859
  Scenario: Filter for comment logs
    Given I open or create the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "making this return ready for approval"
    And I open the comment log from the return instance page
    And I submit a comment "this is some comment"
    When I filter the comment log by "COMMENT"
    Then the top comments should be:
      | comment              | user          | action  |
      | this is some comment | wkapprover1_1 | Comment |

  @AR-1860
  Scenario: Delete comment
    Given I open or create the return "CICDP" v1 with reference date 21/04/2017
    And I open the comment log from the return instance page
    And I submit a comment "I want to delete this comment"
    When I delete the top comment
    Then the comments should not contain "I want to delete this comment"

  @AR-1861
  Scenario: Show deleted comments
    Given I open or create the return "CICDP" v1 with reference date 21/04/2017
    Given I open the comment log from the return instance page
    And I submit a comment "I want to see this deleted comment"
    And I delete the top comment
    When I show deleted comments
    Then the top comments should be:
      | comment                            | user          | action  |
      | I want to see this deleted comment | wkapprover1_1 | Comment |

 # @AR-1878
 # @Regression
 # Scenario: Show historical revisions
 #   Given I open or create the return "CICDP" v1 with reference date 21/04/2017
 #   Given I open the comment log from the return instance page
 #   And I submit a comment "This is a comment before modifying the return"
 #   And I close the comment log
 #   And I directly edit one of the cells on the return
 #   And I open the comment log from the return instance page
 #   And I submit a comment "This is a comment after modifying the return"
 #   When I show historical revisions
 #   Then the top comments should be:
 #     | comment                                       | user          | action  | revision |
 #     | This is a comment after modifying the return  | wkapprover1_1 | Comment | 1        |
 #     | This is a comment before modifying the return | wkapprover1_1 | Comment | 0        |
  #TO DO: This tests needs to collect the value of the editted cell and then replace it post test,
  # there is also a dependency not only on the return existing but that this test has not been run previously i.e revision 0,1