package controllers;

import static play.libs.Json.toJson;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;


import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class RollCalls extends Controller {
	
	
	
	public static Result GetCurrentRunId() {

		Map<String, Integer> currentrun = new HashMap<String, Integer>();
		currentrun.put("runId", 3);
		JsonNode node = Json.toJson(currentrun);
		return ok(toJson(node));
	}

}
