package controllers;

import static play.libs.Json.toJson;



import models.SGroup;
import play.mvc.Controller;
import play.mvc.Result;

public class Portfolio extends Controller{
	
	
	public static Result fetchGroupProfile(String groupId) {

		SGroup groups = SGroup.find.byId(groupId);
		
		
		

		return ok(toJson(groups));
	}

}
