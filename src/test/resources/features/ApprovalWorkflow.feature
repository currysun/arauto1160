@AgileREPORTER @AR-WorkflowApprovalTemplates
Feature: Approvals Configuration: Workflow Template Setup/Edit

  Background:
    Given a user is logged in with username "admin" and password "password"
    And I am on the Approval Workflow Template page

  @AR-958
  Scenario: Create an approval template
    And I request a new template with the name "AT-REMOVE", the description "AT-REMOVE DESC"
    And a step with the name "STEP1", the approval count 2
    And another step with the name "STEP2", the approval count 3
    And another step with the name "STEP3", the approval count 4
    And another step with the name "STEP4", the approval count 5
    When I have created the new template
    Then my created template should be available

  @AR-959
  Scenario: Create an approval template with a duplicate name
    And I request a new template with the name "AT-REMOVE", the description "AT-REMOVE DESC"
    And a step with the name "STEP1", the approval count 2
    And another step with the name "STEP2", the approval count 3
    When I have created the new template
    And I request a new template with the same name
    And I try to create a new template
    Then an error should stop me from entering the same template name

  @AR-962
  Scenario: Workflow template name - enter no characters
    And I request a new template with the name "", the description "AT-REMOVE DESC"
    And a step with the name "STEP1", the approval count 2
    When I try to create a new template
    Then an error should stop me from leaving the template name blank

  @AR-963
  Scenario: approval template - enter no description
    And I request a new template with the name "AT-REMOVE", the description ""
    And a step with the name "STEP1", the approval count 2
    And another step with the name "STEP2", the approval count 3
    When I have created the new template
    Then my created template should be available

  @AR-966
  Scenario: approval template - Add step - enter no value for name of a step
    And I request a new template with the name "AT-REMOVE", the description "AT-REMOVE DESC"
    And a step with the name "", the approval count 2
    When I try to create a new template
    Then an error should stop me from entering a value in my approval step

  @AR-968
  Scenario: approval template - add step - enter duplicate for name
    And I request a new template with the name "AT-REMOVE", the description "AT-REMOVE DESC"
    And a step with the name "STEP1", the approval count 2
    And another step with the name "STEP1", the approval count 3
    When I try to create a new template
    Then an error should stop me from entering a duplicate step name

  @AR-1326
  Scenario: View an existing approval template - read only
    When I select any existing template
    Then my highlighted template should be in read only mode

  @AR-1034
  Scenario: Edit an existing approval template
    When I select any existing template
    And I edit the template name to "AT-EDITTED", the description to "AT-EDITTED DESC"
    And the first step name to "EDITTED STEP1"
    And I update the template
    Then all details should be updated

  @AR-1035
  Scenario: Cancel the creation of an approval template - Cancel
    And I request a new template with the name "AT-CANCELED", the description "AT-CANCELED DESC"
    And a step with the name "STEP1-CANCELED", the approval count 2
    And another step with the name "STEP2-CANCELED", the approval count 3
    When I press the cancel button
    Then all details entered should be removed from my template

  @AR-1035
  Scenario: Cancel the creation of an approval template - Add new template
    And I request a new template with the name "AT-CANCELED", the description "AT-CANCELED DESC"
    And a step with the name "STEP1-CANCELED", the approval count 2
    And another step with the name "STEP2-CANCELED", the approval count 3
    When I press the add a new template button from the left hand pane
    Then all details entered should be removed from my template

  @AR-1035
  Scenario: Cancel the creation of an approval template - View existing template
    When I request a new template with the name "AT-CANCELED", the description "AT-CANCELED DESC"
    And a step with the name "STEP1-CANCELED", the approval count 66
    And another step with the name "STEP2-CANCELED", the approval count 77
    Then all details entered should be different from the selected template

  @AR-1055
  Scenario: Cancel the creation of an approval template - Navigate away
    And I request a new template with the name "AT-CANCELED", the description "AT-CANCELED DESC"
    And a step with the name "STEP1-CANCELED", the approval count 2
    And another step with the name "STEP2-AT-CANCELED", the approval count 3
    When I navigate away from the template page and return
    Then all details entered should be removed from my template

  @AR-1056
  Scenario: Cancel the creation of an approval template - Refresh
    And I request a new template with the name "AT-CANCELED", the description "AT-CANCELED DESC"
    And a step with the name "STEP1-CANCELED", the approval count 2
    And another step with the name "STEP2-CANCELED", the approval count 3
    When I refresh the page
    Then all my details should still remain on screen