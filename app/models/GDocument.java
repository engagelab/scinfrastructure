package models;



import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;



/**
 * @author Muhammad Fahied
 */



@Entity(value = "gdrive", noClassnameStored = true, concern = "NORMAL")

public class GDocument extends Model{
    
	@Property("folderName")
    public String folderName;
	
	@Property("folderUrl")
    public String folderUrl;
	
	@Property("taskId")
	public String taskId;
	
	@Property("taskTitle")
	public String taskTitle;
	
	@Property("runId")
	public int runId;

	// FINDERS ----------

    public static final Finder<GDocument> find = new Finder<GDocument>(GDocument.class);
	
    // Constructors
    public GDocument() {
		// TODO Auto-generated constructor stub
	}
	
	
	
    public GDocument(String folderName, String folderUrl, String taskId, String taskTitle, int runId) 
    {
    	
    	this.folderName = folderName;
    	this.folderUrl = folderUrl;
    	this.taskId = taskId;
    	this.taskTitle = taskTitle;
    	this.runId = runId;
    	
	}
    
    
    
    
 
    public void deleteGDocument(String folderId) 
    {
    	this.delete();
	}
     


    @Override
    public String toString() 
    {
        return Objects.toStringHelper(this)
        		
                .add("folderName", folderName)
                .add("folderUrl", folderUrl)
                .add("taskId", taskId)
                .add("taskTitle", taskTitle)
                .add("runId", runId)
                .toString();
    }
    
	
	
}
