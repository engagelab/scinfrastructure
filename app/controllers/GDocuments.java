package controllers;

import static play.libs.Json.toJson;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonNode;

import models.GDocument;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.gdrive;



/**
 * @author Muhammad Fahied
 */

public class GDocuments extends Controller{

	
	
	public static Result list() {
		
		List<GDocument> gdocs = GDocument.find.asList();
		
		Set<GDocument> gdocSet = new HashSet<GDocument>(gdocs);

		return ok(gdrive.render(gdocSet));
		//return ok("opss it is working");
		}
	
    
    
    
	public static Result addGDriveFolder() {
		
		//parse JSON from request body
    	JsonNode node =  ctx().request().body().asJson();
    	String folderName = node.get("folderName").asText();
    	String folderUrl = node.get("folderUrl").asText();
    	String taskId = node.get("taskId").asText();
    	String taskTitle = node.get("taskTitle").asText();
    	int runId = node.get("runId").asInt();

    	GDocument gDocument = new GDocument(folderName, folderUrl, taskId, taskTitle, runId);
    	gDocument.save();
		
		response().setContentType("application/json");
		return ok(toJson(gDocument));
	}
    
    
}
