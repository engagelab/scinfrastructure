package models;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;
import com.mongodb.MongoException;

import utils.GridFsHelper;

/**
 * @author Muhammad Fahied
 */
@Embedded
public class SImage {

	@Indexed
	@Property("id")
	public String id = new ObjectId().toString();
	
	@Property("date")
	public String postedAt = new Date().toString();
	
//	@Property("author")
//    public String author;
	
	@Property("fileId")
    public String fileId;
    
	
	@Property("fileName")
    public String fileName;
	

	@Property("contentType")
	public String contentType;
	
	@Property("filePath")
    public String filePath;
	
	@Property("isPortfolio")
	public Boolean isPortfolio;
	
	@Property("isFinalPortfolio")
	public Boolean isFinalPortfolio;
	
	@Property("xpos")
    public int xpos;
	
	@Property("ypos")
    public int ypos;
	
	@Property("taskId")
	public String taskId;
	

	@Embedded()
    public List <SComment> scomments;

	
	
	
	public SImage() {
		
	}
	
	
	
	
	
    public SImage(File file, String fileName, String contentType, String taskId, boolean isPortFolio) throws IOException 
    {
    	this.fileName = fileName;
    	this.contentType = contentType;
    	//save file in GridFS and retrieve its ID to be stored in fileId
    	this.fileId = GridFsHelper.storeFile(file,fileName,contentType);
    	this.filePath = createUriForFile(fileId);
    	this.isPortfolio = isPortFolio;
    	this.isFinalPortfolio = false;
    	this.taskId = taskId;
    	
	}
    
    
    
    
 
    public void deleteImage(String fileId) throws MongoException, IOException 
    {
    	GridFsHelper.deleteFile(fileId);
    	
	}
    
    
    
    


    @Override
    public String toString() 
    {
        return Objects.toStringHelper(this)
        		
                .add("fileName", fileName)
                .add("fileId", fileId)
                .add("filePath", filePath)
                .add("isPortfolio", isPortfolio)
                .add("isFinalPortfolio", isFinalPortfolio)
                .add("taskId", taskId)
                .toString();
    }
    
    
    
    
    
	private String createUriForFile(String gridfsId) throws IOException {
		//Map<String,Object> map = new HashMap<String,Object>();
		//map.put("id", file.getId().toString());
		//return Router.getFullUrl("Application.showImage", map);
		
		String uri = "/image/"+gridfsId;
		return uri;
		
	}
}
