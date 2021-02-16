package com.endava.app.stepdefs;

import com.endava.app.RestfulBookerApi;
import com.endava.app.entities.Booking;
import com.endava.app.helpers.DataGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;

public class BookingSteps {

	private RestfulBookerApi api = new RestfulBookerApi("https://restful-booker.herokuapp.com");
	private int id;
	private List<Integer> list;
	@Given("A recently created Booking")
    public void aRecentlyCreatedBooking() {
		Booking booking = DataGenerator.createRandomBooking();
		Response response = api.createBooking(booking);
		id = response.then().extract().path("bookingid");
		Assert.assertEquals("The status code is different than 200Ok", response.statusCode(), 200);
    }

	@When("The user sends a request to the booking list endpoint")
	public void theUserSendsARequestToTheBookingListEndpoint() {
		list = api.getBookingIds();
	}

	@Then("The booking is added to the list")
	public void theBookingIsAddedToTheList() {
		Assert.assertTrue(searchItemToUpdate(list,id));
	}

	public boolean searchItemToUpdate(List<Integer> listIds, int idToSearch){
		for (Integer id:listIds) {
			if (id==idToSearch){
				return true;
			}
		}
		return false;
	}
}
