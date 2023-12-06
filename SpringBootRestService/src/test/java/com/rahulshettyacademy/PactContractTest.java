package com.rahulshettyacademy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rahulshettyacademy.controller.LibraryController;

import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "CoursesCatalogue")
public class PactContractTest {
	
	
//	@Autowired
//	private LibraryController libraryController; //Creating object for Library Controller instead of using new keyword
//	
//	//we are writing the Server below, so we are telling Pact which is our Server
//	@Pact(consumer="BooksCatalogue") //setting up Pact server. telling who is the Consumer
//	public RequestResponsePact PactallCoursesDetailsConfig(PactDslWithProvider builder) //builder object will help us to create all the mock config. PactDslWithProvider is a class
//	{
//		return builder.given("courses exist") // first we need to define the state. Now we are mocking allCourseDetails CALL
//		.uponReceiving("getting all course details")
//		.path("/allCoursesDetails")
//		.willRespondWith()
//		.status(200)
//		.body(PactDslJsonArray.arrayMinLike(2)//PactDslJsonArray class, supporting all json arrays
//				.stringType("course_name")
//				.stringType("id")
//				.integerType("price", 12)
//				.stringType("category").closeObject()).toPact();
//	//we are just mocking this. because we want to test this method assuming this service call is happening as expected and everything working perfectly
//	}
//	
//	@Test
//	@PactTestFor(pactMethod="PactallCoursesDetailsConfig")//small change
//	public void testAllProductsSum()
//	//we want to call getProductPrices() method from here.
//	
//	{
//		//LibraryController lib = new LibraryController();
//	//	libraryController.getProductPrices();
//	}

}
