package com.endava.app;

import com.endava.app.entities.Auth;
import com.endava.app.entities.Booking;
import com.endava.app.helpers.DataGenerator;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class BookingCRUDTests {
	private static Properties props;
	private static RestfulBookerApi api;

	@BeforeClass
	public static void createTestEnvironment() {
		props = new Properties();
		try {
			props.load(new FileInputStream("application.properties"));
		} catch (IOException var2) {
			System.out.println("Hay un error leyendo el archivo de properties");
		}
		System.out.println(props.getProperty("url"));
		api = new RestfulBookerApi(props.getProperty("url"));
	}

	@Test
	public void getBookingIds() {
		List<Integer> listBookingIds = api.getBookingIds();
		boolean areItemsPositive = true;
		Assert.assertTrue("The list of ids is empty", listBookingIds.size() > 0);
		for (Integer bookingId : listBookingIds) {
			if (bookingId <= 0) {
				areItemsPositive = false;
				break;
			}
		}
		Assert.assertTrue("An id is negative", areItemsPositive);
	}

	@Test
	public void testPing() {
		Response response = api.doPing();
		Assert.assertEquals(201, response.getStatusCode());
	}

	@Test
	public void getBookingById() {
		List<Integer> listBookingIds = api.getBookingIds();
		int random = (int) (Math.random() * (listBookingIds.size())) + 1;
		System.out.println("This is the random number: " + random);
		Booking booking = api.getBookingById(random);

		Assert.assertNotNull("Booking is null", booking);
	}

	@Test
	public void createBooking() {
		Booking booking = DataGenerator.createRandomBooking();
		Response response = api.createBooking(booking);

		Assert.assertEquals("The status code is different than 200Ok", response.statusCode(), 200);
		String name = response.then().extract().path("booking.firstname");
		Assert.assertEquals("Names do not match", name, booking.getFirstname());
	}

	@Test
	public void createBookingImprovedTest() {
		List<Integer> bookingsInList = api.getBookingIds();
		Booking booking = DataGenerator.createRandomBooking();
		Response response = api.createBooking(booking);

		Assert.assertEquals("The status code is different than 200Ok", response.statusCode(), 200);
		List<Integer> newBookingList = api.getBookingIds();
		Assert.assertTrue(newBookingList.size() > bookingsInList.size());

		int id = response.then().extract().path("bookingid");
		Booking createdBooking = api.getBookingById(id);
		Assert.assertEquals("Names do not match", booking.getFirstname(), createdBooking.getFirstname());
	}

	//Exercise Try to create a test to update a booking.
	//Note that we have to do the auth method first and obtain a token!




	//We can manage the auth in many ways but let's use Serialization again
	@Test
	public void updateBooking() {
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		Auth auth = new Auth(username,password);
		String token = api.auth(auth);

		List<Integer> bookingList = api.getBookingIds();
		int random = (int) (Math.random() * (bookingList.size()) + 1);
		System.out.println("This is the random number: " + random);

		Booking booking = api.getBookingById(random);
		booking.setFirstname("Pedro");
		booking.setLastname("Pascal");

		Response response = api.updateBooking(booking, token, bookingList.get(random));
		response.then().log().all();
		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertEquals("Names do not match", response.then().extract().path("firstname"), booking.getFirstname());
		Assert.assertEquals("LastNames do not match", response.then().extract().path("lastname"), booking.getLastname());
	}


	//Exercice try to test delete booking



	@Test
	public void deleteBooking() {
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		Auth auth = new Auth(username,password);
		String token = api.auth(auth);

		List<Integer> bookingList = api.getBookingIds();

		int random = (int) (Math.random() * (bookingList.size())+1);
		System.out.println("This is the random number: " + random);

		Response response = api.deleteBooking(token, bookingList.get(random));

		Assert.assertEquals("",response.statusCode(), 201);

		List<Integer> updatedList = api.getBookingIds();

		Assert.assertFalse("", updatedList.contains(bookingList.get(random)));
		Assert.assertTrue(updatedList.size() < bookingList.size());

	}

	/*
	We could do more and more testing, this was just a quick example of the test you could do with Rest Assured
	If you are interested look for Data Driven Testing, how to send concurrent requests to the APIs, Differences between Data Binding Libraries
	How to upload these tests to a CI pipeline.
	 */

	//Continues in feature/8-Gherkin-and-Clossure
}
