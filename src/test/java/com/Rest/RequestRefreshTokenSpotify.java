
//Post request to receive the Refresh code based on OAuth

package com.Rest;

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
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RequestRefreshTokenSpotify {

	RequestSpecBuilder reqspecbuild=null;
	RequestSpecification reqspec=null;
	ResponseSpecBuilder respspecbuild=null;
	ResponseSpecification respspec=null;
	
	@BeforeTest
	@Parameters({"BaseURISpotify","BasePathTokenRefresh"})
	public void preCondition(String BaseURISpotify,String BasePathTokenRefresh)
	{
		reqspecbuild=new RequestSpecBuilder().
						 setBaseUri(BaseURISpotify).
						 setBasePath(BasePathTokenRefresh).
						 setContentType(ContentType.URLENC).
						 setUrlEncodingEnabled(true).
						 log(LogDetail.URI).and().log(LogDetail.BODY);
		reqspec=reqspecbuild.build();
		
		respspecbuild=new ResponseSpecBuilder().
						  expectContentType(ContentType.JSON).
						  expectStatusCode(200).
						  expectResponseTime(Matchers.is(Matchers.lessThan((long)(6000)))).
						  expectBody("token_type",Matchers.is("Bearer")).
						  log(LogDetail.BODY);
		respspec=respspecbuild.build();
	}
	
	@Test
	@Parameters({"Client_ID","Client_Secret","Grant_type","Ref_Token"})
	public void requestRefreshToken(String Client_ID,String Client_Secret,String Grant_type,String Ref_Token)
	{
		HashMap<String, String> hm=new HashMap<String, String>();
		hm.put("client_id", Client_ID);
		hm.put("client_secret", Client_Secret);
		hm.put("grant_type", Grant_type);

		RestAssured.given(reqspec).
					formParams(hm).
					formParams("refresh_token",Ref_Token).
					when().
					post().
					then().
					spec(respspec);
	}
}