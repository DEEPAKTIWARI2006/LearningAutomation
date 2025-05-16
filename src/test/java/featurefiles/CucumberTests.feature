#Author: Deepak 
 #Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: Demo Test Suite
  I want to use this template for my feature file

 @smoke
 Scenario Outline: Login validation with valid credentials
  Given User is on the Main Landing screen
  When User enters valid credentials "<username>" "<password>"
  Then  User should login successfully "<LoginSuccessMessage>"
	
	Examples:
  | username | password | LoginSuccessMessage             |
  | deepaksporty2005@gmail.com          | Deepak@30         | DEEPAK  |
    
 @regression
 Scenario Outline: Login validation with invalid credentials
  	Given User is on the Main Landing screen
  	When User enters valid credentials "<username>" "<password>"
  	Then  User should login successfully "<LoginSuccessMessage>"

	Examples:
  | username | password | LoginSuccessMessage             |
  | deepakautomation@gmail.com          | Djhsedgfe         | Deepak  |