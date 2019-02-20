@AgileREPORTER @AR-SubmitOverride @AR-SubmitOverrideXVal
Feature: Submit Overrider privilege tests - cross validation failures

  Background:
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select a cross validation failure return of "AD" v2 with reference date 30/06/2017 and unlock the return
    And I select a cross validation failure return of "BT" v4 with reference date 30/06/2017 and unlock the return
    And I select a cross validation failure return of "BE" v3 with reference date 30/06/2017 and unlock the return
    #JOINT VALIDATION AND CROSS VALIDATION FAILURES
    And I select a cross validation failure return of "IS" v3 with reference date 30/06/2017 and unlock the return
    And I select a cross validation failure return of "BN" v1 with reference date 30/06/2017 and unlock the return

  @AR-2390
  Scenario: User logs on with submitter override privilege and submits an unlocked+unvalidated return from the dashboard
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I unlock the return
    And I close the return
    When I open the Export to "Export To AD_V2" dialog from the Dashboard
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then My export type is "Export to XML-XSLT"
    And The "AD" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the dashboard page for return "AD" version "2" and reference date 30/06/2017
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-2391
  Scenario: User logs on with submitter override privilege and submits an unlocked+valid return from the dashboard
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I unlock the return
    And I validate the return
    And I close the return
    When I open the Export to "Export To AD_V2" dialog from the Dashboard
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then My export type is "Export to XML-XSLT"
    And The "AD" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the dashboard page for return "AD" version "2" and reference date 30/06/2017
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-2392
  Scenario: User logs on with submitter override privilege and submits a unlocked+invalid return from the dashboard
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "IS" v3 with reference date 30/06/2017
    And I unlock the return
    And I validate the return
    And I close the return
    When I open the Export to "Export To IS_V3" dialog from the Dashboard
    And I select the "LRM100" entity and "IS_V3" module with reference date 30/06/2017
    Then My export type is "Export to XML-XSLT"
    And The "IS" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the dashboard page for return "IS" version "3" and reference date 30/06/2017
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-2393
  Scenario: User logs on with submitter override privilege and submits a locked+unvalidated return from the dashboard
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I lock the return
    And I close the return
    When I open the Export to "Export To AD_V2" dialog from the Dashboard
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then My export type is "Export to XML-XSLT"
    And The "AD" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the dashboard page for return "AD" version "2" and reference date 30/06/2017
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-2178
  Scenario: User logs on with submitter override privilege and submits a locked+valid return from the dashboard
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I lock the return
    And I validate the return
    And I close the return
    When I open the Export to "Export To AD_V2" dialog from the Dashboard
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    And My export type is "Export to XML-XSLT"
    And The "AD" return can be submitted
    When I open the comment log from the dashboard page for return "AD" version "2" and reference date 30/06/2017
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-2394
  Scenario: User logs on with submitter override privilege and submits a locked+invalid return from the dashboard
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "IS" v3 with reference date 30/06/2017
    And I lock the return
    And I validate the return
    And I close the return
    When I open the Export to "Export To IS_V3" dialog from the Dashboard
    And I select the "LRM100" entity and "IS_V3" module with reference date 30/06/2017
    Then My export type is "Export to XML-XSLT"
    And The "IS" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the dashboard page for return "IS" version "3" and reference date 30/06/2017
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-2234
  Scenario: User logs on with submitter override privilege and submits an unlocked+unvalidated return from the return page
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I unlock the return
    When I open the Export to "Export To AD_V2" dialog from the return
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then My export type is "Export to XML-XSLT"
    And The "AD" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the return instance page
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-6????
  Scenario: User logs on with submitter override privilege and submits an unlocked+valid return from the return page
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I unlock the return
    And I validate the return
    When I open the Export to "Export To AD_V2" dialog from the return
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then My export type is "Export to XML-XSLT"
    And The "AD" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the return instance page
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-7????
  Scenario: User logs on with submitter override privilege and submits a unlocked+invalid return from the return page
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "IS" v3 with reference date 30/06/2017
    And I unlock the return
    And I validate the return
    When I open the Export to "Export To IS_V3" dialog from the return
    And I select the "LRM100" entity and "IS_V3" module with reference date 30/06/2017
    Then My export type is "Export to XML-XSLT"
    And The "IS" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the return instance page
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-8????
  Scenario: User logs on with submitter override privilege and submits a locked+unvalidated return from the return page
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I lock the return
    When I open the Export to "Export To AD_V2" dialog from the return
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then My export type is "Export to XML-XSLT"
    And The "AD" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the return instance page
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-2186
  Scenario: User logs on with submitter override privilege and submits a locked+valid return from the return page
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I lock the return
    And I validate the return
    When I open the Export to "Export To AD_V2" dialog from the return
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then My export type is "Export to XML-XSLT"
    And The "AD" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the return instance page
    Then the top force submitted comment should be "[Status before submission: Not Cross Validated] forcing through due to deadline"

  @AR-9????
  Scenario: User logs on with submitter override privilege and submits a locked+invalid return from the return page
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "IS" v3 with reference date 30/06/2017
    And I lock the return
    And I validate the return
    When I open the Export to "Export To IS_V3" dialog from the return
    And I select the "LRM100" entity and "IS_V3" module with reference date 30/06/2017
    Then My export type is "Export to XML-XSLT"
    And The "IS" return can be force submitted with a comment of "forcing through due to deadline"
    When I open the comment log from the return instance page
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"

  @AR-2204
  Scenario: User logs on with Return Submitter privilege and attempts to submit a unlocked return from the dashboard
    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I unlock the return
    And I close the return
    When I open the Export to "Export To AD_V2" dialog from the Dashboard
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then The "AD" return can not be submitted

  @AR-2209
  Scenario: Submitter privilege and attempts to submit a locked return from the dashboard, no worflow approval
    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I lock the return
    And I validate the return
    And I close the return
    When I open the Export to "Export To AD_V2" dialog from the Dashboard
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then The "AD" return can be submitted

  @AR-2211
  Scenario: Submitter privilege and attempts to submit a unlocked return from the return page
    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I unlock the return
    When I open the Export to "Export To AD_V2" dialog from the return
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then The "AD" return can not be submitted

  @AR-2212
  Scenario: User logs on with Return Submitter privilege and attempts to submit a locked return from the return page
    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I lock the return
    And I validate the return
    When I open the Export to "Export To AD_V2" dialog from the return
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then The "AD" return can be submitted

  @AR-2213
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege, and attempts to submit a unlocked return from the dashboard
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I unlock the return
    And I close the return
    When I open the Export to "Export To AD_V2" dialog from the Dashboard
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then The "AD" return can not be submitted

  @AR-2214
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege, attempts to submit a locked return from the dashboard
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I lock the return
    And I validate the return
    And I close the return
    When I open the Export to "Export To AD_V2" dialog from the Dashboard
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then The "AD" return can not be submitted

  @AR-2217
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege, and attempts to submit a unlocked return from the return page
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I unlock the return
    When I open the Export to "Export To AD_V2" dialog from the return
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then The "AD" return can not be submitted

  @AR-2219
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege, and attempts to submit a locked return from the return page
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "MFSD"
    And I select the entity "LRM100"
    And I select the return "AD" v2 with reference date 30/06/2017
    And I lock the return
    And I validate the return
    And I close the return
    When I open the Export to "Export To AD_V2" dialog from the Dashboard
    And I select the "LRM100" entity and "AD_V2" module with reference date 30/06/2017
    Then The "AD" return can not be submitted