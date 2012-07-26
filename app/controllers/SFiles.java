package controllers;


import static play.libs.Json.toJson;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFSDBFile;

import models.*;


import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Http.MultipartFormData.FilePart;
import utils.GridFsHelper;



/**
 * @author Muhammad Fahied
 */

public class SFiles extends Controller {

	

	public static Result fetchFileById(String fileId) {

		SFile file = SFile.find.byId(fileId);

		if (file == null) {
			return ok("{}");
		}
		return ok(toJson(file));

	}

	
	
	
	
	
	
	public static Result fetchFilesByTaskId(String taskId) {

		List<SFile> files = SFile.find.filter("taskId", taskId).asList();
		
		if (files == null)
		
			return ok("[]");
		
		else
		
			return ok(toJson(files));
	}


	
	
	public static Result uploadFile(String taskId, String runId) {

		FilePart filePart = ctx().request().body().asMultipartFormData().getFile("file");
		
		SFile file = null;

		int runIdINT = Integer.parseInt(runId);
		
		if (filePart.getFile() == null)
			return ok(toJson("{status: No Image found}"));
		try {
			file = new SFile(filePart.getFile(), filePart.getFilename(), filePart.getContentType(), taskId, runIdINT);
			file.save();
		} catch (IOException e) {
			flash("uploadError", e.getMessage());
		}
		return ok(toJson(file));
	}
	
	
	
	
	
	
	
	
	
	

	public static Result showImage(String fileId) throws IOException {
		
		GridFSDBFile file = GridFsHelper.getFile(fileId);
		
		byte[] bytes = IOUtils.toByteArray(file.getInputStream());
		
		return Results.ok(bytes).as(file.getContentType());
		
	}

	
	
	
	
	
	
	
	public static Result deleteFileById(String fileId) throws MongoException, IOException {
		
		WriteResult res = SFile.find.byId(fileId).delete();
		return ok(res.getError());
	}
		
	

	
	
	public static String getFileExtension(String filePath){  
		  StringTokenizer stk=new StringTokenizer(filePath,".");  
		  String FileExt="";  
		  while(stk.hasMoreTokens()){  
		   FileExt=stk.nextToken();  
		  }  
		  return FileExt;  
		 } 
	

}
