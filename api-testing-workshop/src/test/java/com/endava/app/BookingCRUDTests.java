package com.endava.app;

import com.endava.app.entities.Booking;
import com.endava.app.http.HttpMessageSender;
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


	/*
	Something very useful when testing the APIs is what we know as Serialization and Deserialization process.
	With those we can managed the data binding between our test suite and the Json/XML objects used by the system.

	Serialization: converts Java Objects to JSON output.
	Deserialization: converts JSON input into Java objects.

	In Java we know those objects as POJOs (Plain Old Java Object)
	But no only in java we can do this with other languages. Those objects are considered DTO (Data Transfer Object)

	Can you think of some usages for this?

	Let's see this in more detail in the Booking class.
	*/

	//To do the serialization we have to use an external library that help us with Data binding. We can use
	//Jackson, Gson, among others. In this example we will use Gson. Let's add the maven dependency

	@Test
	public void getBookingById() {
		List<Integer> listBookingIds = api.getBookingIds();
		int random = (int) (Math.random() * (listBookingIds.size())) + 1;
		System.out.println("This is the random number: " + random);
		Booking booking = api.getBookingById(random);

		Assert.assertNotNull("Booking is null",booking);
	}

	//Is this an Example of serialization or deserialization?
}
