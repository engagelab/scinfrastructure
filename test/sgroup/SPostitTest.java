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

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import play.libs.Json;
import play.mvc.Result;

public class SPostitTest {
	
	@Test
	public void addPostit() {
		running(fakeApplication(), new Runnable() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void run() {
				Map postit = new HashMap();
				postit.put("groupId", "4fd5e7c23004f157ced52677");
				postit.put("content", "Heatpump is not pumping engough water for us!");
				postit.put("xpos", 123);
				postit.put("ypos", 25);

				JsonNode node = Json.toJson(postit);
				Result result = routeAndCall(fakeRequest("POST", "/postit")
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
}
