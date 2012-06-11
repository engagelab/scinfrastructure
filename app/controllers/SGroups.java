package controllers;

import static play.libs.Json.toJson;
import java.util.List;


import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonNode;


import models.SGroup;
import models.SUser;
import play.mvc.*;

/**
 * @author Muhammad Fahied
 */

public class SGroups extends Controller {
	
	
	
	public static Result fetchAllGroups() {

		List<SGroup> groups = SGroup.find.asList();
		return ok(toJson(groups));
	}
	
	
	
	public static Result fetchGroupsByRunId( String runId) {
		
		int runid = Integer.parseInt(runId);
		List<SGroup> groups = SGroup.find.field("runId").equal(runid).asList();
		return ok(toJson(groups));
		
	}
	
	
	
	
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
	
	
	
	
// Expected request body : {"groupId":"4fd217d130049ddad80506f1" , "name": "Fahied", "email":"anonymous@tmail.com", "age":25, "imageurl":"/image/43d217d130049ddad98506g4" }
	public static Result addMember() {
		
		//parse JSON from request body
    	JsonNode node =  ctx().request().body().asJson();
    	
    	String groupId = node.get("groupId").asText();
    	String name = node.get("name").asText();
    	String email = node.get("email").asText();
    	int age = node.get("age").asInt();
    	String imageUri = node.get("imageUri").asText();
    	
    	ObjectId objId = ObjectId.massageToObjectId(groupId);
		SGroup group = SGroup.find.byId(objId);
		SUser user = new SUser(name, email, age, imageUri);
		user.save();
		group.addMember(user);
		group.save();
		
		return ok(toJson(user));
	}

	
	public static Result fetchGroupMembers(String groupId) {
		
		SGroup group = SGroup.find.byId(groupId);
		List<SUser> users = group.susers;
		return ok(toJson(users));
		
	}
	
	

}