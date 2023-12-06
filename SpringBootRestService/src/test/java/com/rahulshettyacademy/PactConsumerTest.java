package com.rahulshettyacademy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahulshettyacademy.controller.LibraryController;
import com.rahulshettyacademy.controller.ProductsPrices;
import com.rahulshettyacademy.controller.SpecificProduct;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

@SpringBootTest // to load all external repositories
@ExtendWith(PactConsumerTestExt.class) //extend the capabilities of spring boot test with PactConsumerTest. Adding PAct consumer test library
@PactTestFor(providerName = "CoursesCatalogue") // all the cons test are targeting provider. Provider need to be clearly provided
//these three annotation above must be provided before we write any unit or contract tests using Pact. 
public class PactConsumerTest {
	
	@Autowired
	private LibraryController libraryController;
	
//	@Pact(consumer="BooksCatalogue") //setting up Pact server. telling who is the Consumer
//	public RequestResponsePact PactallCoursesDetailsConfig(PactDslWithProvider builder) //builder object will help us to create all the mock config. PactDslWithProvider is a class
//	{
//		return builder.given("courses exist") // first we need to define the state. Now we are mocking allCourseDetails CALL
//		.uponReceiving("getting all course details")
//		.path("/allCourseDetails")
//		.willRespondWith()
//		.status(200)
//		.body(PactDslJsonArray.arrayMinLike(3)//PactDslJsonArray class, supporting all Json arrays
//				.stringType("course_name")
//				.stringType("id")
//				.integerType("price", 10)
//				.stringType("category").closeObject()).toPact();
//	//we are just mocking this. because we want to test this method assuming this service call is happening as expected and everything working perfectly
//	}
	
	@Pact(consumer="BooksCatalogue") //setting up Pact server. telling who is the Consumer
	public RequestResponsePact PactallCoursesDetailsPriceCheck(PactDslWithProvider builder) //builder object will help us to create all the mock config. PactDslWithProvider is a class
	{
		return builder.given("courses exist") // first we need to define the state. Now we are mocking allCourseDetails CALL
		.uponReceiving("getting all course details")
		.path("/allCourseDetails")
		.willRespondWith()
		.status(200)
		.body(PactDslJsonArray.arrayMinLike(3)//PactDslJsonArray class, supporting all Json arrays
				
				.integerType("price", 10)
				.closeObject()).toPact();
	//we are just mocking this. because we want to test this method assuming this service call is happening as expected and everything working perfectly
	}
	//  PactFlow
	
	// -> PactFlow Server  (contract file to server)

	@Test
	@PactTestFor(pactMethod="PactallCoursesDetailsPriceCheck", port="9876")//server configuration.
	
	public void testAllProductSum(MockServer mockServer) throws JsonMappingException, JsonProcessingException//Pact server Expose MockServer Class and has the details about port etc..it will start on localhost, we are overriding port above 9999
	{
		String expectedJson = "{\"booksPrice\":250,\"coursesPrice\":30}";
		libraryController.setBaseUrl(mockServer.getUrl());//the url of the Pact Server.
		ProductsPrices productsPrices = libraryController.getProductPrices();
		
		ObjectMapper obj = new ObjectMapper();   //converting JAva object to JSON
		String jsonActual = obj.writeValueAsString(productsPrices); //we are adding object here and store into Json string
		
		Assertions.assertEquals(expectedJson, jsonActual);
	}
	
	
	
	
//		@Pact(consumer="BooksCatalogue")
//		public RequestResponsePact PactallCoursesDetailsPriceCheck(PactDslWithProvider builder)
//		{
//			return builder.given("courses exist")
//			.uponReceiving("getting all courses details")
//			.path("/allCourseDetails")
//			.willRespondWith()
//			.status(200)
//			.body(PactDslJsonArray.arrayMinLike(3)
//					
//					.integerType("price", 10)
//					.closeObject()).toPact();
//							
//		}
//		
//		@Pact(consumer = "BooksCatalogue")
//		public RequestResponsePact getCourseByName(PactDslWithProvider builder)
//		
//		{
//			return builder.given("Course Appium exist")
//			.uponReceiving("Get the Appium course details")
//			.path("/getCourseByName/Appium")
//			.willRespondWith()
//			.status(200)
//			.body(new PactDslJsonBody()
//					.integerType("price",44)
//					.stringType("category","mobile")).toPact();
//			
//		}
//	
//	
//	
//
//	@Test
//	@PactTestFor(pactMethod="PactallCoursesDetailsPriceCheck",port = "9999")
//	
//	public void testAllProductsSum(MockServer mockServer) throws JsonMappingException, JsonProcessingException
//	
//	{
//		
//		String expectedJson ="{\"booksPrice\":250,\"coursesPrice\":30}";
//		libraryController.setBaseUrl(mockServer.getUrl());
//		
//		ProductsPrices productsPrices = libraryController.getProductPrices();
//		ObjectMapper obj = new ObjectMapper();
//		String jsonActual = obj.writeValueAsString(productsPrices);
//		
//		Assertions.assertEquals(expectedJson, jsonActual);
//	
//		
//	}
//	
//	@Test
//	@PactTestFor(pactMethod="getCourseByName",port = "9999")
//	
//	public void testByProductName(MockServer mockServer) throws JsonMappingException, JsonProcessingException
//	
//	{
//		
//		libraryController.setBaseUrl(mockServer.getUrl());
//		
//		String expectedJson = "{\"product\":{\"book_name\":\"Appium\",\"id\":\"fdsefr343\",\"isbn\":\"fdsefr3\",\"aisle\":43,\"author\":\"Rahul Shetty\"},\"price\":44,\"category\":\"mobile\"}";
//		
//		SpecificProduct specificProduct =libraryController.getProductFullDetails("Appium");
//		
//		ObjectMapper obj = new ObjectMapper();
//		String jsonActual = obj.writeValueAsString(specificProduct);
//		
//		Assertions.assertEquals(expectedJson, jsonActual);
//		
//		
//	}
//	@Pact(consumer = "BooksCatalogue")
//	public RequestResponsePact getCourseByNameNotExist(PactDslWithProvider builder)
//	
//	{
//		return builder.given("Course Appium does not exist","name","Appium")
//		.uponReceiving("Appium course Does not exist")
//		.path("/getCourseByName/Appium")
//		.willRespondWith()
//		.status(404)
//		.toPact();
//		
//	}
//	
//	@Test
//	@PactTestFor(pactMethod="getCourseByNameNotExist",port = "9999")
//	
//	public void testByProductNameNotExist(MockServer mockServer) throws JsonMappingException, JsonProcessingException
//	
//	{
//		
//		libraryController.setBaseUrl(mockServer.getUrl());
//		
//		String expectedJson = "{\"product\":{\"book_name\":\"Appium\",\"id\":\"fdsefr343\",\"isbn\":\"fdsefr3\",\"aisle\":43,\"author\":\"Rahul Shetty\"},\"msg\":\"AppiumCategory and price details are not available at this time\"}";
//		
//		SpecificProduct specificProduct =libraryController.getProductFullDetails("Appium");
//		
//		ObjectMapper obj = new ObjectMapper();
//		String jsonActual = obj.writeValueAsString(specificProduct);
//		
//		Assertions.assertEquals(expectedJson, jsonActual);
//		
//		
//	}

	
	
	
	
	
	
	
	
	
	

	
	
}
