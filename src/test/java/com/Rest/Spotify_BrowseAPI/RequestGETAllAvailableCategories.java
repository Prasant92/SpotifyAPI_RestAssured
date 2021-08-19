
//Get list of browse categories

package com.Rest.Spotify_BrowseAPI;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RequestGETAllAvailableCategories {

	RequestSpecBuilder reqspecbuild=null;
	RequestSpecification reqspec=null;
	ResponseSpecBuilder respspecbuild=null;
	ResponseSpecification respspec=null;
	
	
	@BeforeTest
	@Parameters({"BaseURISpotifyForGetProfile","Authorization"})
	public void preCondition(String BaseURISpotifyForGetProfile,String Authorization)
	{
		reqspecbuild=new RequestSpecBuilder().
						 setBaseUri(BaseURISpotifyForGetProfile).
						 addHeader("Authorization", Authorization).
						 log(LogDetail.URI).
						 and().
						 log(LogDetail.BODY);
		reqspec=reqspecbuild.build();
		
		respspecbuild=new ResponseSpecBuilder().
						  expectContentType(ContentType.JSON).
						  expectStatusCode(200).
						  expectResponseTime(Matchers.is(Matchers.lessThan((long)(6000)))).
						  log(LogDetail.BODY);
		respspec=respspecbuild.build();
	}
	
	@Test(priority = 1)
	@Parameters({"BasePathForGettingAllCategories"})
	public void getAllAvailableCategories(String BasePathForGettingAllCategories)
	{
		
	Response resp=	RestAssured.given(reqspec).
					when().
					get(BasePathForGettingAllCategories).
					then().
					spec(respspec).
					extract().response();
		for(int i=0;i<20;i++)
		{
			String st=resp.path("categories.items["+i+"].id");
			System.out.println(st);
		}
	}
	
	@Test(priority = 2)
	@Parameters({"BasePathForGettingAllCategories","CountryCode"})
	public void getAllCategoriesBasedOnCountryCode(String BasePathForGettingAllCategories, String CountryCode)
	{
		
	Response resp=	RestAssured.given(reqspec).
					queryParam("country", CountryCode).
					when().
					get(BasePathForGettingAllCategories).
					then().
					spec(respspec).
					extract().response();
		for(int i=0;i<20;i++)
		{
			String st=resp.path("categories.items["+i+"].id");
			System.out.println(st);
		}
	}
}