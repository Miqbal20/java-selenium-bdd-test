Feature: Login Page Kasir Aja

  @Regression @Positive
  Scenario: Verify Success Login
    Given User on Login Pages kasir Aja
    When User fill valid username and password
    And click login button
    Then User redirect to dashboard page


  @Regression @Negative
  Scenario: Verify Failed Login
    Given User on Login Pages kasir Aja
    When User fill invalid username and password
    And click login button
    Then User get error message


  @Regression @Positive
  Scenario: Verify Success Logout
    Given User on Login Pages kasir Aja
    When User fill valid username and password
    And click login button
    And user success login
    And click logout button
    Then User redirect to login page