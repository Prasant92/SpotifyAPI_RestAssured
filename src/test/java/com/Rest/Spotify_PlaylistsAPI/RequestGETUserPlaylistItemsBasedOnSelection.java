package com.Rest.Spotify_PlaylistsAPI;

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

public class RequestGETUserPlaylistItemsBasedOnSelection {

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
	@Parameters({"FilterOutSongsFromPlaylistQuery","Limit","BasePathForAddingSongsToUserPlaylist","Playlist_id"})
	public void getPlaylistBasedOnSelection(String FilterOutSongsFromPlaylistQuery,String Limit,String BasePathForAddingSongsToUserPlaylist,String Playlist_id)
	{
		RestAssured.given(reqspec).
		queryParam("fields",FilterOutSongsFromPlaylistQuery).
		queryParam("limit", Limit).
		when().
		get(BasePathForAddingSongsToUserPlaylist+"/"+Playlist_id+"/tracks").
		then().
		spec(respspec);
	}
}
