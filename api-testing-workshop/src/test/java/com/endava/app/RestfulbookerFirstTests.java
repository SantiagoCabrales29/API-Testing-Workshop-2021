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
	//There's a "problem" with the previous test.
	//We are duplicating code!

	@Test
	public void restfulBookerFirstTest(){
		given().
				contentType(ContentType.JSON).
				when().
				get("https://restful-booker.herokuapp.com/booking").
				then().log().body().
				assertThat().
				statusCode(200);
	}

	//What is we want to add a new test to test? We will have to duplicate the content type the get and the status code
	//We can use the request and response Specification objects from Rest Assured to set a "template" of the request and
	//response that we will need.

	private static RequestSpecification requestSpecification;
	private static ResponseSpecification responseSpecification;

	@BeforeClass
	public static void createRequestSpecification(){
		requestSpecification = new RequestSpecBuilder().
				setContentType(ContentType.JSON).
				setBaseUri("https://restful-booker.herokuapp.com/booking").
				build();
	}

	@BeforeClass
	public static void createResponseSpecification(){
		responseSpecification = new ResponseSpecBuilder().
				expectContentType(ContentType.JSON).
				expectStatusCode(200).
				build();
	}

	//Let's used them
	@Test
	public void restfulBookerGetBookingById(){
		given().
				spec(requestSpecification).
				when().
				get("/1").
				then().
				spec(responseSpecification);
	}

	//Refactor the first Test so that it uses the new request and response specifications

	@Test
	public void restfulBookerGetBookingByIdRefactored(){
		given().
				spec(requestSpecification).
				when().
				get("").
				then().
				spec(responseSpecification);
	}

	//Continue in the feature/3-Environment-Variables
}
