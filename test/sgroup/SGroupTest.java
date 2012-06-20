package sgroup;

import java.util.HashMap;
import java.util.Map;



import org.codehaus.jackson.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.libs.Json;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

/**
 * @author Muhammad Fahied
 */

public class SGroupTest {

	@Test
	public void createGroup() {
		running(fakeApplication(), new Runnable() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void run() {
				Map group = new HashMap();
				group.put("name", "NewYork");
				group.put("runId", 2);
				
				JsonNode node = Json.toJson(group);
				Result result = routeAndCall(fakeRequest("POST", "/group")
						.withJsonBody(node));
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentType(result)).isEqualTo("application/json");
				JsonNode node2 = Json.parse(contentAsString(result));
				assertThat(node2.get("name").asText()).isEqualTo("NewYork");
				assertThat(node2.get("runId").asInt()).isEqualTo(2);
			}
		});
	}
	
	
	// Expected request body : {"groupId":"4fd217d130049ddad80506f1" , "name": "Fahied", "email":"anonymous@tmail.com", "age":25, "imageUri":"/image/43d217d130049ddad98506g4" }

	@Ignore
	public void addMember() {
		running(fakeApplication(), new Runnable() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void run() {
				Map gmemeber = new HashMap();
				gmemeber.put("groupId", "4fd867b930044dadd70e89b6");
				gmemeber.put("name", "Fahied");
				gmemeber.put("email", "anonymous@tmail.com");
				gmemeber.put("age", 25);
				gmemeber.put("imageUri", "/image/43d217d130049ddad98506g4");
			
				JsonNode node = Json.toJson(gmemeber);
				Result result = routeAndCall(fakeRequest("POST", "/member")
						.withJsonBody(node));
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentType(result)).isEqualTo("application/json");
				JsonNode node2 = Json.parse(contentAsString(result));
				assertThat(node2.get("name").asText()).isEqualTo("Fahied");
				assertThat(node2.get("email").asText()).isEqualTo("anonymous@tmail.com");
				assertThat(node2.get("age").asInt()).isEqualTo(25);
				assertThat(node2.get("imageUri").asText()).isEqualTo("/image/43d217d130049ddad98506g4");
				
			}
		});
	}
	
	
}
