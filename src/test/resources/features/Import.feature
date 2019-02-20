@AgileReporter @Import
Feature: Import Return
  As a User,
  I want to import adjustments to a return from either excel or csv format
  so that I don't have to enter the data manually

  Background:
    Given a user is logged in with username "admin" and password "password"

  @AR-5299
  Scenario: Import adjustments as scaled or No Scale from xlsx file with in a return
    When I select the regulator "ECR"
    And I select the entity "ECR2999"
    And I select the return "MKIR" v5 with reference date 10/09/2017
    And I choose to import "scaledReturn.xlsx"
    And I select the scaled option
    And I import the file
    Then value in cell "MKIRR012C010" equals "44,725,588 "
    And I choose to import "unscaledReturn.xlsx"
    And I select the no scale option
    And I import the file
    Then value in cell "MKIRR012C010" equals "44,725,588 "

  @AR-5300
  Scenario: Import adjustments as scaled or No Scale from csv file with in a return
    When I select the regulator "ECR"
    And I select the entity "ECR2999"
    And I select the return "MKIR" v5 with reference date 10/09/2017
    And I choose to import "scaledReturn.csv"
    And I select the scaled option
    And I import the file
    Then value in cell "MKIRR012C010" equals "44,725,588 "
    And I choose to import "unscaledReturn.csv"
    And I select the no scale option
    And I import the file
    Then value in cell "MKIRR012C010" equals "44,725,588 "