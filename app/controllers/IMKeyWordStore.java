package controllers;


import org.codehaus.jackson.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;
import play.libs.F.Function;
import play.libs.WS;

/**
 * @author Muhammad Fahied
 */


// Wrapper Class for IMKEYWORDSTORE API which is running on port 9002

public class IMKeyWordStore extends Controller{
	
	public static String KeywordServerPath = "http://localhost:9002/keywords";
	
	
	
	
	
	
	public static Result addKeywordStore()  {
		JsonNode node = request().body().asJson();
		return async(
			      WS.url(KeywordServerPath)
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
	
	
	
	
	
	
	public static Result fetchAllKeywordStore()  {
		
		return async(
			      WS.url(KeywordServerPath).get().map(
			        new Function<WS.Response, Result>() {
			          public Result apply(WS.Response response) {
			            return ok(response.asJson());
			          }
			        }
			      )
			    ); 
		}
	
	
	
	
	
	
	
	public static Result fetchKeywordStorebyGroup(String groupId)  {
		
		return async(
			      WS.url(KeywordServerPath+"/"+groupId).get().map(
			        new Function<WS.Response, Result>() {
			          public Result apply(WS.Response response) {
			            return ok(response.asJson());
			          }
			        }
			      )
			    ); 
	  }
	
	
	
	
	
	
public static Result updateKeywordStore(String storeId)  {
		
	JsonNode node = request().body().asJson();
	return async(
		      WS.url(KeywordServerPath+"/"+storeId)
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
	

}
