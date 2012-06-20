package sgroup;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.routeAndCall;
import static play.test.Helpers.running;
import static play.test.Helpers.status;
import static play.test.Helpers.*;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.junit.Ignore;
import org.junit.Test;

import controllers.routes;

import play.libs.Json;
import play.mvc.Result;

public class SPostitTest {
	
	@Ignore
	public void addPostit() {
		running(fakeApplication(), new Runnable() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void run() {
				Map postit = new HashMap();
				postit.put("groupId", "4fd867b930044dadd70e89b6");
				postit.put("content", "Heatpump is not pumping engough water for us!");
				postit.put("xpos", 123);
				postit.put("ypos", 25);

				JsonNode node = Json.toJson(postit);
				Result result = routeAndCall(fakeRequest(POST, "/postit")
						.withJsonBody(node));
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentType(result)).isEqualTo("application/json");
				JsonNode node2 = Json.parse(contentAsString(result));
				assertThat(node2.get("content").asText()).isEqualTo("Heatpump is not pumping engough water for us!");
				assertThat(node2.get("xpos").asInt()).isEqualTo(123);
				assertThat(node2.get("ypos").asInt()).isEqualTo(25);
				
			}
		});
	}
	
	

	@Test
	public void UpdatePostitInFlash() {
		running(fakeApplication(), new Runnable() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void run() {
				Map postit = new HashMap();
				postit.put("postitId", "4fd882233004ab185d315289");
				postit.put("content", "Heatpump is not pumping engough water for us!");
				postit.put("xpos", 222);
				postit.put("ypos", 222);
				JsonNode node = Json.toJson(postit);
				
				Result result = callAction(routes.ref.SPostits.updatePostitOnWeb(),
                        fakeRequest().withJsonBody(node, "PUT"));
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo("application/json");
				//JsonNode node2 = Json.parse(contentAsString(result));
				//assertThat(node2.get("content").asText()).isEqualTo("Heatpump is not pumping engough water for us!");
				//assertThat(node2.get("xpos").asInt()).isEqualTo(123);
				//assertThat(node2.get("ypos").asInt()).isEqualTo(25);
			}
		});
	}
	
	 @Test
	    public void UpdatePostitOnWeb() {
	        running(fakeApplication(), new Runnable() {
	        	@SuppressWarnings({ "rawtypes", "unchecked" })
				@Override
	            public void run() {
	            	Map postit = new HashMap();
					postit.put("postitId", "4fd882233004ab185d315289");
					postit.put("wxpos", 222);
					postit.put("wypos", 222);
					JsonNode node = Json.toJson(postit);
					
	                Result result = callAction(routes.ref.SPostits.updatePostitOnWeb(),
	                        fakeRequest().withJsonBody(node, "PUT"));
	                assertThat(status(result)).isEqualTo(OK);
	                assertThat(contentType(result)).isEqualTo("application/json");
	                //JsonNode node2 = Json.parse(contentAsString(result));
	               // assertThat(node2.get("wxp").asInt()).isEqualTo(2);
	               // assertThat(node2.get("key3").asBoolean()).isTrue();
	            }
	        });
	    }
	 
	 
//	 @Test
//	    public void DeletePostitById() {
//	        running(fakeApplication(), new Runnable() {
//				@Override
//	            public void run() {
////	                Result result = callAction(routes.ref.SPostits.deletePostitById(),
////	                        fakeRequest());
//	                assertThat(status(result)).isEqualTo(OK);
//	                assertThat(contentType(result)).isEqualTo("application/json");
//	            }
//	        });
//	    }
	 
	 
	 
}
