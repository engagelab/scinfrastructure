package controllers;



import org.codehaus.jackson.JsonNode;


import play.libs.WS;
import play.libs.F.Function;
import play.mvc.Controller;
import play.mvc.Result;
import java.nio.charset.Charset;


public class WrapperOnRubyGDrive extends Controller{
	
	
	 //Locale locale = Locale.getDefault(); // get zh_CN  


	private final static Charset UTF8_CHARSET = Charset.forName("UTF-8");

	public static String decodeUTF8(byte[] bytes) {
	    return new String(bytes, UTF8_CHARSET);
	}

	public static byte[] encodeUTF8(String string) {
	    return string.getBytes(UTF8_CHARSET);
	}
	
	
	public static String rubyServerPath = "http://scihub.uio.no:4568/gdriveFiles";
	
	
	public static Result fetchGDriveFiles()  {
		
		
		JsonNode node = ctx().request().body().asJson();
//		String folderNameString = node.get("folderName").asText();
//		
//		String uString = encodeUTF8(folderNameString).toString();
//
//		Map<String,String> group = new HashMap<String,String>();
//		group.put("folderName", folderNameString);
		
		return async(
			      WS.url(rubyServerPath)
			      .setHeader("Content-Type", "application/json")
			      .post(node.toString()).map(
			        new Function<WS.Response, Result>() {
			          public Result apply(WS.Response response) {
			        	  JsonNode node = response.asJson();
			        	  System.out.println(node.toString());
			            return ok(response.asJson());
			          }
			        }
			      )
			    ); 
	}

}
