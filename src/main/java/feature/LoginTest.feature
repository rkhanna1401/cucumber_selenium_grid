
Feature: Login Verification
Description: This will test all login related scenario's

Background:
User on the Login Page

Scenario Outline: Invalid Login Test
Given Navigate To Loginpage
When User enter correct username "<email>"
And User enter incorrect password
Then Message displayed Wrong UserName & Password
And User is not logged-in.

@smoke
Examples:
| email |
| bhavs62.tandon@gmail.com |
| rishi.khanna7868@gmail.com |
 
@regression
Examples:
| email |
| rishi.khanna786@gmail.com | 


@loginusers
  Scenario: Verify Login with multiple users
    When I Logged in with users
      | UserName                  | Password |
      | rishi.khanna@gmail.com    | test1234|
      | r.khanna@gmail.com        | test12345 |
      | rishi.khanna786@gmail.com | test123 |


@sanity
Scenario: Password Reset Test
Given Navigate to password page
When User selects Forgot password
And User navigated to password reset page
Then User succesfully resets the password
 