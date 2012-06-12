package controllers;

import static play.libs.Json.toJson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;

import com.mongodb.gridfs.GridFSDBFile;

import models.SGroup;
import models.SImage;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import utils.GridFsHelper;

/**
 * @author Muhammad Fahied
 */

public class SImages extends Controller {

	public static Result fetchImagesById(String imageId) {

		// need indexing for postit
		// SPostit postit =
		// SGroup groups = SGroup.find.;

		return ok(toJson("imageInfo"));

	}

	public static Result fetchImagesByGroupId(String groupId) {
		SGroup group = SGroup.find.byId(groupId);
		List<SImage> images = group.simages;
		if (images == null)
			return ok(toJson("{status: No Picture added yet}"));
		else
			return ok(toJson(images));
	}

	@SuppressWarnings("unused")
	public static Result storeImage(String groupId, String author) {

		String pathname = "/Users/userxd/Pictures/fahied.jpg";
		File imageData = new File(pathname);
		SImage image = null;
		if (imageData == null)
			return ok(toJson("{status: No Image found}"));
		try {
			image = new SImage(author, imageData);
			SGroup group = SGroup.find.byId(groupId);

			if (group.simages == null) {
				group.simages = new ArrayList<SImage>();
			}

			group.simages.add(image);
			group.save();
		} catch (IOException e) {
			flash("uploadError", e.getMessage());
		}
		return ok(toJson(image));
	}

	public static Result showImage(String imageId) throws IOException {
		GridFSDBFile file = GridFsHelper.getFile(imageId);
		byte[] bytes = IOUtils.toByteArray(file.getInputStream());
		return Results.ok(bytes).as(file.getContentType());
	}

	// {"imageId":"3423j342kjl¿23h1", "wxpos":120, "wypos":32}
	public static Result updateImagePosition() {
		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		int wxpos = node.get("wxpos").asInt();
		int wypos = node.get("wypos").asInt();
		String groupId = node.get("groupId").asText();
		SGroup group = SGroup.find.byId(groupId);
		String postitIt = node.get("postitId").asText();

		return ok(toJson("postit"));
	}

}
