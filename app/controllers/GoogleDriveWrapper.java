package controllers;

import static play.libs.Json.toJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

import play.mvc.Controller;
import play.mvc.Result;
import services.OAuthCredent;
import services.WorkWithDriveData;
import services.OAuthCredent.CodeExchangeException;

public class GoogleDriveWrapper extends Controller{
	
	
	
	  private static final String USER_EMAIL = "fahied@gmail.com";
	
	public static Result talkingResult() throws IOException {

		String authorizeUrl = OAuthCredent.getAuthorizationUrl(USER_EMAIL, null);
	    System.out.println("Paste this url in your browser: " + authorizeUrl);

	    // Wait for the authorization code
	    System.out.println("Type the code you received here: ");
	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    String authorizationCode = in.readLine();
	    Credential credential = null;
	    try {
	      credential = OAuthCredent.exchangeCode(authorizationCode);
	    } catch (CodeExchangeException e) {
	      e.printStackTrace();
	    }
	    System.out.println("credential: " + credential.toString());

	    HttpTransport httpTransport = new NetHttpTransport();
	    JacksonFactory jsonFactory = new JacksonFactory();
	    Drive drive = new Drive.Builder(httpTransport, jsonFactory, credential).build();

	    System.out.println("Drive: " + drive.toString());

	    List<File> listFile = WorkWithDriveData.retrieveAllFiles(drive);
	    System.out.println("fileList: " + listFile.size());

	    /*
	     * for (int i = 0; i < 50; i++){ File one = listFile.get(i);
	     * System.out.println(one.getTitle()); if
	     * (one.getTitle().equals("copyZip.zip")){
	     * //WorkWithDriveData.deleteFile(drive, one.getId()); } }
	     */

	    File one = listFile.get(0);
	    System.out.println(" Parents: " + one.getParents() + " UserPermission(): "
	        + one.getUserPermission() + " Etag: " + one.getEtag() + "EmbedLink: " + one.getEmbedLink()
	        + "Description: " + one.getDescription() + "Title: " + one.getTitle() + "Owner names: "
	        + one.getOwnerNames() + "Last modifying: " + one.getLastModifyingUserName() + "Mime type: "
	        + one.getMimeType());

	    // Print information about the current user along with the Drive API
	    WorkWithDriveData.printAbout(drive);

	    // Print information about the File in GDrive
	    System.out.println(one.getTitle());

	    // Print permission
	    // WorkWithDriveData.printPermission(drive, one.getId());

	    List<Permission> listPermission = WorkWithDriveData.retrievePermissions(drive, one.getId());
	    System.out.println("retrievePermission: " + listPermission);
	    one = listFile.get(20);
	    listPermission = WorkWithDriveData.retrievePermissions(drive, one.getId());
	    System.out.println("retrievePermission: " + listPermission);

	    About about = drive.about().get().execute();

	    /*
	     * for (int i = 0; i < listFile.size(); i++){ one = listFile.get(i); if
	     * (WorkWithDriveData.retrievePermissions(drive,
	     * one.getId()).get(0).getType().equals("user")){
	     * System.out.println(one.getTitle()); } }
	     */
	    // WorkWithDriveData.printPermission(drive, permissionId, permissionId);

	    // Copy file
	    // WorkWithDriveData.copyFile(drive, one.getId(), "copyZip.zip");
	    return ok(toJson("Yes What"));
	  }
	     
		
	}


