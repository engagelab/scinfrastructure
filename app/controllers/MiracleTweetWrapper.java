/**
 *  Wrapper for Miracle Tweet API
 */
package controllers;

import org.codehaus.jackson.JsonNode;

import play.libs.WS;
import play.libs.F.Function;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author Muhammad Fahied
 *
 */



//Wrapper Class for MiracleTweet API which is running on port 9003
public class MiracleTweetWrapper extends Controller{
	
	public static final String SERVER_PATH = "http://localhost:9003";
	
	
	public static Result saveTweet()  {
		
		String rout = "/tweet";
		JsonNode node = request().body().asJson();
		return async(
			      WS.url(SERVER_PATH+rout)
			      .setHeader("Content-Type", "application/json")
			      .post(node.toString()).map(
			        new Function<WS.Response, Result>() {
			          public Result apply(WS.Response response) {
			            return ok(response.asJson());
			          }
			        }
			      )
			    ); 
	}
	
	
	
	
	
	
	public static Result updateTweet()  {
		
		String rout = "/tweet";
		JsonNode node = request().body().asJson();
		return async(
			      WS.url(SERVER_PATH+rout)
			      .setHeader("Content-Type", "application/json")
			      .put(node.toString()).map(
			        new Function<WS.Response, Result>() {
			          public Result apply(WS.Response response) {
			            return ok(response.asJson());
			          }
			        }
			      )
			    ); 	
		  }
	
	
	
	
	
	
	
	
	public static Result fetchTweetByUser(String userName)  {
		
		String rout = "/tweet" +"/"+userName;
		return async(
			      WS.url(SERVER_PATH + rout).get().map(
			        new Function<WS.Response, Result>() {
			          public Result apply(WS.Response response) {
			            return ok(response.asJson());
			          }
			        }
			      )
			    ); 
	  }
	
	
	
	
	
	
	
	
	
	public static Result removeTweet(String tweetId)  {
	
		String rout = "/tweet" +"/"+tweetId;
			return async(
				      WS.url(SERVER_PATH+rout)
				      .delete().map(
				        new Function<WS.Response, Result>() {
				          public Result apply(WS.Response response) {
				            return ok(response.asJson());
				          }
				        }
				      )
				    ); 
			}
	
	






	public static Result addTweetToHashTable()  {
		
		String rout = "/hash";
		JsonNode node = request().body().asJson();
		return async(
			      WS.url(SERVER_PATH+rout)
			      .setHeader("Content-Type", "application/json")
			      .post(node.toString()).map(
			        new Function<WS.Response, Result>() {
			          public Result apply(WS.Response response) {
			            return ok(response.asJson());
			          }
			        }
			      )
			    ); 
		}


	
	public static Result fetchTweetsByHashTag(String hashTag)  {
		
		String rout = "/hash" +"/"+hashTag;
		return async(
			      WS.url(SERVER_PATH + rout)
			      .get().map(
			        new Function<WS.Response, Result>() {
			          public Result apply(WS.Response response) {
			            return ok(response.asJson());
			          }
			        }
			      )
			    ); 
	  }

	
	

	
	
	
	
	

}
