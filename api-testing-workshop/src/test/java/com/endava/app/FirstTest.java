package com.endava.app;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import io.restassured.http.ContentType;
import org.junit.Test;


public class FirstTest
{
	@Test
	public void firstApiTest(){

		given().
				contentType(ContentType.JSON).
		when().
				get("https://official-joke-api.appspot.com/random_joke").
		then().
				assertThat().
				statusCode(200);
	}

	@Test
	public void secondApiTest(){

		given().
				contentType(ContentType.JSON).
				when().
				get("https://official-joke-api.appspot.com/random_joke").
				then().log().all().
				assertThat().
				statusCode(200);
	}

	@Test
	public void StarWarsAPITest(){

		given().
				contentType(ContentType.JSON).
				when().
				get("https://swapi.dev/api/people/1").
				then().log().body().
				assertThat().
				statusCode(200);
	}
}
