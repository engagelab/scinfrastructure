package controllers;

import static play.libs.Json.toJson;

import java.util.ArrayList;
import java.util.List;

import models.*;

import org.codehaus.jackson.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;


/**
 * @author Muhammad Fahied
 **/


public class SVideos extends Controller {
	
	
	
	

	public static Result fetchVideoById(String videoId) {

		SGroup group = SGroup.find.filter("svideos.id", videoId).get();
		SVideo res = null;
		for (SVideo p : group.svideos) {
			if (p.id.equals(videoId)) {
				res = p;
				break;
			}
		}

		if (res == null) {
			return ok("{}");
		}
		return ok(toJson(res));

	}
	
	
	
	
	
	
	
	
	

	public static Result fetchVideosByGroupId(String groupId) {

		// String groupId =
		// ctx().request().queryString().get("groupId").toString();
		SGroup group = SGroup.find.byId(groupId);
		List<SVideo> videos = group.svideos;
		if (group.svideos == null) {
			return ok("[]");
		}
		return ok(toJson(videos));
	}
	
	
	
	
	
	
	
	
	
	
	

	/*
	 * POST : JSON Request { "groupId":"4fe42505da063acbfc99d735" , "title":
	 * "My 2 video", "uri":"http://www.youtube.com/XYZ" }
	 */

	// TODO : Validation Required
	public static Result addVideo() {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		String title = node.get("title").asText();
		String uri = node.get("uri").asText();
		String taskId = node.get("taskId").asText();

		String groupId = node.get("groupId").asText();

		SVideo video = new SVideo(title, uri, taskId);
		SGroup group = SGroup.find.byId(groupId); // GUL
		if (group.svideos == null) {
			group.svideos = new ArrayList<SVideo>();
		}
		group.addVideo(video);
		group.save();

		return ok(toJson(video));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/*
	 * POST : JSON request
	 * 
	 * { "videoId":"4fe43023da063acbfc99d764" , "wxpos": 400, "wypos":400 }
	 */

	// TODO : Validation required
	public static Result updateVideo() {

		JsonNode node = ctx().request().body().asJson();
		String videoId = node.get("id").asText();
		if (SGroup.find.field("svideos.id").equal(videoId).get() == null) {
			return status(401, "Not Authorized");
		}  
		
		int xpos = node.get("xpos").asInt();
		int ypos = node.get("ypos").asInt();
		Boolean isPortfolio =node.get("isPortfolio").asBoolean();
		Boolean isFinalPortfolio =node.get("isFinalPortfolio").asBoolean();
		
		Query<SGroup> query = SGroup.datastore.createQuery(SGroup.class).field("svideos.id").equal(videoId);
		UpdateOperations<SGroup> ops = SGroup.datastore
				.createUpdateOperations(SGroup.class).disableValidation()
				.set("svideos.$.xpos", xpos)
				.set("svideos.$.ypos", ypos)
				.set("svideos.$.isPortfolio", isPortfolio)
				.set("svideos.$.isFinalPortfolio", isFinalPortfolio);
		
		SGroup.datastore.findAndModify(query, ops); 
		
		return ok(toJson(fetchVideoById(videoId)));
		 
		//return status(200, "OK");
//		SVideo video = null;
//		for (SVideo p : group.svideos) {
//			if (p.id.equals(videoId)) {
//				video = p;
//				break;
//			}
//		}

	}

	
	
	
	
	
	
	
	
	public static Result deleteVideoById(String videoId) {

		SGroup group = SGroup.find.filter("svideos.id", videoId).get();
		// Second locate the fruit and remove it:
		for (SVideo p : group.svideos) {
			if (p.id.equals(videoId)) {
				group.svideos.remove(p);
				group.save();
				break;
			}
		}
		return ok("deleted successfully");
	}

	/*
	 
	 */

	public static Result postCommentOnVideo() {

		JsonNode node = ctx().request().body().asJson();
		String videoId = node.get("videoId").asText();
		String content = node.get("content").asText();

		
		// Second locate the fruit and remove it:
		SComment comment = new SComment(content);
		// update member of embedded object list
		Query<SGroup> query = SGroup.datastore.createQuery(SGroup.class)
				.field("svideos.id").equal(videoId);
		UpdateOperations<SGroup> ops = SGroup.datastore
				.createUpdateOperations(SGroup.class).disableValidation()
				.add("svideos.$.scomments", comment);
		
		SGroup group = SGroup.datastore.findAndModify(query, ops);

		
		SVideo video = null;
		for (SVideo p : group.svideos) {
			if (p.id.equals(videoId)) {
				video = p;
				break;
			}
		}
		return ok(toJson(video));

	}

	public static Result fetchCommentsByVideo(String videoId) {

		SGroup group = SGroup.find.filter("svideos.id", videoId).get();
		
		

		List<SComment> comments = null;
		for (SVideo p : group.svideos) {
			if (p.id.equals(videoId)) {
				comments = p.scomments;
				break;
			}

		}

		if (comments == null) {
			return ok("[]");
		}
		return ok(toJson(comments));
	}

}
