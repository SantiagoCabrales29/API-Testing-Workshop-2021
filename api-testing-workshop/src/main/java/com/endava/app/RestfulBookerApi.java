package com.endava.app;

import com.endava.app.http.HttpMessageSender;
import io.restassured.response.Response;

import java.util.List;

public class RestfulBookerApi {
	private final String url;
	private final HttpMessageSender messageSender;


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
}
