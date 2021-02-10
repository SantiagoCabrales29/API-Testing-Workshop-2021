package com.endava.app;

import com.endava.app.helpers.TestEnv;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TestsUsingEnvVariables {

	//How to use an test environment class. This allows me to have sensible information in one place. If I need to modify it then I change it in the class.
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


	//How to use an application.properties file

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
}
