package com.Rest.Spotify_PlaylistsAPI;

import java.io.File;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RequestPOSTCreatePlaylist {

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
						  expectStatusCode(201).
						  expectResponseTime(Matchers.is(Matchers.lessThan((long)(6000)))).
						  log(LogDetail.BODY);
		respspec=respspecbuild.build();
	}
	
	@Test(priority=1)
	@Parameters({"BasePathForCreatingPlaylist","DatafilePath"})
	public void createPlaylist(String BasePathForCreatingPlaylist,String DatafilePath)
	{
		File f=new File(DatafilePath);
		
		RestAssured.given().
					spec(reqspec).
					body(f).
					when().
					post(BasePathForCreatingPlaylist).
					then().
					spec(respspec).and().
					assertThat().
					body("display_name",Matchers.is(Matchers.equalTo("Prashant")));
	}
}