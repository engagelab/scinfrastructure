package controllers;


import static play.libs.Json.toJson;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFSDBFile;

import models.*;


import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Http.MultipartFormData.FilePart;
import utils.GridFsHelper;
import views.html.*;

/**
 * @author Muhammad Fahied
 */

public class SDocuments extends Controller {
	
	private static final Form<SDocument> documentForm = form(SDocument.class);
	
	

	public static Result showBlank()
	{
		return ok(show.render(documentForm));
		}
	
	
	
	
	public static Result list() {
		
		List<SDocument> docs = SDocument.find.asList();
		
		Set docSet = new HashSet(docs);

		return ok(list.render(docSet));
		}

	
	
	public static Result save() {

		Form<SDocument> boundForm = documentForm.bindFromRequest();
		if (boundForm.hasErrors()) {
			flash("error", "Please correct the form below.");
			return badRequest(show.render(boundForm));
		}
		SDocument document = boundForm.get();
		// SDocument.add(document);
		flash("success",
				String.format("Successfully added product %s", document));
		return redirect(routes.SDocuments.list());

	}
	
	public static Result show(String id) {
		final SDocument document = SDocument.find.byId(id);
		if (document == null) {
		return notFound("Document %s does not exist.");
		}
		Form<SDocument> filledForm = documentForm.fill(document);
		return ok(show.render(filledForm));
		}
	
	
	
	
	
	
	
	
	
	public static Result fetchAllDocuments() {

		List<SDocument> docs = SDocument.find.asList();

		if (docs == null) {
			return ok("{}");
		}
		return ok(toJson(docs));

	}
	
	
	
	
	
	public static Result fetchDocumentById(String docId) {

		SDocument doc = SDocument.find.byId(docId);

		if (doc == null) {
			return ok("{}");
		}
		return ok(toJson(doc));

	}

	
	
	
	
	
	
	public static Result fetchDocumentsByTaskId(String taskId) {

		List<SDocument> docs = SDocument.find.filter("taskId", taskId).asList();
		
		if (docs == null)
		
			return ok("[]");
		
		else
		
			return ok(toJson(docs));
	}


	
	
	
	
	
	public static Result uploadDocument(String taskId, String taskName, String runId) {

		FilePart filePart = ctx().request().body().asMultipartFormData().getFile("file");
		
		SDocument doc = null;

		int runIdINT = Integer.parseInt(runId);
		
		if (filePart.getFile() == null)
			return ok(toJson("{status: No Image found}"));
		try {
			doc = new SDocument(filePart.getFile(), filePart.getFilename(), filePart.getContentType(), taskId, taskName, runIdINT);
			doc.save();
		} catch (IOException e) {
			flash("uploadError", e.getMessage());
		}
		return ok(toJson(doc));
	}
	
	
	
	
	
	
	
	
	
	

	public static Result getDocument(String fileId) throws IOException {
		
		GridFSDBFile file = GridFsHelper.getFile(fileId);
		
		//InputStream is = file.getInputStream();
		 //return ok(is);
		if (file == null) {
			
			return ok("No Document found!!");
		}
		
		//custom filename signatures for Play 2.0.2
		String filenameString = "attachment; filename="+file.getFilename();
		
		response().setHeader("Content-Disposition", filenameString);

		byte[] bytes = IOUtils.toByteArray(file.getInputStream());
		
		return Results.ok(bytes).as(file.getContentType());
		
	}

	
	
	
	
	
	
	
	public static Result deleteDocumentById(String docId) throws MongoException, IOException {
		
		// Remove our users
        //Query<SDocument> doc = SDocument.datastore.createQuery( SDocument.class ).filter("id", docId);
        //SDocument.datastore.delete( doc );
		
		SDocument doc = SDocument.find.filter("id", docId).get();
		doc.deleteDocument(doc.fileId);
		
		return ok("deleted");
	}
	
	
	
	
public static Result deleteDocumentsByTaskId(String taskId) throws MongoException, IOException {
		
		// Remove our users
        //Query<SDocument> doc = SDocument.datastore.createQuery( SDocument.class ).filter("id", docId);
        //SDocument.datastore.delete( doc );
		
		List<SDocument> docs = SDocument.find.filter("taskId", taskId).asList();
		
		for (SDocument doc : docs) 
		{
			doc.deleteDocument(doc.fileId);

		}
		
		return ok("deleted successfully");
	}
	
	
}

