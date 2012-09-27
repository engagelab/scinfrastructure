package services;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Children;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionList;

public class WorkWithDriveData {

  /**
   * Retrieve a list of File resources.
   * 
   * @param service Drive API service instance.
   * @return List of File resources.
   */
  public static List<File> retrieveAllFiles(Drive service) {
    List<File> result = new ArrayList<File>();
    Files.List request = null;
    try {
      request = service.files().list();
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    do {
      try {
        FileList files = request.execute();

        result.addAll(files.getItems());
        request.setPageToken(files.getNextPageToken());
      } catch (IOException e) {
        System.out.println("An error occurred: " + e);
        request.setPageToken(null);
      }
    } while (request.getPageToken() != null && request.getPageToken().length() > 0);

    return result;
  }

  protected static void printFile(Drive service, String fileId) {

    try {
      File file = service.files().get(fileId).execute();

      System.out.println("Title: " + file.getTitle());
      System.out.println("Description: " + file.getDescription());
      System.out.println("MIME type: " + file.getMimeType());
    } catch (IOException e) {
      System.out.println("An error occured: " + e);
    }
  }

  public static void printAbout(Drive service) {
    try {
      About about = service.about().get().execute();

      System.out.println("Current user name: " + about.getName());
      System.out.println("Root folder ID: " + about.getRootFolderId());
      System.out.println("Total quota (bytes): " + about.getQuotaBytesTotal());
      System.out.println("Used quota (bytes): " + about.getQuotaBytesUsed());
    } catch (IOException e) {
      System.out.println("An error occurred: " + e);
    }
  }

  /**
   * Download a file's content.
   * 
   * @param service Drive API service instance.
   * @param file Drive File instance.
   * @return InputStream containing the file's content if successful,
   *         {@code null} otherwise.
   */
  protected static InputStream downloadFile(Drive service, File file) {
    if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
      try {
        HttpResponse resp = service.getRequestFactory()
                                   .buildGetRequest(new GenericUrl(file.getDownloadUrl()))
                                   .execute();
        return resp.getContent();
      } catch (IOException e) {
        // An error occurred.
        e.printStackTrace();
        return null;
      }
    } else {
      // The file doesn't have any content stored on Drive.
      return null;
    }
  }

  /**
   * Copy an existing file.
   * 
   * @param service Drive API service instance.
   * @param originFileId ID of the origin file to copy.
   * @param copyTitle Title of the copy.
   * @return The copied file if successful, {@code null} otherwise.
   */
  protected static File copyFile(Drive service, String originFileId, String copyTitle) {
    File copiedFile = new File();
    copiedFile.setTitle(copyTitle);
    try {
      return service.files().copy(originFileId, copiedFile).execute();
    } catch (IOException e) {
      System.out.println("An error occurred: " + e);
    }
    return null;
  }
  
  /**
   * Permanently delete a file, skipping the trash.
   *
   * @param service Drive API service instance.
   * @param fileId ID of the file to delete.
   */
  protected static void deleteFile(Drive service, String fileId) {
    try {
      service.files().delete(fileId).execute();
    } catch (IOException e) {
      System.out.println("An error occurred: " + e);
    }
  }
  
  /**
   * Print files belonging to a folder.
   *
   * @param service Drive API service instance.
   * @param folderId ID of the folder to print files from.
   */
  protected static void printFilesInFolder(Drive service, String folderId) throws IOException {
    Children.List request = service.children().list(folderId);

    do {
      try {
        ChildList children = request.execute();

        for (ChildReference child : children.getItems()) {
          System.out.println("File Id: " + child.getId());
        }
        request.setPageToken(children.getNextPageToken());
      } catch (IOException e) {
        System.out.println("An error occurred: " + e);
        request.setPageToken(null);
      }
    } while (request.getPageToken() != null &&
             request.getPageToken().length() > 0);
  }
  
  /**
   * Print information about the specified permission.
   *
   * @param service Drive API service instance.
   * @param fileId ID of the file to print permission for.
   * @param permissionId ID of the permission to print.
   */
  protected static void printPermission(Drive service, String fileId,
      String permissionId) {
    try {
      Permission permission = service.permissions().get(
          fileId, permissionId).execute();

      System.out.println("Name: " + permission.getName());
      System.out.println("Role: " + permission.getRole());
      for (String additionalRole : permission.getAdditionalRoles()) {
        System.out.println("Additional role: " + additionalRole);
      }
    } catch (IOException e) {
      System.out.println("An error occured: " + e);
    }
  }
  
  /**
   * Retrieve a list of permissions.
   *
   * @param service Drive API service instance.
   * @param fileId ID of the file to retrieve permissions for.
   * @return List of permissions.
   */
  public static List<Permission> retrievePermissions(Drive service,
      String fileId) {
    try {
      PermissionList permissions = service.permissions().list(fileId).execute();
      return permissions.getItems();
    } catch (IOException e) {
      System.out.println("An error occurred: " + e);
    }

    return null;
  }

}
