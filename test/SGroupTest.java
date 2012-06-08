

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.junit.*;
import play.mvc.*;
import play.libs.Json;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;



public class SGroupTest {



	/**
	 * Checks that we can build fake request with a json body. In this test, we
	 * use the default method (POST).
	 */
//	@Ignore
//	public void addGroupTest2() {
//		running(fakeApplication(), new Runnable() {
//
//			@SuppressWarnings({ "rawtypes", "unchecked" })
//			public void run() {
//				Map coordinate = new HashMap();
//				coordinate.put("latitude", 33.22);
//				coordinate.put("longitude", 13.11);
//				
//				Map map = new HashMap();
//				map.put("author", "Jeremy");
//				map.put("name", "InterMedia");
//				map.put("description", "I work here");
//				map.put("coordinates", coordinate);
//				JsonNode node = Json.toJson(map);
//				Result result = routeAndCall(fakeRequest("POST", "/placemark")
//						.withJsonBody(node));
//				assertThat(status(result)).isEqualTo(OK);
//				assertThat(contentType(result)).isEqualTo("application/json");
//				JsonNode node2 = Json.parse(contentAsString(result));
//				assertThat(node2.get("author").asText()).isEqualTo("Jeremy");
//				assertThat(node2.get("name").asText()).isEqualTo("InterMedia");
//				assertThat(node2.get("description").asText()).isEqualTo(
//						"I work here");
//			}
//		});
//	}
	
	
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
