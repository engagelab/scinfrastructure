package models;

import java.io.File;
import java.io.IOException;

import java.util.Date;

import utils.GridFsHelper;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;
import com.mongodb.MongoException;


/**
 * @author Muhammad Fahied
 */

@Entity(value = "documents", noClassnameStored = true, concern = "NORMAL")

public class SDocument extends Model{
	
	@Property("date")
	public String postedAt = new Date().toString();
	
	@Property("fileId")
    public String fileId;
    
	@Property("fileName")
    public String fileName;
	
	@Property("contentType")
	public String contentType;
	
	@Property("filePath")
    public String filePath;
	
	@Property("taskId")
	public String taskId;
	
	@Property("taskName")
	public String taskName;
	
	@Property("runId")
	public int runId;

	// FINDERS ----------

    public static final Finder<SDocument> find = new Finder<SDocument>(SDocument.class);
	
    // Constructors
    public SDocument() {
		// TODO Auto-generated constructor stub
	}
	
	
	
    public SDocument(File file, String fileName, String contentType, String taskId, String taskName, int runId) throws IOException 
    {
    	this.fileName = fileName;
    	this.contentType = contentType;
    	//save file in GridFS and retrieve its ID to be stored in fileId
    	this.fileId = GridFsHelper.storeFile(file,fileName,contentType);
    	this.filePath = createUriForFile(fileId);
    	this.taskId = taskId;
    	this.taskName = taskName;
    	this.runId = runId;
    	
	}
    
    
    
    
 
    public void deleteDocument(String fileId) throws MongoException, IOException 
    {
    	this.delete();
    	GridFsHelper.deleteFile(fileId);
    	
	}
    
       


    @Override
    public String toString() 
    {
        return Objects.toStringHelper(this)
        		
                .add("fileName", fileName)
                .add("fileId", fileId)
                .add("filePath", filePath)
                .add("taskId", taskId)
                .add("fileName", fileName)
                .add("runId", runId)
                .toString();
    }
    
    
    
    
    
	private String createUriForFile(String gridfsId) throws IOException {
		//Map<String,Object> map = new HashMap<String,Object>();
		//map.put("id", file.getId().toString());
		//return Router.getFullUrl("Application.showImage", map);
		
		String uri = "/doc/"+gridfsId;
		return uri;
		
	}
	
	
}
