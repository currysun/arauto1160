@AnalysisModule @Download
Feature: Grid Export
  As a User,
  I want to export the Grid to a file
  so that I can do further analysis on it.

  Background:
    Given a user is logged in with username "admin" and password "password"
    And I click Analysis Module button from the header

  @AR-2707
  Scenario: Variance Analysis Grid export
    Given Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 31/03/2017
    When I create a Variance Analysis for previous reference date 28/02/2017
    And I order the cells in ascending order
    And I export the grid as a "CSV" file
    And I export the grid as a "XLSX" file
    Then the exported variance files should match the expected file "variance-analysis.csv"

  @AR-2941
  Scenario: Trends Analysis Grid export
    Given Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "BT" and reference date 31/07/2017
    When I create a Trend Analysis for the last 2 periods
    And I export the grid as a "CSV" file
    And I export the grid as a "XLSX" file
    Then the exported files should be named "MFSD 4Regs1000 BT 2017-07-31 trends"

  @AR-2658
  Scenario: Variance Drilldown export
    Given Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 31/03/2017
    When I create a Variance Analysis for previous reference date 28/02/2017
    And I drilldown into the derived cell "PMG1 (Subtotal)"
    And I export the grid as a "CSV" file
    And I export the grid as a "XLSX" file
    Then the exported files should be named "MFSD 4Regs1000 PM 2017-03-31 PMG1 variance"