package controllers;

import static play.libs.Json.toJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.*;

import play.mvc.Controller;
import play.mvc.Result;

public class Portfolio extends Controller{
	
	
	public static Result fetchGroupContributionsByTask(String groupId, String taskId) {

		SGroup group = SGroup.find.byId(groupId);
		
		List<SVideo> videos = group.svideos;
		List<SImage> images = group.simages;
		List<SPostit> postits = group.spostits;
		
		//temporary storage
		List<SVideo> tvideos = new ArrayList<SVideo>();
		List<SImage> timages = new ArrayList<SImage>();
		List<SPostit> tpostits = new ArrayList<SPostit>();
		
		// FIXME use java templates
		
		if (videos != null) {
			
			for(SVideo video :videos)
			{
				if (video.taskId == taskId) {
					tvideos.add(video);
				}
			}
		}
		
		if (images != null) {
			
			for(SImage image :images)
			{
				if (image.taskId == taskId) {
					timages.add(image);
				}
			}
		}
		
		
		if (postits != null) {
			
			for(SPostit postit :postits)
			{
				if (postit.taskId == taskId) {
					tpostits.add(postit);
				}
			}
		}
		
		Map<String, Object>  portfolioMap = new HashMap<String, Object>();
		portfolioMap.put("spostits", tpostits);
		portfolioMap.put("simages", timages);
		portfolioMap.put("svideos", tvideos);

		return ok(toJson(portfolioMap));
	}

}
