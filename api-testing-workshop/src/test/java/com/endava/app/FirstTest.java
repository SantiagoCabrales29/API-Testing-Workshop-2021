package com.endava.app;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import io.restassured.http.ContentType;
import org.junit.Test;


public class FirstTest
{
    /*
     We need to use an interaction tool library. Example Rest Assured, Selenium, etc.
     An interaction library allows us to interact with the system that we want to test, in this case an API.
     We will use Rest Assured but you can use any library that allows you to send HTTP Messages. Let's add it.
     */

    //Now with Rest Assured we can do our first api call!
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
	/*
	In this case we are using the official joke api.
	The test passes but is it making the things that we want?
	How can we see anything rest assured is doing?
	 */

	//Note that the name of the methods must be meaningful, don't follow my example!
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

	//Exercise do a check to the swapi endpoint https://swapi.dev/api/people/1

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
