package com.endava.app.helpers;

import com.endava.app.entities.Booking;
import com.endava.app.entities.BookingDates;

import java.util.Date;
import java.util.HashMap;

public class DataGenerator {
	public static String createRandomString(){
		return String.valueOf(System.currentTimeMillis()).
				replaceAll("1","a").
				replaceAll("2","c").
				replaceAll("3","f").
				replaceAll("4","y").
				replaceAll("5","q").
				replaceAll("6","s").
				replaceAll("7","o").
				replaceAll("8","i").
				replaceAll("9","l");
	}

	public static Booking createRandomBooking(){
		String firstname = createRandomString();
		String lastname = createRandomString();
		int totalprice = 200;
		boolean depositpaid = false;
		BookingDates dates = new BookingDates(new Date(), new Date());
		String additionalneeds = createRandomString();

		return new Booking(firstname,lastname,totalprice,depositpaid,dates,additionalneeds);
	}

	//What does static mean?
	//Note that we can also use the Java Faker Library to generate Fake data!
}
