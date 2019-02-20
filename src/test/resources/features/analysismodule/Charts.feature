@AnalysisModule
Feature: Trend Analysis Charts

  As a User, I want to be able to view data from a range of periods, in graphical form,
  showing movements over time.

  Note:
  Further further testing to collect data are found in ChartsTests
  even though steps to grab data are automated manual review of this data is needed

  Background:
    Given a user is logged in with username "admin" and password "password"
    And I click Analysis Module button from the header
    And Analysis Module's selected return details are regulator "ECR", entity "ECR2999", return "CAR" and reference date 31/12/2015
    And I create a Trend Analysis for the last 2 periods

  @AR-3105
  Scenario: Charts - full screen view
    Given I choose a random selection of cells to graph
    When I open the chart in full screen
    And I close the full screen chart
    Then the grid should be displayed

  @AR-2484
  @Pipeline @ScreenshotAfterScenario
  Scenario: Trend Analysis is charted
    When I choose a random selection of cells to graph
    And I select the "Value" chart view
    Then the chart should contain the selected cells