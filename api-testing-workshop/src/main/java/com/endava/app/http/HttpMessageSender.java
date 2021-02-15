package com.endava.app.http;

import com.endava.app.entities.Auth;
import com.endava.app.entities.Booking;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.net.URL;

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


	public Response auth(Auth credentials, String endpoint){
		String requestURL = url + endpoint;
				return
						given().
						contentType(ContentType.JSON).
						body(credentials).
						log().all().
						when().
						post(requestURL).
						andReturn();
	}


	public Response putRequestToEndpoint(Booking booking, String token, String endpoint){
		String requestURL = url + endpoint;

			return
				given().
						body(booking).
						contentType(ContentType.JSON).
						cookie("token",token).log().all().
						when().
						put(requestURL).
						andReturn();
	}
}
