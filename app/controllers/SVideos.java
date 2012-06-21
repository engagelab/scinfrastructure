package controllers;

import static play.libs.Json.toJson;

import java.util.ArrayList;
import java.util.List;

import models.SComment;
import models.SGroup;
import models.SPostit;
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
		
		//int wxpos = node.get("wxpos").asInt();
		//int wypos = node.get("wxpos").asInt();
		String groupId = node.get("groupId").asText();

		SVideo video = new SVideo(title, uri);
		SGroup group = SGroup.find.byId(groupId);
		if (group.svideos == null) {
			group.svideos = new ArrayList<SVideo>();
		}
		group.svideos.add(video);
		group.save();

		return ok(toJson(video));
	}
	
	public static Result updateVideoOnWeb() {

		JsonNode node = ctx().request().body().asJson();
		String videoId = node.get("videoId").asText();
		int wxpos = node.get("wxpos").asInt();
		int wypos = node.get("wxpos").asInt();

		SGroup group = SGroup.find.filter("svideos.id",videoId ).get();
		// Second locate the fruit and remove it:
		SVideo res = new SVideo();
		for (SVideo p : group.svideos) {
			if (p.id.equals(videoId)) {
				res.id = p.id;
				res.wxpos = wxpos;
				res.wypos = wypos;
				res.title = p.title;
				res.uri = p.uri;
				group.svideos.remove(p);
				group.svideos.add(res);
				group.save();
				break;
			}
		}
		return ok(toJson(res));

	}

}
