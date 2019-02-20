@AgileREPORTER @AR-AttachmentsConfiguration
Feature: Attachments configuration
  As an admin I want to be able to configure settings for uploading attachments with comments

  Background: I am on the configuration screen
    Given a user is logged in with username "admin" and password "password"
    And I am on the Attachment Configuration page

  @AR-1823
  @AttachmentsConfiguration
  Scenario: I want to check file size limited to maximum of 1023 KB or MB
    When I set the file size limit to 10000 MB
    Then I see that the file size limit is 1023 MB
    When I set the file size limit to 10000 KB
    Then I see that the file size limit is 1023 KB

  @AR-1712
  @AttachmentsConfiguration
  Scenario: I want to be able to add to the allowed file extensions
    Given the default allowed file extensions are set
    When I add a new file extension "newFileExtension"
    And I add a new file extension ".anotherNewFileExtension"
    Then I see that the allowed file extensions contain ".newfileextension"
    And I see that the allowed file extensions contain ".anothernewfileextension"

  @AR-1713
  @AttachmentsConfiguration
  Scenario: I want to be able to delete from the allowed file extensions
    Given the default allowed file extensions are set
    When I delete the file extension ".doc"
    Then I see that the allowed file extensions do not contain ".doc"

  @AR-1714
  @AttachmentsConfiguration
  Scenario: I want to restrict configuration to admins only
    Given I am on the Users page
    When a user is logged in with username "maker" and password "password"
    Then I do not see Comments menu option
    And I see that I do not have privileges to configure comments

  @AR-1727
  @AttachmentsConfiguration
  Scenario: Restrict attachments file types not in the whitelist
    Given the default allowed file extensions are set
    And I delete the file extension ".txt"
    When I create any return
    And I open the comment log from the dashboard page
    Then an error should stop me from uploading an attachment

  @AR-1728
  @AttachmentsConfiguration
  Scenario: Allow attachments file types in the whitelist
    Given I see that the allowed file extensions contain ".txt"
    When I create any return
    And I open the comment log from the dashboard page
    And I submit a comment "Needs approving urgently" with an attachment
    Then it should have an attachment

  @AR-1741
  @AttachmentsConfiguration
  Scenario: Disable file upload if there are no file types in the whitelist
    Given there are no file extensions allowed for uploading
    When I create any return
    And I open the comment log from the dashboard page
    Then I should not be able to upload an attachment

  @AR-1789
  @AttachmentsConfiguration
  Scenario: Allow attachment of files with size equal to the limit
    Given I set the file size limit to 1 KB
    When I create any return
    And I open the comment log from the dashboard page
    And I submit a comment "Needs approving urgently" with an attachment equal to the file size limit
    Then it should have an attachment

  @AR-1790
  @AttachmentsConfiguration
  Scenario: Allow attachment of files with size under the limit
    Given I set the file size limit to 1 KB
    When I create any return
    And I open the comment log from the dashboard page
    And I submit a comment "Needs approving urgently" with an attachment under the file size limit
    Then it should have an attachment

  @AR-1791
  @AttachmentsConfiguration
  Scenario: Disallow attachment of files with size over the limit
    Given I set the file size limit to 1 KB
    When I create any return
    And I open the comment log from the dashboard page
    Then an error should stop me from uploading a larger attachment
    Given a user is logged in with username "admin" and password "password"
    And I am on the Attachment Configuration page
    Then I reset the file size limit to 10000 MB