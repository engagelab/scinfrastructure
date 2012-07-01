package controllers;

import static play.libs.Json.toJson;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;

import models.SComment;
import models.SGroup;
import models.SPostit;

import play.mvc.Controller;
import play.mvc.Result;
import scala.sys.process.ProcessBuilderImpl.DaemonBuilder;
import sun.security.action.PutAllAction;

/**
 * @author Muhammad Fahied
 */

public class SPostits extends Controller {

	public static Result fetchPostitById(String postitId) {

		SGroup group = SGroup.find.filter("spostits.id", postitId).get();
		SPostit res = null;
		for (SPostit p : group.spostits) {
			if (p.id.equals(postitId)) {
				res = p;
				break;
			}
		}

		if (res == null) {
			return ok("{}");
		}
		return ok(toJson(res));

	}

	public static Result fetchPostitsByGroupId(String groupId) {

		SGroup group = SGroup.find.byId(groupId);
		List<SPostit> postits = group.spostits;
		if (group.spostits == null) {
			return ok("[]");
		}
		return ok(toJson(postits));
	}
	
	
	public static Result fetchPostitsByTPRG(String taskId, String runId, String groupId ) {

		
		
		SGroup group = SGroup.find.byId(groupId);
		
		DB nDb = group.datastore.getDB();
		
		
		
		
		BasicDBObject doc = new BasicDBObject();
		BasicDBObject edoc = new BasicDBObject();
		edoc.put("$slice", 1);
        doc.put("spostits.taskId", taskId);
        doc.put("spostits", edoc);

        List<Integer> sized = nDb.getCollection("groups").find(doc).getSizes();
        
//       while(cur.hasNext()) {
//           System.out.println(cur.next());
//       }
		
        
        //SPostit postit = group.datastore.find(SPostit.class).get();
        
      
        
		//db.blogs.find({"posts.title":"Mongodb!"}, {posts:{$slice: 1}})
		
		List<SPostit> postits = group.spostits;
		if (group.spostits == null) {
			return ok("[]");
		}
		return ok(toJson(postits));
	}
	
	
	
	
	
	

	

	/*
	 * POST : JSON Request
	 * 
	 * { "groupId":"4fe42505da063acbfc99d735" , "content": "My 1 posit",
	 * "xpos":100, "ypos":100 }
	 */

	public static Result addPostit() {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		String content = node.get("content").asText();
		int xpos = node.get("xpos").asInt();
		int ypos = node.get("ypos").asInt();
		String groupId = node.get("groupId").asText();
		String taskId = node.get("taskId").asText();
		int runId = node.get("runId").asInt();

		SPostit postit = new SPostit(content, xpos, ypos, taskId, runId);
		// SGroup group = SGroup.find.byId(groupId);
		SGroup group = SGroup.find.byId(groupId);
		if (group.spostits == null) {
			group.spostits = new ArrayList<SPostit>();
		}
		group.addPostit(postit);
		group.save();

		return ok(toJson(postit));
	}

	/*
	 * POST : JSON Request { "postitId":"4fe4298dda063acbfc99d74b" , "content":
	 * "My 1 modified posit", "xpos":105, "ypos":105 }
	 */

	public static Result updatePostitInFlash() {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		String postitId = node.get("postitId").asText();
		String content = node.get("content").asText();
		int xpos = node.get("xpos").asInt();
		int ypos = node.get("xpos").asInt();

		// new
		SGroup group = SGroup.find.filter("spostits.id", postitId).get();
		Query<SGroup> query = group.datastore.createQuery(SGroup.class)
				.field("spostits.id").equal(postitId);
		UpdateOperations<SGroup> ops = group.datastore
				.createUpdateOperations(SGroup.class).disableValidation()
				.set("spostits.$.content", content)
				.set("spostits.$.xpos", xpos).set("spostits.$.ypos", ypos);
		group.datastore.update(query, ops);

		
		SGroup ngroup = SGroup.find.filter("spostits.id", postitId).get();
		SPostit res = null;
		for (SPostit p : ngroup.spostits) {
			if (p.id.equals(postitId)) {
				res = p;
				break;
			}
		}

		if (res == null) {
			return ok("{}");
		}
		return ok(toJson(res));
	}

	/*
	 * POST : JSON Request
	 * 
	 * { "postitId":"4fe4298dda063acbfc99d74b" , "wxpos":105, "wypos":105 }
	 */

	public static Result updatePostitOnWeb(String postitId) {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		int wxpos = node.get("wxpos").asInt();
		int wypos = node.get("wypos").asInt();

		SGroup group = SGroup.find.filter("spostits.id", postitId).get();
		Query<SGroup> query = group.datastore.createQuery(SGroup.class).field("spostits.id").equal(postitId);
		
		UpdateOperations<SGroup> ops = group.datastore.createUpdateOperations(SGroup.class).disableValidation()
				.set("spostits.$.wxpos", wxpos)
				.set("spostits.$.wypos", wypos);
		group.datastore.update(query, ops);

		SGroup ngroup = SGroup.find.filter("spostits.id", postitId).get();
		SPostit res = null;
		for (SPostit p : ngroup.spostits) {
			if (p.id.equals(postitId)) {
				res = p;
				break;
			}
		}

		if (res == null) {
			return ok("{}");
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

	/*
	 * POST
	 * 
	 * { "postitId":"4fe4298dda063acbfc99d74b" , "content": "My 1 comment" }
	 */

	public static Result postCommentOnPostit() {

		JsonNode node = ctx().request().body().asJson();
		String postitId = node.get("postitId").asText();
		String content = node.get("content").asText();

		SComment comment = new SComment(content);
		SGroup group = SGroup.find.filter("spostits.id", postitId).get();
		Query<SGroup> query = group.datastore.createQuery(SGroup.class)
				.field("spostits.id").equal(postitId);
		UpdateOperations<SGroup> ops = group.datastore
				.createUpdateOperations(SGroup.class).disableValidation()
				.add("spostits.$.scomments", comment);
		group.datastore.update(query, ops);

		
		SGroup ngroup = SGroup.find.filter("spostits.id", postitId).get();
		SPostit res = null;
		for (SPostit p : ngroup.spostits) {
			if (p.id.equals(postitId)) {
				res = p;
				break;
			}
		}

		if (res == null) {
			return ok("{}");
		}
		return ok(toJson(res));
	}

	
	
	public static Result fetchCommentOnPostit(String postitId) {

		SGroup group = SGroup.find.filter("spostits.id", postitId).get();

		List<SComment> comments = null;
		for (SPostit p : group.spostits) {
			if (p.id.equals(postitId)) {
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

