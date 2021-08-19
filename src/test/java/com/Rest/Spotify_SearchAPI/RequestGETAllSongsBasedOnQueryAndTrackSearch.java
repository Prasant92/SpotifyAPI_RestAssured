
//Search any song from any genre/movie/track/album/artist

package com.Rest.Spotify_SearchAPI;

import java.util.ArrayList;
import java.util.HashMap;

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

public class RequestGETAllSongsBasedOnQueryAndTrackSearch {

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
	@Parameters({"BasePathForSearchingSongTrack","QueryName","Type","CountryCode","Limit","Include_External"})
	public void searchUsingQueryAndType(String BasePathForSearchingSongTrack,String QueryName,String Type,String CountryCode,String Limit,String Include_External)
	{
		HashMap<String, String> hm=new HashMap<String, String>();
		hm.put("q", QueryName);
		hm.put("type", Type);
		hm.put("market", CountryCode);
		hm.put("limit", Limit);
		hm.put("include_external", Include_External);
		
		Response resp=RestAssured.given(reqspec).
					queryParams(hm).
					when().
					get(BasePathForSearchingSongTrack).
					then().
					spec(respspec).
					extract().
					response();
		
		Integer len=Integer.valueOf(Limit);
		
		if(Type.equals("track"))
		{
			for(int i=0;i<len;i++)
			{
				String track_name=resp.path("tracks.items["+i+"].name");
				System.out.println(track_name);
				String track_id=resp.path("tracks.items["+i+"].id");
				System.out.println(track_id);
			}	
		}
		
		else if(Type.equals("album"))
		{
			for(int i=0;i<len;i++)
			{
				String album_name=resp.path("albums.items["+i+"].name");
				System.out.println(album_name);
				String album_id=resp.path("albums.items["+i+"].id");
				System.out.println(album_id);
			}	
		}
		
		else if(Type.equals("playlist"))
		{
			for(int i=0;i<len;i++)
			{
				String playlist_name=resp.path("playlists.items["+i+"].name");
				System.out.println(playlist_name);
				String playlist_id=resp.path("playlists.items["+i+"].id");
				System.out.println(playlist_id);
			}	
		}
		
		else if(Type.equals("show"))
		{
			for(int i=0;i<len;i++)
			{
				String show_name=resp.path("shows.items["+i+"].name");
				System.out.println(show_name);
				String show_id=resp.path("shows.items["+i+"].id");
				System.out.println(show_id);
			}	
		}
		
		else if(Type.equals("episode"))
		{
			for(int i=0;i<len;i++)
			{
				String episode_name=resp.path("episodes.items["+i+"].name");
				System.out.println(episode_name);
				String episode_id=resp.path("episodes.items["+i+"].id");
				System.out.println(episode_id);
			}	
		}
		
		else if(Type.equals("artist"))
		{
			for(int i=0;i<len;i++)
			{
				String artist_name=resp.path("artists.items["+i+"].name");
				System.out.println(artist_name);
				String artist_id=resp.path("artists.items["+i+"].id");
				System.out.println(artist_id);
			}	
		}
	}
}