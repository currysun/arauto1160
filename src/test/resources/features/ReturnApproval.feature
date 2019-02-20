@AgileREPORTER @AR-WorkFlowApprovalProcess
Feature: Returns Approval and Review process with workflow

  Background:
    Given a user is logged in with username "NoSubmitter" and password "password"
    And I select the regulator "RBI"
    And I recreate the return "CICDP" v1 with reference date 21/04/2017

  @AR-1837@AR-1838@AR-1839@AR-1840@AR-2444
  @Regression
  Scenario: Approval progress example

    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I make the return ready for approval with comment "Good to go, automated attachment added" and add attachment "documentAttachment_1Byte.txt"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_1"
    And the top comment in the comment log should have action "Ready for Approval"
    And the top comment in the comment log should be "Good to go, automated attachment added"
    And it should have an attachment
    And it should have a timestamp
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (0/4)"

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_2"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be "Looking good!"
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (1/4)"

    Given a user is logged in with username "wkapprover1_3" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking even better"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_3"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be "Looking even better"
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (2/4)"

    Given another user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return for review to "workgroup1", adding the comment "Sending back for review, automated attachment added" and add attachment "documentAttachment_1Byte.txt"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover2_1"
    And the top comment in the comment log should have action "Sent for Review"
    And the top comment in the comment log should be "Sending back for review, automated attachment added"
    And it should have an attachment
    And it should have a timestamp
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Under Review (0/4)"

    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I make the return ready for approval with comment "oops I did it again.." and add attachment "documentAttachment_1Byte.txt"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_1"
    And the top comment in the comment log should have action "Ready for Approval"
    And the top comment in the comment log should be "oops I did it again.."
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (0/4)"

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Reviewed and approved from Under Review status, automated attachment added" and add attachment "documentAttachment_1Byte.txt"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_2"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be "Reviewed and approved from Under Review status, automated attachment added"
    And it should have an attachment
    And it should have a timestamp
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (1/4)"

    Given a user is logged in with username "wkapprover1_3" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking better"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_3"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be "Looking better"
    And it should have a timestamp
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (2/4)"

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return back to maker, adding the comment "I don't like this!"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover2_1"
    And the top comment in the comment log should have action "Sent for Review"
    And the top comment in the comment log should be "I don't like this!"
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Under Review (0/4)"

    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I make the return ready for approval with comment "oops I did it again.." and add attachment "documentAttachment_1Byte.txt"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_1"
    And the top comment in the comment log should have action "Ready for Approval"
    And the top comment in the comment log should be "oops I did it again.."
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (0/4)"

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return back to maker, adding the comment "Try harder!"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_2"
    And the top comment in the comment log should have action "Sent for Review"
    And the top comment in the comment log should be "Try harder!"
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Under Review (0/4)"

    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I make the return ready for approval with comment "ok nailed it"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_1"
    And the top comment in the comment log should have action "Ready for Approval"
    And the top comment in the comment log should be "ok nailed it"
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (0/4)"

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Amazing!"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_2"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be "Amazing!"
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (1/4)"

    Given a user is logged in with username "wkapprover1_3" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "well done you figured out what a financial return is for"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_3"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be "well done you figured out what a financial return is for"
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (2/4)"

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "just in time"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover2_1"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be "just in time"
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (3/4)"

    Given a user is logged in with username "wkapprover3_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "just in time"
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover3_1"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be "just in time"
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Approved (4/4)"

  @AR-2427
  Scenario: Approval progress example with no comment and no attachment
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I make the return ready for approval with no comment
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_1"
    And the top comment in the comment log should have action "Ready for Approval"
    And the top comment in the comment log should be empty
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (0/4)"

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the no comment
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_2"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be empty
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (1/4)"

    Given a user is logged in with username "wkapprover1_3" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with an attachment
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_3"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be empty
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (2/4)"

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return for review to "workgroup1", adding no comment
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover2_1"
    And the top comment in the comment log should have action "Sent for Review"
    And the top comment in the comment log should be empty
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Under Review (0/4)"

  @AR-2428
  Scenario: Approval progress example with attachment
    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I make the return ready for approval with an attachment
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_1"
    And the top comment in the comment log should have action "Ready for Approval"
    And the top comment in the comment log should be empty
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (0/4)"

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with an attachment
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_2"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be empty
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (1/4)"

    Given a user is logged in with username "wkapprover1_3" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with an attachment
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_3"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be empty
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (2/4)"

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return for review to "workgroup1", adding an attachment
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover2_1"
    And the top comment in the comment log should have action "Sent for Review"
    And the top comment in the comment log should be empty
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Under Review (0/4)"

  @AR-2429
  Scenario: Approval progress example with comment and attachment

    Given a user is logged in with username "wkapprover1_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I make the return ready for approval with comment "Good to go" and attachment
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_1"
    And the top comment in the comment log should have action "Ready for Approval"
    And the top comment in the comment log should be "Good to go"
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (0/4)"

    Given a user is logged in with username "wkapprover1_2" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!" and attachment
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_2"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be "Looking good!"
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (1/4)"

    Given a user is logged in with username "wkapprover1_3" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I approve the return with the comment "Looking good!" and attachment
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover1_3"
    And the top comment in the comment log should have action "Approved"
    And the top comment in the comment log should be "Looking good!"
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Pending Approval (2/4)"

    Given a user is logged in with username "wkapprover2_1" and password "password"
    And I select the regulator "RBI"
    And I select the return "CICDP" v1 with reference date 21/04/2017
    When I send the return for review to "workgroup1", adding the comment "Try harder!" and attachment
    When I open the comment log from the return instance page
    Then the top comment in the comment log should be actioned by "wkapprover2_1"
    And the top comment in the comment log should have action "Sent for Review"
    And the top comment in the comment log should be "Try harder!"
    When I close the comment log
    And the "RBI" return "CICDP" v1 with reference date 21/04/2017 should have the dashboard status "Under Review (0/4)"