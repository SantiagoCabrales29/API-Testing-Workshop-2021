package com.endava.app;

import com.endava.app.http.HttpMessageSender;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
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
		requestSender = new HttpMessageSender(props.getProperty("url"));
		api = new RestfulBookerApi(props.getProperty("url"));
	}

	@Test
	public void restfulBookerFirstTest(){
		Response response = requestSender.getRequestToEndpoint("/booking");

		Assert.assertEquals(200, response.getStatusCode());

		int id = response.then().extract().path("bookingid[0]");
		Assert.assertTrue("The id of the item is wrong",id>0);
	}

	/*
	 You were right we can separate more things.
	 We can use different abstractions.
	 An abstraction is a class that allow us to simulate the components or the behaviors of the SUT.

	 In this example we can create a class that will simulate all the functionalities a user can do with the
	 Restful Booker API.
	 Let's do that!
	 Go to the RestfulBookerApi class
	 */

	//Note that for using this we have to create a new RestfulBookerApi object and instantiate it (we do it in the @BeforeClass
	// but you can do it in the class)
	@Test
	public void getBookingIds(){
		List<Integer> listBookingIds = api.getBookingIds();
		boolean areItemsPositive = true;
		Assert.assertTrue("The list of ids is empty",listBookingIds.size()>0);
		for(Integer bookingId: listBookingIds){
			if(bookingId <=0){
				areItemsPositive=false;
				break;
			}
		}
		Assert.assertTrue("An id is negative",areItemsPositive);
	}

	//The RestfulBookerAPI Class allow us to do an abstraction of all the requests we can send through the api.



	//Exercise: Test HealthCheck Endpoint using the restful booker api class.



	@Test
	public void checkPing(){
		Response response = api.doPing();
		Assert.assertEquals(201, response.getStatusCode());
	}

	//Continues in feature/6-Serialization-and-Deserialization
}
