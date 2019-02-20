@AgileREPORTER @AR-SubmitOverride
Feature: Submit Overrider privilege tests

  Background:
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I open or create the return "CICDP" v1 with reference date 21/04/2017
    And I unlock the return

  @AR-2230
  Scenario: User logs on with submitter override privilege and cancels the force submit request

    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    Then My export type is "Export to"
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then The "CICDP" return can be force submitted but I choose to cancel from the force submit dialog

  @AR-2249
  Scenario: User logs on with submitter override privilege and submits an unlocked return then checks the log

    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"
    And I open the comment log from the dashboard page for return "CICDP" version "1" and reference date 21/04/2017
    Then the top force submitted comment should be "[Status before submission: Not Validated] forcing through due to deadline"
    And it should be actioned by "SubmitterOverrider"
    And it should have a timestamp
    And it should not have an attachment

  @AR-2250
  Scenario: User logs on with submitter override privilege and submits a locked return then checks the log

    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I validate the return
    And I lock the return
    And I close the return
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"
    And I open the comment log from the dashboard page for return "CICDP" version "1" and reference date 21/04/2017
    Then the top force submitted comment should be "[Status before submission: Not Approved] forcing through due to deadline"
    And it should be actioned by "SubmitterOverrider"
    And it should have a timestamp
    And it should not have an attachment

  @AR-2162
  Scenario: User logs on with submitter override privilege and submits an unlocked return from the dashboard

    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"

  @AR-2163
  Scenario: User logs on with submitter override privilege and submits a locked return from the dashboard

    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I lock the return
    And I close the return
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"

  @AR-2165
  Scenario: User logs on with submitter override privilege and submits an unlocked return from the return page

    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"

  @AR-2166
  Scenario: User logs on with submitter override privilege and submits a locked return from the return page

    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I lock the return
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"

  @AR-2167
  Scenario: User logs on with Return Submitter privilege and attempts to submit a unlocked return from the dashboard

    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted

  @AR-2169
  Scenario: User logs on with Return Submitter privilege and attempts to submit a locked return from the dashboard

    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I lock the return
    And I close the return
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted

  @AR-2170
  Scenario: User logs on with Return Submitter privilege and attempts to submit a unlocked return from the return page

    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted

  @AR-2166
  Scenario: User logs on with Return Submitter privilege and attempts to submit a locked return from the return page

    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I lock the return
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted

  @AR-2172
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege, and attempts to submit a unlocked return from the dashboard

    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted

  @AR-2173
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege, attempts to submit a locked return from the dashboard

    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I lock the return
    And I close the return
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted

  @AR-2174
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege, and attempts to submit a unlocked return from the return page

    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted

  @AR-2175
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege, and attempts to submit a locked return from the return page

    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I lock the return
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted

  @AR-1864
  @Regression
  Scenario: User logs on with submitter override privilege and submits return on all available approval states from the dashboard - Maker is Overrider

#     "Not Approved" state
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"

#    "Ready to Approve" state
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    And I close the return
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"

#    "Pending Approval" state
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return
    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"

#    "Under Review" state -
    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return for review to "workgroup1", adding the comment "Try harder!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"

#    "Approved" state
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "better!"
    And I close the return
    Given a user is logged in with username "wkapprover3_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "gggggrrreeaaat!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be submitted

  @AR-1865
  @Regression
  Scenario: User logs on with submitter override privilege and submits return all available approval states from the dashboard - Maker is NOT Overrider

#  "Ready to Approve" state - Maker is NOT Overrider
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"

    And a user is logged in with username "SubmitterOverrider" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"

#  "Pending Approval" state
    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"

    Given a user is logged in with username "wkapprover1_3" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"

#    "Under Review" state -
    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return for review to "workgroup1", adding the comment "Try harder!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"

#    "Approved" state
    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "better!"
    And I close the return
    Given a user is logged in with username "wkapprover3_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "gggggrrreeaaat!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be submitted

  @AR-1871
  @Regression
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege, All states - can't submit return from the dashboard - maker

