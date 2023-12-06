package com.rahulshettyacademy.Courses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("CoursesCatalogue")
@PactFolder("pacts")
public class PactProviderTest {
	
	@LocalServerPort //this annotation can listen to the port number and number will be binded to int port.
	public int port;   //getting port in our test, so context to know where to run
	
	@TestTemplate
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	public void pactVerificationTest(PactVerificationContext context) //this context comes from junit5, its junit Pact test runner
	{
		context.verifyInteraction();//opening the json file and pull off the interactions.
		//test template
	}
	
	@BeforeEach
	public void setup(PactVerificationContext context)
	{
		context.setTarget(new HttpTestTarget("localhost", port));//where is my target server where i need to push this annotations
		
	}
	
	@State(value= "courses exist",action=StateChangeAction.SETUP) //this will be executed before test gets executed. Adding value, will executed for that specific test only
	public void coursesExist()//
	{
		
	}
	
	@State(value= "courses exist",action=StateChangeAction.TEARDOWN) //this will be executed before test gets executed. Adding value, will executed for that specific test only
	public void coursesExistTearDown()
	{
		
	}
	// setup method - Appium is present? if not insert a record in test
	//teardown - appium delete?
	/// /getCourseName/Appium - > {name - appium, id="", price=""}
	//  /getCourseName/Appium -> {msg: course do not exist}
	

}
