
  Feature: Booking test using Gherkin

	Scenario: Create Booking
	  Given A recently created Booking
	  When The user sends a request to the booking list endpoint
	  Then The booking is added to the list
