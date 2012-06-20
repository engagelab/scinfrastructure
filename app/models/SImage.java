package models;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;

import utils.GridFsHelper;

/**
 * @author Muhammad Fahied
 */
@Embedded
public class SImage {

	@Property("author")
    public String author;
	
	@Property("fileId")
    public String fileId;
    
	
	@Property("fileName")
    public String fileName;
	
	@Property("filePath")
    public String filePath;

	@Embedded()
    public List <SComment> scomments;
	
    public SImage() 
    {
    	
	}
    
    public SImage(String author, File image) throws IOException 
    {
    	this.author = author;
    	this.fileName = image.getName();
    	//save file in GridFS and retrieve its ID to be stored in fileId
    	this.fileId = GridFsHelper.storeFile(image);
    	this.filePath = createUriForFile(fileId);
    }
    
    
    public SImage(String author) 
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
