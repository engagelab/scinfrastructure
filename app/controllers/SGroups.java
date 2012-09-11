package controllers;

import static play.libs.Json.toJson;


import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.JsonNode;
import models.SGroup;
import models.SUser;
import play.mvc.*;


/**
 * @author Muhammad Fahied
 */

public class SGroups extends Controller {
	
	/*
	 * 
	 * Group Services
	 * 
	 * */
	
	public static Result fetchAllGroups() {

		List<SGroup> groups = SGroup.find.asList();
		
//	     JSONSerializer modelSerializer = new JSONSerializer()
//	     .include("name","_id","susers","susers.name","susers.id").exclude("*"); 
//	    String text = modelSerializer.serialize(groups);
	    response().setContentType("application/json");
	     
		return ok(toJson(groups));
	}
	
	/* POST :  JSON Request
	 
	{
		"name":"Group 1",
		"runId":1
	}
	
	*/
	
	public static Result createGroup() {
		
		//parse JSON from request body
    	JsonNode node =  ctx().request().body().asJson();
    	
    	String name = node.get("name").asText();
    	int runId = node.get("runId").asInt();
		
    	SGroup group = new SGroup(name, runId);
		group.save();
		// producing customized JSON response
		//SGroup cGroup = group.datastore.createQuery(SGroup.class).retrievedFields(true, "name").get();

		return ok(toJson(group));
	}
	
	
	
	public static Result fetchGroupById( String groupId) {
		
		SGroup group = SGroup.find.byId(groupId);
		return ok(toJson(group));
		
	}
	
	
	
	
	public static Result fetchGroupsByRunId( String runId) {
		
		int runid = Integer.parseInt(runId);
		List<SGroup> groups = SGroup.find.field("runId").equal(runid).asList();
		return ok(toJson(groups));
		
	}
	
	
	/*
	 * 
	 * Member Services
	 * 
	 * */
	public static Result fetchGroupMembers(String groupId) {
		
		SGroup group = SGroup.find.byId(groupId);
		List<SUser> users = group.susers;
		return ok(toJson(users));
		
	}
	
	/* POST : JSON request

	{
		"groupId":"4fe424f7da063acbfc99d734" , 
		"name": "Fahied", 
		"email":"anonymous@tmail.com", 
		"age":25 
	}
	
	*/
	public static Result addMember() {
		
		//parse JSON from request body
    	JsonNode node =  ctx().request().body().asJson();
    	String groupId = node.get("groupId").asText();
    	String name = node.get("name").asText();
    	String email = node.get("email").asText();
    	int age = node.get("age").asInt();
    	
		SGroup group = SGroup.find.byId(groupId);
    	
    	//SGroup group = SGroup.find.filter("_id", groupId).get();
    	
		SUser user = new SUser(name, email, age);
		user.save();
		//group.addMember(user);
		if (group.susers == null) {
			group.susers = new ArrayList<SUser>();
		}
		group.susers.add(user);
		group.save();
		
		response().setContentType("application/json");
		return ok(toJson(user));
	}

}