#  "Not Approved" state
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Ready to Approve" state
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    And I close the return
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Pending Approval" state
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return
    Given a user is logged in with username "NoSubmitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Under Review" state
    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return back to maker, adding the comment "Try harder!"
    And I close the return
    Given a user is logged in with username "NoSubmitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Approved" state
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    And I close the return

    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "better!"
    And I close the return
    Given a user is logged in with username "wkapprover3_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "gggggrrreeaaat!"
    And I close the return
    Given a user is logged in with username "NoSubmitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be submitted
   #uses default privileges which contain export rights, needs changing in application AR-2360 work allocated to support custom privileges only in framework

  @AR-1927
  @Regression
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege - can't submit return from the dashboard - NOT the maker

#  "Ready to Approve" state
    Given a user is logged in with username "maker" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    And I close the return
    Given a user is logged in with username "NoSubmitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Pending Approval" state
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return
    Given a user is logged in with username "NoSubmitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Under Review" state
    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return back to maker, adding the comment "Try harder!"
    And I close the return
    Given a user is logged in with username "NoSubmitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Approved" state
    Given a user is logged in with username "maker" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    And I close the return
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return
    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "better!"
    And I close the return
    Given a user is logged in with username "wkapprover3_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "gggggrrreeaaat!"
    And I close the return
    Given a user is logged in with username "NoSubmitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be submitted
   #uses default privileges which contain export rights, needs changing in application AR-2360 work allocated to support custom privileges only in framework

  @AR-1930
  @Regression
  Scenario: User logs on Return Submitter privilege - can't submit return unless approved from the dashboard - maker

#  "Not Approved" state
    Given a user is logged in with username "Submitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Ready to Approve" state
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    And I close the return
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Pending Approval" state
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return
    Given a user is logged in with username "Submitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Under Review" state
    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I send the return back to maker, adding the comment "Try harder!"
    And I close the return
    Given a user is logged in with username "Submitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    And My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Approved" state
    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    And I close the return

    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "better!"
    And I close the return
    Given a user is logged in with username "wkapprover3_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "gggggrrreeaaat!"
    And I close the return
    Given a user is logged in with username "Submitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be submitted

  @AR-1931
  @Regression
  Scenario: User logs on Return Submitter privilege ONLY - can't submit return from the dashboard unless approved - NOT maker

#  "Ready to Approve" state
    Given a user is logged in with username "maker" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    And I close the return
    Given a user is logged in with username "Submitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Pending Approval" state
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return
    Given a user is logged in with username "Submitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Under Review" state
    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return back to maker, adding the comment "Try harder!"
    And I close the return
    Given a user is logged in with username "Submitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Approved" state

    Given a user is logged in with username "maker" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    And I close the return

    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "better!"
    And I close the return
    Given a user is logged in with username "wkapprover3_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "gggggrrreeaaat!"
    And I close the return
    Given a user is logged in with username "Submitter" and password "password"
    When I open the Export to "Export To Arbitrary Excel" dialog from the Dashboard
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be submitted

# Access submit menu from return page

  @AR-1932
  @Regression
  Scenario: User logs on with submitter override privilege and submits return from the return page on all available approval states - Maker is Overrider

#     "Not Approved" state
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"
    And I close the return

#    "Ready to Approve" state
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"
    And I close the return

#    "Pending Approval" state
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"
    And I close the return

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"
    And I close the return

#    "Under Review" state -
    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return for review to "workgroup1", adding the comment "Try harder!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"
    And I close the return

#    "Approved" state
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "better!"
    And I close the return
    Given a user is logged in with username "wkapprover3_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "gggggrrreeaaat!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be submitted

  @AR-1933
  @Regression
  Scenario: User logs on with submitter override privilege and submits from the return page on all available approval states - Maker is NOT Overrider

#  "Ready to Approve" state - Maker is NOT Overrider
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"

    And a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"
    And I close the return

