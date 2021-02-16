package com.endava.app;

import com.endava.app.helpers.TestEnv;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TestsUsingEnvVariables {

	@Test
	public void usingEnvVariableTest(){
		given().
				contentType(ContentType.JSON).
				when()
				.get(TestEnv.getURL()+"/booking/1").
				then().
				assertThat().
				statusCode(200);
	}

	@Test
	public void usingPropertiesFileTest(){
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("application.properties"));
		} catch (IOException var2) {
			System.out.println("Hay un error leyendo el archivo de properties");
		}

		given().
				contentType(ContentType.JSON).
				when()
				.get(props.getProperty("url")).
				then().
				assertThat().
				statusCode(200);
	}

	@Test
	public void IdListOnlyHasPositiveIds(){

		Properties props = new Properties();
		try {
			props.load(new FileInputStream("application.properties"));
		} catch (IOException var2) {
			System.out.println("Hay un error leyendo el archivo de properties");
		}
		boolean areItemsPositive=true;
		Response response = given().contentType(ContentType.JSON).when().get(props.getProperty("url")+"/booking").andReturn();
		List<Integer> listIds=response.then().extract().path("bookingid");

		Assert.assertTrue("The list of ids is empty",listIds.size()>0);

		for(Integer bookingId: listIds){
			if(bookingId <=0){
				areItemsPositive=false;
				break;
			}
		}
		Assert.assertTrue("An id is negative",areItemsPositive);
	}

	@Test
	public void TestFirstNameValueOfFirstBooking(){

		Properties props = new Properties();
		try {
			props.load(new FileInputStream("application.properties"));
		} catch (IOException var2) {
			System.out.println("Hay un error leyendo el archivo de properties");
		}
		Response response = given().contentType(ContentType.JSON).when().get(props.getProperty("url")+"/booking/1").andReturn();
		String name =response.then().extract().path("firstname");
		Assert.assertNotSame("We have an incorrect name", "Santiago", name);

	}
}
