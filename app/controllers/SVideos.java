package controllers;

import static play.libs.Json.toJson;

import java.util.ArrayList;
import java.util.List;

import models.SComment;
import models.SGroup;
import models.SVideo;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.inject.Inject;

public class SVideos extends Controller {
	
	@Inject
    public static Datastore datastore; // requestStaticInjection(..)

	public static Result fetchVideoById(String positId) {

		// need indexing for Video
		// SVideo Video =
		// SGroup groups = SGroup.find.;

		return ok(toJson("Video"));

	}

	public static Result fetchVideosByGroupId(String groupId) {

		SGroup group = SGroup.find.byId(groupId);
		List<SVideo> videos = group.svideos;
		return ok(toJson(videos));
	}

	public static Result addVideo() {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		String title = node.get("title").asText();
		String uri = node.get("uri").asText();
		
		int xpos = node.get("xpos").asInt();
		int ypos = node.get("xpos").asInt();
		String groupId = node.get("groupId").asText();

		SVideo video = new SVideo(title, uri, xpos, ypos);
		SGroup group = SGroup.find.byId(groupId);
		if (group.svideos == null) {
			group.svideos = new ArrayList<SVideo>();
		}
		group.svideos.add(video);
		group.save();

		return ok(toJson(video));
	}
	
	public static Result updateVideoOnWeb() {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		String videoId = node.get("videoId").asText();
		int wxpos = node.get("wxpos").asInt();
		int wypos = node.get("wxpos").asInt();

		// update member of embedded object list 
		Query<SGroup> query = datastore.createQuery(SGroup.class).field("svideos._id").equal(videoId);
		UpdateOperations<SGroup> ops = datastore.createUpdateOperations(SGroup.class).disableValidation()
				.set("svideos.$.wxpos", wxpos)
				.set("svideos.$.wypos", wypos);
		datastore.update(query, ops);
		return ok(toJson("group"));

	}
	
	public static Result updateVideoInFlash() {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		String videoId = node.get("videoId").asText();
		String content = node.get("content").asText();
		int xpos = node.get("xpos").asInt();
		int ypos = node.get("xpos").asInt();

		// update member of embedded object list 
		Query<SGroup> query = datastore.createQuery(SGroup.class).field("svideos._id").equal(videoId);
		UpdateOperations<SGroup> ops = datastore.createUpdateOperations(SGroup.class).disableValidation()
				.set("svideos.$.content", content)
				.set("svideos.$.xpos", xpos)
				.set("svideos.$.ypos", ypos);
		datastore.update(query, ops);
		
		return ok(toJson("group"));
	}
	
	
//	public static Result postCommentOnVideo() {
//
//		JsonNode node = ctx().request().body().asJson();
//		String groupId = node.get("groupId").asText();
//		String videoId = node.get("videoId").asText();
//		String content = node.get("content").asText();
//		
//		SGroup group = SGroup.find.byId(groupId);
//		
//		// Find video
//		SVideo video = new SVideo();
//		video.scomments.add(new SComment());
//		
//		//SVideo videos = group.datastore.createUpdateOperations(arg0);
//		// update video
//		return ok(toJson(group));
//	}

}
