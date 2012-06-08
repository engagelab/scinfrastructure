package groups;

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

public class MyTest {
	
	@Test
	public void addGroupTest() {
		running(fakeApplication(), new Runnable() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void run() {
				
				Map group = new HashMap();
				group.put("name", "Fahied");
				group.put("runId", 1);

				JsonNode node = Json.toJson(group);
				Result result = routeAndCall(fakeRequest("POST", "/group").withJsonBody(node));
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentType(result)).isEqualTo("application/json");
				JsonNode node2 = Json.parse(contentAsString(result));
				assertThat(node2.get("name").asText()).isEqualTo("Fahied");
				assertThat(node2.get("runId").asInt()).isEqualTo(1);	
			}
		});
	}

}
