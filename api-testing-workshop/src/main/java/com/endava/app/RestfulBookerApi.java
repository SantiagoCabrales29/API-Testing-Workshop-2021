package com.endava.app;

import com.endava.app.entities.Auth;
import com.endava.app.entities.Booking;
import com.endava.app.http.HttpMessageSender;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.restassured.response.Response;

import java.util.List;

public class RestfulBookerApi {
	private final String url;
	private final HttpMessageSender messageSender;
	private JsonParser parser = new JsonParser();


	public RestfulBookerApi(String url) {
		this.url = url;
		this.messageSender = new HttpMessageSender(url);
	}

	public List<Integer> getBookingIds() {
		Response response = messageSender.getRequestToEndpoint("/booking");

		List<Integer> listIds = response.then().extract().path("bookingid");

		return listIds;
	}

	public Response doPing(){
		Response response = messageSender.getRequestToEndpoint("/ping");
		return  response;
	}

	public Booking getBookingById(int id) {
		Response response = messageSender.getRequestToEndpoint("/booking/"+id);
		JsonElement jsonResponse = parser.parse(response.body().asString());

		Booking booking = new Gson().fromJson(jsonResponse, Booking.class);
		System.out.println("This is the name of the booking: " + booking.getFirstname() + " " + booking.getLastname());

		return booking;
	}

	//Now we are going to use post method that we created some time ago. Note that we only have to modify the HttpMessageSender Class
	public Response createBooking(Booking booking) {
		return messageSender.postRequestToEndpoint(booking,"/booking");
	}

	public String auth(Auth credentials) {

		Response response = messageSender.auth(credentials,"/auth");
		String token = response.then().extract().path("token");

		System.out.println("El token es:" + token);

		return token;
	}


	public Response updateBooking(Booking booking, String token, int index) {
		return messageSender.putRequestToEndpoint(booking, token,"/booking/"+index);

	}

	public Response deleteBooking(String token, int index) {
		return messageSender.deleteRequestToEndpoint(token, "/booking/"+index);

	}
}
