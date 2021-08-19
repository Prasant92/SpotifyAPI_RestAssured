package com.Rest.Spotify_PlaylistsAPI;

import java.io.File;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

public class RequestPUTReorderUserPlaylistItems {

	RequestSpecBuilder reqspecbuild=null;
	RequestSpecification reqspec=null;
	ResponseSpecBuilder respspecbuild=null;
	ResponseSpecification respspec=null;
	
	int size=0;
	
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
	public void orderBeforeRearranging(String FilterOutSongsFromPlaylistQuery,String Limit,String BasePathForAddingSongsToUserPlaylist,String Playlist_id) throws JSONException
	{
		String resp=RestAssured.given(reqspec).
								  queryParam("fields",FilterOutSongsFromPlaylistQuery).
								  queryParam("limit", Limit).
								  when().
								  get(BasePathForAddingSongsToUserPlaylist+"/"+Playlist_id+"/tracks").
								  then().
								  spec(respspec).
								  extract().
								  response().asString();
		JSONObject json=new JSONObject(resp);
		JSONArray json_Arr=json.getJSONArray("items");
		int x=json_Arr.length();
		System.out.println(x);
		
		for(int i=0;i<x;i++)
		{
			System.out.println("Started");
			String res=RestAssured.given(reqspec).
			  			queryParam("fields",FilterOutSongsFromPlaylistQuery).
			  			queryParam("limit", Limit).
			  			when().
			  			get(BasePathForAddingSongsToUserPlaylist+"/"+Playlist_id+"/tracks").
			  			then().
			  			spec(respspec).
			  			extract().
			  			response().
			  			path("items["+i+"].track.album.name").toString();
			System.out.println(res);
		}
	}
	
	@Test(priority = 2)
	@Parameters({"DatafilePath","BasePathForAddingSongsToUserPlaylist","Playlist_id"})
	public void rearranagePlaylistTracks(String DatafilePath,String BasePathForAddingSongsToUserPlaylist,String Playlist_id)
	{
		File f=new File(DatafilePath);
		RestAssured.given(reqspec).
					body(f).
					when().
					put(BasePathForAddingSongsToUserPlaylist+"/"+Playlist_id+"/tracks").
					then().
					spec(respspec);
	}
	
	@Test(priority = 3)
	@Parameters({"FilterOutSongsFromPlaylistQuery","Limit","BasePathForAddingSongsToUserPlaylist","Playlist_id"})
	public void orderAfterRearranging(String FilterOutSongsFromPlaylistQuery,String Limit,String BasePathForAddingSongsToUserPlaylist,String Playlist_id) throws JSONException
	{
		String resp=RestAssured.given(reqspec).
								  queryParam("fields",FilterOutSongsFromPlaylistQuery).
								  queryParam("limit", Limit).
								  when().
								  get(BasePathForAddingSongsToUserPlaylist+"/"+Playlist_id+"/tracks").
								  then().
								  spec(respspec).
								  extract().
								  response().asString();
		JSONObject json=new JSONObject(resp);
		JSONArray json_Arr=json.getJSONArray("items");
		int x=json_Arr.length();
		System.out.println(x);
		
		for(int i=0;i<x;i++)
		{
			System.out.println("Started");
			String res=RestAssured.given(reqspec).
			  			queryParam("fields",FilterOutSongsFromPlaylistQuery).
			  			queryParam("limit", Limit).
			  			when().
			  			get(BasePathForAddingSongsToUserPlaylist+"/"+Playlist_id+"/tracks").
			  			then().
			  			spec(respspec).
			  			extract().
			  			response().
			  			path("items["+i+"].track.album.name").toString();
			System.out.println(res);
		}
	}
}