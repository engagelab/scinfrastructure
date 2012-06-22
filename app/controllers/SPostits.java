package controllers;

import static play.libs.Json.toJson;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import models.SComment;
import models.SGroup;
import models.SPostit;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author Muhammad Fahied
 */

public class SPostits extends Controller {

	@Inject
	public static Datastore datastore; // requestStaticInjection(..)

	
	
	
	
	//FIXME 
	public static Result fetchPostitById(String positId) {

		// need indexing for postit
		// SPostit postit =
		// SGroup groups = SGroup.find.;

		return ok("not available yet");

	}

	
	
	
	public static Result fetchPostitsByGroupId(String groupId) {

		SGroup group = SGroup.find.byId(groupId);
		List<SPostit> postits = group.spostits;
		if (group.spostits == null) {
			return ok("[]");
		}
		return ok(toJson(postits));
	}

	/* POST : JSON Request
	
	{
		"groupId":"4fe42505da063acbfc99d735" , 
		"content": "My 1 posit", 
		"xpos":100, 
		"ypos":100 
	}
	
	*/
	
	public static Result addPostit() {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		String content = node.get("content").asText();
		int xpos = node.get("xpos").asInt();
		int ypos = node.get("xpos").asInt();
		String groupId = node.get("groupId").asText();

		SPostit postit = new SPostit(content, xpos, ypos);
		// SGroup group = SGroup.find.byId(groupId);
		SGroup group = SGroup.find.byId(groupId);
		if (group.spostits == null) {
			group.spostits = new ArrayList<SPostit>();
		}
		group.spostits.add(postit);
		group.save();

		return ok(toJson(postit));
	}

	
	/* POST : JSON Request
	{
		"postitId":"4fe4298dda063acbfc99d74b" , 
		"content": "My 1 modified posit", 
		"xpos":105, 
		"ypos":105 
	}
	*/
	
	public static Result updatePostitInFlash() {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		String postitId = node.get("postitId").asText();
		String content = node.get("content").asText();
		int xpos = node.get("xpos").asInt();
		int ypos = node.get("xpos").asInt();

		// update member of embedded object list
		Query<SGroup> query = datastore.createQuery(SGroup.class).field("spostits.id").equal(postitId);
		UpdateOperations<SGroup> ops = datastore.createUpdateOperations(SGroup.class).disableValidation()
				.set("spostits.$.content", content)
				.set("spostits.$.xpos", xpos)
				.set("spostits.$.ypos", ypos);
		datastore.update(query, ops);
		return ok("success");
		
	}

	
	/* POST : JSON Request
	
	{
		"postitId":"4fe4298dda063acbfc99d74b" , 
		"wxpos":105, 
		"wypos":105 
	}
	
	*/
	
	public static Result updatePostitOnWeb() {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		String postitId = node.get("postitId").asText();
		int wxpos = node.get("wxpos").asInt();
		int wypos = node.get("wxpos").asInt();

		SGroup group = SGroup.find.filter("spostits.id", postitId).get();
		// Second locate the fruit and remove it:
		SPostit res = new SPostit();
		for (SPostit p : group.spostits) {
			if (p.id.equals(postitId)) {
				res.id = p.id;
				res.wxpos = wxpos;
				res.wypos = wypos;
				res.content = p.content;
				res.xpos = p.xpos;
				res.ypos = p.ypos;
				group.spostits.remove(p);
				group.spostits.add(res);
				group.save();
				break;
			}
		}
		return ok(toJson(res));

	}


	public static Result deletePostitById(String postitId) {

		// First find out the group from which you want to remove one postit:
		// SGroup group = SGroup.find.byId(groupId);
		SGroup group = SGroup.find.filter("spostits.id", postitId).get();
		// Second locate the fruit and remove it:
		for (SPostit p : group.spostits) {
			if (p.id.equals(postitId)) {
				group.spostits.remove(p);
				group.save();
				break;
			}
		}
		
		// try this one
		/*
		 * 
		 * Cart.o().removeAll("fruit.name",
		 * "apple").update(Cart.q().filter("_id", theId));
		 */
		return ok("deleted");
	}

	
	
	/* POST
	
	{
		"postitId":"4fe4298dda063acbfc99d74b" , 
		"content": "My 1 comment"
	}
	
	*/
	
	public static Result postCommentOnPostit() {

		JsonNode node = ctx().request().body().asJson();
		String postitId = node.get("postitId").asText();
		String content = node.get("content").asText();

		SGroup group = SGroup.find.filter("spostits.id", postitId).get();
		// Second locate the fruit and remove it:
		SPostit res = new SPostit();
		for (SPostit p : group.spostits) {
			if (p.id.equals(postitId)) {
				group.spostits.remove(p);

				if (p.scomments == null) {
					p.scomments = new ArrayList<SComment>();
				}
				p.scomments.add(new SComment(content));
				group.spostits.add(p);
				group.save();
				res = p;
				break;
			}

		}

		return ok(toJson(res));
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

// db.testarray.update({"albums.photos._id":ObjectId("4f545d1bc328103812d00000")},{'$set':{"albums.$.photos.1.name":"play132"}})
/**
 * 1. find postit 2. update its contents 2. save the group
 * 
 * */

// DBCollection gCollection = datastore.getCollection(SGroup.class);
//
// // convert JSON to DBObject directly
// DBObject q = (DBObject)
// JSON.parse("{'spostits._id':'4fd87071300453388399f0c1'}");
// DBObject o = (DBObject)
// JSON.parse("{$set:{'spostits.$.xpos':12343, 'spostits.$.ypos':300}}");
//
// gCollection.update(q, o,false,true);

// Query<SGroup> updateQuery =
// datastore.createQuery(SGroup.class).field("spostits._id").equal("4fd87071300453388399f0c1");
// // change the
// UpdateOperations<SGroup> ops =
// datastore.createUpdateOperations(SGroup.class).set("spostits.xpos.$",
// "datastore");
// //UpdateOperations<SGroup> ops =
// datastore.createUpdateOperations(SGroup.class).i;
// datastore.update(updateQuery, ops);
//
// BasicDBObject query = new BasicDBObject();
// query.put("", "");

//
// SGroup group = SGroup.find.filter("spostits._id",
// "4fd87071300453388399f0c1").get();
// Query<SGroup> query =
// datastore.createQuery(SGroup.class).field("spostits._id").equal("4fd87071300453388399f0c1");
// UpdateOperations<SGroup> ops =
// datastore.createUpdateOperations(SGroup.class).disableValidation().set("spostits.$.xpos",
// 1000);
// datastore.update(query, ops);
//

// Search group with embbedded document Id
// SGroup group = SGroup.find.filter("spostits._id",
// "4fd882233004ab185d315289").get();
// SGroup group2 = SGroup.find.filter("spostits.xpos", 1230).get();

// SPostit sPostit = group.datastore.

// group.find("type","event").field("parentIDs").hasThisOne(id).asList();
// group.find.filter("spostits.$._id", "id").asList();
//
// UpdateOperations operations =
// group.datastore.createUpdateOperations(SGroup.class).set("spostits.$.wxpos",
// 1);

