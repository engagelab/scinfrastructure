package controllers;



import org.codehaus.jackson.JsonNode;


import play.libs.WS;
import play.libs.F.Function;
import play.mvc.Controller;
import play.mvc.Result;

public class WrapperOnRubyGDrive extends Controller{
	
	
	public static String rubyServerPath = "http://localhost:4568/gdriveFiles";
	
	
	public static Result fetchGDriveFiles()  {
		
		
		JsonNode node = ctx().request().body().asJson();
		
		return async(
			      WS.url(rubyServerPath)
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

}
