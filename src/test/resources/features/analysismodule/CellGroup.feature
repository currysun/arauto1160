@AnalysisModule @AnalysisModule-CellGroups
Feature: Analysis Module Cell Group - Create - Default - Edit - Delete - Usage

  As a User I want to be able to create, edit, default, delete cell group/s within a return in Variance or Trends Analysis.
  And as a tester I want to  verify the easy & quick switches between groups of cells when viewing a return in Variance or Trends Analysis.

  Background:
    Given a user is logged in with username "admin" and password "password"
    And I open the cell group return in analysis module

  @AR-2967
  @CellGroupCleanUp
  Scenario: Create a new cell group
    Given I create a cell group "AR 2967"
    Then I should see the cell group in the list on cell group manager window

  @AR-2969
  @CellGroupCleanUp
  Scenario: Cell Groups should not be created with duplicate Cell Group Names
    Given I create a cell group "AR 2969"
    Then If I try to create a cell group with the same name I should see an error stating so

  @AR-2970
  Scenario: Cell Group Name should not accept more than maximum length of characters
    Given I start to create a cell group "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    Then I should not be able to save the cell group

  @AR-2972
  Scenario: Cell Group Name  should not accept special characters
    Then I am unable to create a cell group with a special character in

  @AR-2977
  Scenario: Cell Group should not be saved if the name is not provided
    Given I start to create a cell group ""
    Then I should not be able to save the cell group

  @AR-3001
  Scenario: Cell Group should not be saved if the name is white space
    Given I start to create a cell group "                "
    Then I should not be able to save the cell group

  @AR-3002
  Scenario: Cell group should not be saved if the cell group does not contain any cells
    Given I try to create a cell group "no cells added" with no cells
    Then I should not be able to save the cell group

  @AR-3079
  Scenario: Cell Groups - Delete - Cannot delete active cell group
    When I select the "Never the default" cellgroup and create
    Then I should not be able to delete "Never the default"

  @AR-3083
  @CellGroupCleanUp
  Scenario: Cell Groups - Delete - Deleting default cell group should make "All cells" default
    Given I create a cell group "AR 3083"
    And I make the cell group my default
    When I delete the cell group
    Then the default cell group should be "All Cells"

  @AR-3078
  @CellGroupCleanUp
  Scenario: Cell Groups - Delete - Delete and select 'No' to the delete warning
    Given I create a cell group "AR 3078"
    When I start to delete a cell group but cancel deletion
    Then I should see the cell group in the list on cell group manager window

  @AR-3084
  Scenario: Cell Groups - Delete - Cannot delete system cell group
    Then there should be no option to delete a system cell group

  @AR-2983
  @CellGroupCleanUp
  Scenario: Cell Group Edit - Add/Remove few cells and save the edited cell group
    Given I create a cell group "AR 2983"
    When I edit the cell group to remove the cells
      | CAORDAR360 |
    Then the cell group no longer includes the cells
      | CAORDAR360 |

  @AR-2984
  @CellGroupCleanUp
  Scenario: Cell Group Edit - Remove all the cells and save, should not save the cell group
    Given I create a cell group "AR 2984"
    When I remove all the cells from the cell group
    Then I should not be able to save the cell group

  @AR-3007
  @CellGroupCleanUp
  Scenario: Cell Groups - Edit - Edit the Cell Group Name to special characters, should not save cell group
    Given I start to create a cell group "AR-3007"
    Then since the cell group includes a special character I am unable to save

  @AR-3008
  @CellGroupCleanUp
  Scenario: Cell Groups - Edit - duplicate Cell Group Name , should not save cell group
    Given I create a cell group "Original AR 3008"
    Then If I try to rename the cell group to an existing name I should see an existing name error

  @AR-3009
  @CellGroupCleanUp
  Scenario: Cell Groups - Edit - White space the Cell Group Name
    Given I create a cell group "White space"
    Then If I try to rename the cell group to a blank name I shouldn't be able to save

  @AR-3006
  @CellGroupCleanUp
  Scenario: Cell Groups - Edit - Blank cell group name
    Given I create a cell group "AR 3006"
    Then If I try to rename the cell group to a blank name I shouldn't be able to save

  @AR-3010
  @CellGroupCleanUp
  Scenario: Cell Groups - Edit - Name Cell Group with more than maximum length, should not save cell group
    Given I create a cell group "ABCDEFGHIJKLMNOPQRSTUVWXY"
    Then If I try to rename the cell group to a full alphabet I shouldn't be able to save

  @AR-3059
  @Pipeline
  Scenario: Variance Analysis Cell Group displays predefined cells
    Given I should see a prepopulated Report Cell Group of "All Cells"
    When I select the "Cell Group 1" cellgroup and create
    Then the grid should contain the following cells and variance difference:
      |                   | CAMIR230 (Subtotal)        | CAORDAR360 | TP01R200C060 (Subtotal) |
      | description       | Investments in the capital |            | 1. T                    |
      | 30/09/2015        | 3,774.00                   | 0.00       | 14,268.00               |
      | difference        | 0.00                       | 0.00       | 0.00                    |
      | percentDifference | 0.00%                      | 0.00%      | 0.00%                   |
      | 31/12/2015        | 3,774.00                   | 0.00       | 14,268.00               |
    When I select the "Cell Group 2" cellgroup and create
    Then the grid should contain the following cells and variance difference:
      |                   | CAMIR230 (Subtotal)        | CAMIR350                   | CAORDAR240 |
      | description       | Investments in the capital | Investments in the capital |            |
      | 30/09/2015        | 3,774.00                   | 1,520.00                   | 0.00       |
      | difference        | 0.00                       | 0.00                       | 0.00       |
      | percentDifference | 0.00%                      | 0.00%                      | 0.00%      |
      | 31/12/2015        | 3,774.00                   | 1,520.00                   | 0.00       |

  @AR-2986
  Scenario: Trend Analysis Cell Group displays predefined cells
    Given I select the "Cell Group 3" cellgroup and create
    When I create an Ad-hoc Trend Analysis for the previous reference dates 30/09/2015, 31/07/2015
    Then the grid should contain the following cells and trend differences:
      |                   | CAOFR030 (Subtotal) |
      | description       | Own Funds           |
      | 31/07/2015        | (3,828.00)          |
      | difference        | 50.00               |
      | percentDifference | (1.31%)             |
      | 30/09/2015        | (3,778.00)          |
      | difference        | 0.00                |
      | percentDifference | 0.00%               |
      | 31/12/2015        | (3,778.00)          |
    And I select the "Cell Group 4" cellgroup and create
    Then the grid should contain the following cells and trend differences:
      |                   | CAMIR230 (Subtotal)        |
      | description       | Investments in the capital |
      | 31/07/2015        | 3,774.00                   |
      | difference        | 0.00                       |
      | percentDifference | 0.00%                      |
      | 30/09/2015        | 3,774.00                   |
      | difference        | 0.00                       |
      | percentDifference | 0.00%                      |
      | 31/12/2015        | 3,774.00                   |

  @AR-3069
  Scenario: Refresh retains the existing cell group
    Then I should not see a prepopulated Report Cell Group of "Never the default"
    When I create a Date Range Trend Analysis for the previous reference date 31/08/2015
    And I select the "Never the default" cellgroup and create
    And I refresh the current page
    Then I should see a prepopulated Report Cell Group of "Never the default"

  @AR-3229
  @CellGroupCleanUp
  Scenario: Cell Groups Version Control - All custom cell groups should have versions associated to them
    Given I create a cell group "AR 3229"
    Then I should see the cell group with return version

  @AR-3217
  Scenario: Cell Groups System - Make the Subtotal cell group active and should see only Subtotal cell references
    And Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 30/04/2017
    When I select the "Subtotal Cells" cellgroup and create
    Then the grid rows should have the following:
      | PMG1 (Subtotal)  |
      | PMG3 (Subtotal)  |
      | PMPA1 (Subtotal)  |
      | PMPA3 (Subtotal)  |
      | PMPL1 (Subtotal) |
      | PMPL3 (Subtotal) |
      | PMS1 (Subtotal) |
      | PMS3 (Subtotal) |