package com.endava.app.http;

import com.endava.app.entities.Booking;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HttpMessageSender {
	private String url;

	public HttpMessageSender(String url) {
		this.url = url;
	}

	public Response getRequestToEndpoint(String endpoint) {
		String requestURL = url + endpoint;
		return
				given().
						contentType(ContentType.JSON).
						when().
						get(requestURL).
						andReturn();

	}

	public Response postRequestToEndpoint(Booking booking, String endpoint) {
		String requestURL = url + endpoint;
		return
				given().
						contentType(ContentType.JSON).
						body(booking).log().all().
						when().
						post(requestURL).
						andReturn();

	}
}
