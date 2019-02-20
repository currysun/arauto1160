@AgileREPORTER @AR-SubmitOverride @AR-SubmitOverrideVal
Feature: Submit Overrider privilege tests - validation failures

  Background:
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select a validation failure return of "FORMAS42" v3 with reference date 10/01/2017 and unlock the return

  @AR-2177
  Scenario: User logs on with submitter override privilege and submits an unlocked return from the dashboard
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    When I open the Export to "Export To iFile" dialog from the Dashboard
    And I select the "LRM111" entity and "FORMAS42" module with reference date 10/01/2017
    Then My export type is "Export to"
    And The "FORMAS42" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the dashboard page for return "FORMAS42" version "3" and reference date 10/01/2017
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-2178
  Scenario: User logs on with submitter override privilege and submits a locked return from the dashboard
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "FORMAS42" v3 with reference date 10/01/2017
    And I lock the return
    And I close the return
    When I open the Export to "Export To iFile" dialog from the Dashboard
    And I select the "LRM111" entity and "FORMAS42" module with reference date 10/01/2017
    Then My export type is "Export to"
    And The "FORMAS42" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the dashboard page for return "FORMAS42" version "3" and reference date 10/01/2017
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-2180
  Scenario: User logs on with submitter override privilege and submits an unlocked return from the return page
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "FORMAS42" v3 with reference date 10/01/2017
    When I open the Export to "Export To iFile" dialog from the return
    And I select the "LRM111" entity and "FORMAS42" module with reference date 10/01/2017
    Then My export type is "Export to"
    And The "FORMAS42" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the return instance page
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-2186
  Scenario: User logs on with submitter override privilege and submits a locked return from the return page
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "FORMAS42" v3 with reference date 10/01/2017
    And I lock the return
    When I open the Export to "Export To iFile" dialog from the return
    And I select the "LRM111" entity and "FORMAS42" module with reference date 10/01/2017
    Then My export type is "Export to"
    And The "FORMAS42" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the return instance page
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-2204
  Scenario: User logs on with Return Submitter privilege and attempts to submit a unlocked return from the dashboard
    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    When I open the Export to "Export To iFile" dialog from the Dashboard
    And I select the "LRM111" entity and "FORMAS42" module with reference date 10/01/2017
    Then My export type is "Export to"
    And The "FORMAS42" return can not be submitted

  @AR-2211
  Scenario: User logs on with Return Submitter privilege and attempts to submit a unlocked return from the return page
    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "FORMAS42" v3 with reference date 10/01/2017
    When I open the Export to "Export To iFile" dialog from the return
    And I select the "LRM111" entity and "FORMAS42" module with reference date 10/01/2017
    Then My export type is "Export to"
    And The "FORMAS42" return can not be submitted

  @AR-2213
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege, and attempts to submit a unlocked return from the dashboard
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    When I open the Export to "Export To iFile" dialog from the Dashboard
    And I select the "LRM111" entity and "FORMAS42" module with reference date 10/01/2017
    Then My export type is "Export to"
    And The "FORMAS42" return can not be submitted

  @AR-2217
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege, and attempts to submit a unlocked return from the return page
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "FORMAS42" v3 with reference date 10/01/2017
    When I open the Export to "Export To iFile" dialog from the return
    And I select the "LRM111" entity and "FORMAS42" module with reference date 10/01/2017
    Then My export type is "Export to"
    And The "FORMAS42" return can not be submitted

  @AR-2219
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege, and attempts to submit a locked return from the return page
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "FORMAS42" v3 with reference date 10/01/2017
    And I lock the return
    When I open the Export to "Export To iFile" dialog from the return
    And I select the "LRM111" entity and "FORMAS42" module with reference date 10/01/2017
    Then My export type is "Export to"
    And The "FORMAS42" return can not be submitted