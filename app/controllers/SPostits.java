package controllers;

import static play.libs.Json.toJson;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import models.SComment;
import models.SGroup;
import models.SImage;
import models.SPostit;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author Muhammad Fahied
 */

public class SPostits extends Controller {

	public static Result fetchPostitById(String positId) {

		// need indexing for postit
		// SPostit postit =
		// SGroup groups = SGroup.find.;

		return ok(toJson("postit"));

	}

	public static Result fetchPostitByGroupId(String groupId) {

		SGroup group = SGroup.find.byId(groupId);
		List<SPostit> postits = group.spostits;
		return ok(toJson(postits));
	}

	public static Result addPostit() {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		String content = node.get("content").asText();
		int xpos = node.get("xpos").asInt();
		int ypos = node.get("xpos").asInt();
		String groupId = node.get("groupId").asText();

		SPostit postit = new SPostit(content, xpos, ypos);
		SGroup group = SGroup.find.byId(groupId);
		if (group.spostits == null) {
			group.spostits = new ArrayList<SPostit>();
		}
		group.spostits.add(postit);
		group.save();

		return ok(toJson(postit));
	}
	
	
	public static Result updatePostitOnWeb() {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		String postitId = node.get("postitId").asText();
		int xpos = node.get("xpos").asInt();
		int ypos = node.get("xpos").asInt();
		String groupId = node.get("groupId").asText();

		/**
		 * 1. find postit 
		 * 2. update its contents
		 * 2. save the group
		 * 
		 * */
		SGroup group = SGroup.find.byId(groupId);
		
		group.save();

		return ok(toJson(group));
	}

	
	
	public static Result updatePostitInFlash() {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		String postitId = node.get("postitId").asText();
		String content = node.get("content").asText();
		int wxpos = node.get("wxpos").asInt();
		int wypos = node.get("wxpos").asInt();
		String groupId = node.get("groupId").asText();

		/**
		 * 1. find postit 
		 * 2. update its contents
		 * 2. save the group
		 * 
		 * */
		SGroup group = SGroup.find.byId(groupId);
		
		group.save();

		return ok(toJson(group));
	}
	
	
	public static Result postCommentOnPostit() {

		JsonNode node = ctx().request().body().asJson();
		String groupId = node.get("groupId").asText();
		String postitId = node.get("postitId").asText();
		String content = node.get("content").asText();
		
		SGroup group = SGroup.find.byId(groupId);
		
		// Find postit
		SPostit postit = new SPostit();
		postit.scomments.add(new SComment());
		
		//SPostit postits = group.datastore.createUpdateOperations(arg0);
		// update postit
		return ok(toJson(group));
	}

	
	
}

/*
 * 
 * 
 * Query<Blog> q =
 * datastore.createQuery(Blog.class).field(Mapper.ID_KEY).equal(blogId);
 * UpadteOperation<Blog> ops =
 * datastore.createUpdateOperations(Blog.class).add("comments", comment);
 * datastore.update(q, ops);//adds the comment object to the comment array.
 * 
 * 
 * 
 * `ops.addAll("comments" commentList)`
 * 
 * 
 * ops = datastore.createUpdateOperations(Blog.class).set("likes." + likeIdStr,
 * likeObject);
 * 
 * datastore.update(q, ops);
 */
