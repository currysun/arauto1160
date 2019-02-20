@AgileREPORTER @AR-UserAssignment
Feature: Assign a specific user,group,entity,return and privileges

  Background:
    Given a user is logged in with username "admin" and password "password"

  Scenario: Submit user testing creation and assignment
    Given I have a user of "mrtester" and password of "password" in group "testers" and assigned to entity "LRM111" with the following "RBI" product and return "CICDP" v1 assigned with privileges of Return Viewer, Return Submitter, Return Maker, Return Overrider
