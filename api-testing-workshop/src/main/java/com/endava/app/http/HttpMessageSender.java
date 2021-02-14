package com.endava.app.http;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HttpMessageSender {
	//This class will group all the methods needed to send HTTP Requests
	private String url;

	public HttpMessageSender(String url) {
		this.url = url;
	}

	public Response getRequestToEndpoint(String endpoint) {
		String requestURL = url + endpoint;
		Response response =
				given().
						contentType(ContentType.JSON).
						when().
						get(requestURL).
						andReturn();

		return response;
	}
}