#    "Pending Approval" state
    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"
    And I close the return

    Given a user is logged in with username "wkapprover1_3" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return

#    "Under Review" state -
    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return for review to "workgroup1", adding the comment "Try harder!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    Then My export type is "Export to"
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then The "CICDP" return can be force submitted with a comment of "forcing through due to deadline"
    And I close the return

#    "Approved" state
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "like it!"
    And I close the return

    Given a user is logged in with username "wkapprover1_3" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "super!"
    And I close the return

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "better!"
    And I close the return
    Given a user is logged in with username "wkapprover3_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "gggggrrreeaaat!"
    And I close the return
    Given a user is logged in with username "SubmitterOverrider" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be submitted
  # uses default privileges which contain export rights, needs changing in application AR-2360 work allocated to support custom privileges only in framework

  @AR-1934
  @Regression
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege - can't submit return from the return page - maker

#  "Not Approved" state
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window
    And I close the return

#  "Ready to Approve" state
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window
    And I close the return

#  "Pending Approval" state
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window
    And I close the return

#  "Under Review" state
    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return back to maker, adding the comment "Try harder!"
    And I close the return
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window
    And I close the return

#  "Approved" state

    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"

    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "better!"
    And I close the return

    Given a user is logged in with username "wkapprover3_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "gggggrrreeaaat!"
    And I close the return

    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be submitted
   #uses default privileges which contain export rights, needs changing in application AR-2360 work allocated to support custom privileges only in framework

  @AR-1935
  @Regression
  Scenario: User logs on WITHOUT submitter override privilege OR Submitter privilege - can't submit return from the return page - NOT the maker

#  "Ready to Approve" state
    Given a user is logged in with username "maker" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    And I close the return

    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window
    And I close the return

#  "Pending Approval" state
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window
    And I close the return

#  "Under Review" state
    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return for review to "workgroup1", adding the comment "Try harder!"
    And I close the return
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window
    And I close the return

#  "Approved" state

    Given a user is logged in with username "maker" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    And I close the return

    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "better!"
    And I close the return

    Given a user is logged in with username "wkapprover3_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "gggggrrreeaaat!"
    And I close the return

    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be submitted
   #uses default privileges which contain export rights, needs changing in application AR-2360 work allocated to support custom privileges only in framework

  @AR-1947
  @Regression
  Scenario: User logs on Return Submitter privilege - can't submit return unless approved from the return page - maker

#  "Not Approved" state
    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window

#  "Ready to Approve" state
    And I make the return ready for approval with comment "Good to go"
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window
    And I close the return

#  "Pending Approval" state
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return

    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window
    And I close the return

#  "Under Review" state
    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return back to maker, adding the comment "Try harder!"
    And I close the return
    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window
    And I close the return

#  "Approved" state
    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"

    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "better!"
    And I close the return

    Given a user is logged in with username "wkapprover3_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "gggggrrreeaaat!"
    And I close the return

    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be submitted

  @AR-1956
  @Regression
  Scenario: User logs on Return Submitter privilege ONLY - can't submit return unless approved from the return page - NOT maker

#  "Ready to Approve" state
    Given a user is logged in with username "maker" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    And I close the return

    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window
    And I close the return

#  "Pending Approval" state
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    And I close the return

    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window
    And I close the return

#  "Under Review" state
    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return back to maker, adding the comment "Try harder!"

    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can not be submitted
    And I close the Export window
    And I close the return

#  "Approved" state
    Given a user is logged in with username "maker" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    And I make the return ready for approval with comment "Good to go"
    And I close the return

    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "did better!"
    And I close the return

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "better!"
    And I close the return

    Given a user is logged in with username "wkapprover3_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "gggggrrreeaaat!"
    And I close the return

    Given a user is logged in with username "Submitter" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I open the Export to "Export To Arbitrary Excel" dialog from the return
    And I select the "LRM111" entity and "CICDP" module with reference date 21/04/2017
    Then My export type is "Export to"
    Then The "CICDP" return can be submitted