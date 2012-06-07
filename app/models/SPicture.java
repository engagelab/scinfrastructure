package models;

import java.io.File;
import java.io.IOException;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;

import util.GridFsHelper;

/**
 * @author Muhammad Fahied
 */
@Embedded
public class SPicture {

	@Property("author")
    public String author;
	
	@Property("fileId")
    public String fileId;
    
	
	@Property("fileName")
    public String fileName;
	
	@Property("filePath")
    public String filePath;

	
    public SPicture() 
    {
    	
	}
    
    public SPicture(String author, File image) throws IOException 
    {
    	this.author = author;
    	this.fileName = image.getName();
    	//save file in GridFS and retrieve its ID to be stored in fileId
    	this.fileId = GridFsHelper.storeFile(image);
    	this.filePath = createUriForFile(fileId);
    }
    
    
    public SPicture(String author) 
    {
        this.author = author;
    }

    @Override
    public String toString() 
    {
        return Objects.toStringHelper(this)
                .add("author", author)
                .add("fileName", fileName)
                .add("fileId", fileId)
                .add("filePath", filePath)
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
