@AgileREPORTER @AR-154 @AR1.16.0
Feature: [BUS - RR] Security Integration - User accounts maintenance: needs to include expiration date, suspension role based security.
  Each user belongs to a security role which determines access to: Menu items, Database table , Reporting entities, Returns,Reports

  Scenario: Create new user
    Given a user is logged in with username "admin" and password "password"
    When I am on the Users page
    Then I create the necessary Users test21, test22, test23, test24, test25, test26, test27, test28, test29, test30, qa2, qa3, qa4, qa5, qa6

  Scenario: User login and logout
    Given a user is logged in with username "test21" and password "password"
    Then I am logged out
    Given a user is logged in with username "test22" and password "password"
    Then I am logged out
    Given a user is logged in with username "test3" and password "password"
    Then I am logged out
    Given a user is logged in with username "test4" and password "password"
    Then I am logged out
    Given a user is logged in with username "test5" and password "password"
    Then I am logged out
    Given a user is logged in with username "test7" and password "password"
    Then I am logged out
    Given a user is logged in with username "test10" and password "password"
    Then I am logged out


  Scenario: User account is failed login with mismatching error
    When a user with username "admin" and password "123" failed login
    Then I should see login error message "The username or password you provided does not match our records."


  Scenario: Confirm user is locked
    Given a user is logged in with username "admin" and password "password"
    When I am on the Users page
    Then I see the "Password Reset Date" status of user "see" is "03/08/2018"
    Then I see the "test" status of user "test1" is "Yes"

  Scenario: Unlock user
    Given a user is logged in with username "admin" and password "password"
    When I am on the Users page
    And I unlock user "test1"
    Then a user is logged in with username "test1" and password "password"
  |1927|113|0001|CNY|66700|82319.361111|150950|13-JAN-19|HK|IBD16CN|209900|82319.361111|15-MAY-14|KMCUST052

  1927	113	0001	CNY	66700	82319.361111	150950	13-JAN-19	HK	IBD16CN	209900	82319.361111	15-MAY-14	KMCUST052

#    User accounts info in testing environment:
#      | user       | create date    | password reset data | account expiration date |                   |
#      | test1      | 08-AUG-18      |                     |                         | 0.00%             |
#      | qa1        | 07-AUG-18      | 07-AUG-18           |                         | 0.00%             |
#      | admin      | 04-JUL-18      | 07-AUG-18           |                         | 13-AUG-18         |
#      | juan chen  | 04-JUL-18      |                     |                         | 0.00%             |
#      | see        | 03-AUG-18      | 03-AUG-18           | 02-AUG-18               | 0.00%             |
#      | qa2        | 09-AUG-18      |                     |                         | 0.00%             |
#      | view       | 03-AUG-18      |                     |                         | 0.00%             |
#      | test5      | 09-AUG-18      |                     |                         | 0.00%             |
#      | test       | 02-AUG-18      | 06-AUG-18           |                         | Expried           |

  Scenario: AR154-01-01-User account is locked if failed login attempts exceeds max
    Given a user is logged in with username "admin" and password "password"
    When I am on the Security Settings page
    Then I change the maximum allowed failed login attempts to "2"
    Then I am logged out
    When a user with username "test1" and password "123" failed login
    Then I should see login error message "The username or password you provided does not match our records."
    When a user with username "test1" and password "p123" failed login
    Then I should see login error message "The username or password you provided does not match our records."
    When a user with username "test1" and password "111" failed login
    Then I should see login error message "The user account has been locked."
    When a user is logged in with username "admin" and password "password"
    And I am on the Users page
    Then I see the "Locked" status of user "test1" is "Yes"
    And I unlock user "test1"
    Then a user is logged in with username "test1" and password "password"
    Then I am logged out
    When a user is logged in with username "admin" and password "password"
    Then I change the maximum allowed failed login attempts to "9999"



  Scenario: AR154-01-02-User account is suspended if no login within the max inactivity period
    Given a user is logged in with username "admin" and password "password"
    When I am on the Security Settings page
    Then I active account suspension and set maximum allowed inactivity period to "2" days
    And I am logged out
    # need to check the last login date of user qa1, if the LocalDate.now() - last login date < 2, qa1 is not be suspended
    When a user with username "qa1" and password "password" failed login
    Then I should see login error message "The user account has been suspended"
    When a user is logged in with username "admin" and password "password"
    And I am on the Users page
    Then I see the "Suspended" status of user "qa1" is "Yes"
    And I remove suspension status of user "qa1"
    Then a user is logged in with username "qa1" and password "password"
    And I am logged out
    When a user is logged in with username "admin" and password "password"
    When I am on the Security Settings page
    Then I disable account suspension




#      | user       | create date    | password reset date | account expiration date | Expect result     |
#      | admin      | 04-JUL-18      | 07-AUG-18           |                         | Warning           |
#      | juan chen  | 04-JUL-18      |                     |                         | Nothing happens   |
#      | view       | 03-AUG-18      |                     |                         | Expried           |
#      | test       | 02-AUG-18      | 06-AUG-18           |                         | Expried           |
  Scenario: AR154-01-03-User password is expired if no changes within the given password life
    Given a user is logged in with username "admin" and password "password"
    When I am on the Security Settings page
    # make sure the password rest date for user test should be "06-AUG-18" --- TO DO will be set value by sql executed
    # pwdLife = LocalDate.now() - LocalDate.of(2018,8,7);
    # exWarningPeriod = passwordLife - 1;
    Then I set the password expiration settings based on 07/08/2018
    And I am logged out
    When a user with username "view" and password "password" failed login
    Then I should see login error message "The users password has expired."
    When a user with username "test" and password "password" failed login
    Then I should see login error message "The users password has expired."
    When a user is logged in with username "juan chen" and password "Jenny123456"
    And I am logged out
    When a user is logged in with username "admin" and password "password"
    Then I see login warning message "Your password will expire, please update as soon as possible!"
    Then I reset password for user "test"







