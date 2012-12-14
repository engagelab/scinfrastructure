package controllers;

import static play.libs.Json.toJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.FinalPortFolioTaskComment;
import models.SGroup;
import models.SImage;
import models.SProject;
import models.STask;
import models.SVideo;
import play.mvc.Controller;
import play.mvc.Result;

public class Portfolio extends Controller {

	public static Result fetchGroupContributionsByTask(String groupId,
			String taskId) {

		SGroup group = SGroup.find.byId(groupId);

		List<SVideo> videos = group.svideos;
		List<SImage> images = group.simages;
		
		// temporary storage
		List<SVideo> tvideos = new ArrayList<SVideo>();
		List<SImage> timages = new ArrayList<SImage>();
		
		// FIXME use java templates

		if (videos != null) {

			for (SVideo video : videos) {
				if (video.taskId.equals(taskId)) {
					tvideos.add(video);
				}
			}
		}

		if (images != null) {

			for (SImage image : images) {
				if (image.taskId.equals(taskId)) {
					timages.add(image);
				}
			}
		}
		
		Map<String, Object> portfolioMap = new HashMap<String, Object>();
		portfolioMap.put("simages", timages);
		portfolioMap.put("svideos", tvideos);

		return ok(toJson(portfolioMap));
	}

	public static Result getSelectedItemsForTemporaryPortfolio(String groupId,
			String sceneId) {
		SGroup group = SGroup.find.byId(groupId);

		List<SVideo> videos = group.svideos;
		List<SImage> images = group.simages;

		// temporary storage
		List<SVideo> tvideos = new ArrayList<SVideo>();
		List<SImage> timages = new ArrayList<SImage>();

		for (String taskId : getTasksForSceneId(sceneId)) {
			if (videos != null) {

				for (SVideo video : videos) {
					if (video.taskId.equals(taskId) && video.isPortfolio) {
						tvideos.add(video);
					}
				}
			}

			if (images != null) {

				for (SImage image : images) {
					if (image.taskId.equals(taskId) && image.isPortfolio) {
						timages.add(image);
					}
				}
			}
		}

		Map<String, Object> portfolioMap = new HashMap<String, Object>();
		portfolioMap.put("simages", timages);
		portfolioMap.put("svideos", tvideos);

		return ok(toJson(portfolioMap));
	}

	private static ArrayList<String> getTasksForSceneId(String sceneId) {
		SProject menu = SProject.find.get();

		if (menu == null) {
			int empty[] = null;
			return new ArrayList<String>();
		}

		List<STask> tasks = menu.stasks;

		ArrayList<String> retrievedTaskIds = new ArrayList<String>();

		for (STask task : tasks) {
			if (task.sceneId.equals(sceneId)) {
				retrievedTaskIds.add(task.id);
			}
		}

		return retrievedTaskIds;
	}
	
	public static Result fetchGroupPortfolioCommentByTask(String groupId, String taskId) {

		SGroup group = SGroup.find.byId(groupId);

		List<FinalPortFolioTaskComment> finalPortFolioTaskComments = group.finalPortfolioTaskComments;
		List<FinalPortFolioTaskComment> tfinalPortFolioTaskComments = new ArrayList<FinalPortFolioTaskComment>();

		if (finalPortFolioTaskComments != null) {
			for (FinalPortFolioTaskComment finalPortFolioTaskComment : finalPortFolioTaskComments) {
				if (finalPortFolioTaskComment.taskId.equals(taskId)) {
					tfinalPortFolioTaskComments.add(finalPortFolioTaskComment);
				}
			}
		}

		return ok(toJson(tfinalPortFolioTaskComments));
	}
}
