package controllers;

import static play.libs.Json.toJson;

import java.util.List;



import models.SUser;

import play.mvc.*;

public class SUsers extends Controller {
  
  
  public static Result addUser() {

  	SUser user = new SUser();
  	user.save();
  	
  	List<SUser> users = SUser.find.asList();
      return ok(toJson(users));
  }
  
}