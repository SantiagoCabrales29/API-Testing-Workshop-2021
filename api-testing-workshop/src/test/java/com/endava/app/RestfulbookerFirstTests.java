package com.endava.app;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class RestfulbookerFirstTests {

	private static RequestSpecification requestSpecification;
	private static ResponseSpecification responseSpecification;

	@BeforeClass
	public static void createRequestSpecification() {
		requestSpecification = new RequestSpecBuilder().
				setContentType(ContentType.JSON).
				setBaseUri("https://restful-booker.herokuapp.com/booking").
				build();
	}

	@BeforeClass
	public static void createResponseSpecification() {
		responseSpecification = new ResponseSpecBuilder().
				expectContentType(ContentType.JSON).
				expectStatusCode(200).
				build();
	}

	@Test
	public void restfulBookerFirstTest() {
		given().
				contentType(ContentType.JSON).
				when().
				get("https://restful-booker.herokuapp.com/booking").
				then().
				assertThat().
				statusCode(200);
	}

	@Test
	public void restfulBookerGetBookingById() {
		given().
				spec(requestSpecification).
				when().
				get("/1").
				then().
				spec(responseSpecification);
	}

	@Test
	public void restfulBookerGetBookingByIdRefactored() {
		given().
				spec(requestSpecification).
				when().
				get("").
				then().
				spec(responseSpecification);
	}

}
