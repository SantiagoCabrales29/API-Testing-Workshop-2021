package com.endava.app.http;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.net.URL;

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


	//This goes after showing the first test


	public Response postRequestToEndpoint(String msg, String endpoint) {
		String requestURL = url + endpoint;
		Response response =
				given().
						contentType(ContentType.JSON).
						body(msg).log().all().
						when().
						post(requestURL).
						andReturn();

		return response;
	}
}
