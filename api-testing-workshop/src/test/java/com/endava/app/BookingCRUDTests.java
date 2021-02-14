package com.endava.app;

import com.endava.app.http.HttpMessageSender;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.Matchers.*;

public class BookingCRUDTests {
	/*
	Do you know about SOLID principles?
	It is important to separate the different logics of our application. Follow the single responsibility principle!
	It doesn't have much sense that our test code has the ability to send HTTP requests.
	The test code should have the methods that allow us to test the application.
	That's why IntelliJ gave us the test and the main folders!
	Test should group all the code needed to test
	Main should group all the code needed to interact with the SUT.

	Let's create a class that help us to handle the requests.
	 */
	private static Properties props;
	private static HttpMessageSender requestSender;

	@BeforeClass
	public static void createTestEnvironment() {
		props = new Properties();
		try {
			props.load(new FileInputStream("application.properties"));
		} catch (IOException var2) {
			System.out.println("Hay un error leyendo el archivo de properties");
		}
		System.out.println(props.getProperty("url"));
		requestSender = new HttpMessageSender(props.getProperty("url"));
	}

	@Test
	public void restfulBookerFirstTest(){
		Response response = requestSender.getRequestToEndpoint("/booking");

		Assert.assertEquals(200, response.getStatusCode());

		int id = response.then().extract().path("bookingid[0]");
		Assert.assertTrue("The id of the item is wrong",id>0);
	}

	/*
	Note that Rest Assured allow us to do some assertions of the fields.
	This is useful, but if we are using JUnit, it is better to use that library to do the assertions and to use Rest
	Assured only to send and receive HTTP requests!
	*/
	@Test
	public void checkFirstNameTest(){
		Response response =requestSender.getRequestToEndpoint("/booking/1");

		response.then().
				assertThat().body("firstname", not(equalTo("Santiago"))).
				body("totalprice",greaterThan(0));
	}

	/*
	Do you see any advantages of using this approach?
	 */


	//Exercise: Modify the Request Sender to also send post requests.

}
