
//Get list of all available genres

package com.Rest.Spotify_BrowseAPI;

import java.util.ArrayList;

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

public class RequestGETAllAvailableGenres {

	RequestSpecBuilder reqspecbuild=null;
	RequestSpecification reqspec=null;
	ResponseSpecBuilder respspecbuild=null;
	ResponseSpecification respspec=null;
	
	ArrayList<String> al=new ArrayList<String>();
	
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
	@Parameters({"BasePathForGettingAllGenres"})
	public void getAllArtists(String BasePathForGettingAllGenres)
	{
		
	Response resp=	RestAssured.given(reqspec).
					when().
					get(BasePathForGettingAllGenres).
					then().
					spec(respspec).
					extract().response();
		for(int i=0;i<126;i++)
		{
			String st=resp.path("genres["+i+"]");
			System.out.println(st);
		}
	}
}