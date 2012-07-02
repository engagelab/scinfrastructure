package controllers;

import static play.libs.Json.toJson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.jbfilter.bean.FilterBean;
import org.jbfilter.bean.PropertyAccessor;
import org.jbfilter.bean.factory.FilterBeans;
import org.jbfilter.bean.factory.FilterComponentBeans;
import org.jbfilter.core.fcomps.single.ContainsStringFilterComponent;


import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import models.SComment;
import models.SGroup;
import models.SPostit;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author Muhammad Fahied
 */

public class SPostits extends Controller {
	
	
	
	// @Inject
	 //public static Datastore datastore; // requestStaticInjection(..)
	 
	 

	public static Result fetchPostitById(String postitId) {

		//SPostit postit = SPostit.datastore.find(SPostit.class).get();
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
	
	
	
	
	
	
	
	
	
	/*
	 * http://code.google.com/p/jbfilter/source/browse/trunk/jbfilter-bean/src/test/java/org/jbfilter/test/demo/IntroExample.java
	 * */
	
	
	public static Result fetchPostitsByTPRG(String taskId, String runId, String groupId ) {

		SGroup group = SGroup.find.byId(groupId);
		Collection<SPostit> cp = group.spostits;
		
		// Filter results using jbfilter libs
		//create a filter
		FilterBean<SPostit> filter = FilterBeans.newFilter();
        
		// 1st possibility : use reflection to access property
		//FilterComponent<SPostit> filterComponent = FilterComponentBeans.newContainsStringFilterComponent("taskId");
		
        // 2nd possibility (preferred) : create component with PropertyAccessor interface
		PropertyAccessor<SPostit, String> propAcc = new PropertyAccessor<SPostit, String>() {
            public String getPropertyValue(SPostit postit) {
                    return postit.taskId;
            }
		};
		
        ContainsStringFilterComponent<SPostit> fc1b = FilterComponentBeans.newContainsStringFilterComponent("taskId", propAcc);

        // attach the filter components to the filter
        filter.addFilterComponent(fc1b);
        
        // set the components relevant settings
        fc1b.setValue(taskId);
        // filter
        List<SPostit> filteredPostits = filter.filter(cp);

		if (filteredPostits == null) {
			return ok("[]");
		}
		return ok(toJson(filteredPostits));
		
		
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

	public static Result updatePostitInFlash(String postitId) {

		// parse JSON from request body
		JsonNode node = ctx().request().body().asJson();
		//String postitId = node.get("postitId").asText();
		String content = node.get("content").asText();
		int xpos = node.get("xpos").asInt();
		int ypos = node.get("ypos").asInt();

		// new
		//SGroup group = SGroup.find.filter("spostits.id", postitId).get();
		Query<SGroup> query = SGroup.datastore.createQuery(SGroup.class)
				.field("spostits.id").equal(postitId);
		UpdateOperations<SGroup> ops = SGroup.datastore
				.createUpdateOperations(SGroup.class).disableValidation()
				.set("spostits.$.content", content)
				.set("spostits.$.xpos", xpos).set("spostits.$.ypos", ypos);

		SGroup group = SGroup.datastore.findAndModify(query, ops);

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

		Query<SGroup> query = SGroup.datastore.createQuery(SGroup.class).field("spostits.id").equal(postitId);
		
		UpdateOperations<SGroup> ops = SGroup.datastore.createUpdateOperations(SGroup.class).disableValidation()
				.set("spostits.$.wxpos", wxpos)
				.set("spostits.$.wypos", wypos);

		SGroup group = SGroup.datastore.findAndModify(query, ops);

		
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
		
		Query<SGroup> query = SGroup.datastore.createQuery(SGroup.class)
				.field("spostits.id").equal(postitId);
		UpdateOperations<SGroup> ops = SGroup.datastore
				.createUpdateOperations(SGroup.class).disableValidation()
				.add("spostits.$.scomments", comment);
	
		SGroup group = SGroup.datastore.findAndModify(query, ops);

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

//SGroup group = SGroup.datastore.find(SGroup.class, "spostits.taskId", taskId).get();		
//DB nDb = group.datastore.getDB();
//
//BasicDBObject doc = new BasicDBObject();
//BasicDBObject edoc = new BasicDBObject();
//edoc.put("$slice", 1);
//doc.put("spostits.taskId", taskId);
//doc.put("spostits", edoc);

// SGroup testGroup = SGroup.find.byId(groupId).field("spostits.taskId").equals(taskId);

//while(cur.hasNext()) {
//   System.out.println(cur.next());
//}


//SPostit postit = group.datastore.find(SPostit.class).get();



//db.blogs.find({"posts.title":"Mongodb!"}, {posts:{$slice: 1}})

