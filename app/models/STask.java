package models;


import java.util.Date;


import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;


/**
 * @author Muhammad Fahied
 */

@Embedded
public class  STask{
	
	@Indexed
	@Property("id")
	public String id = new ObjectId().toString();

	@Property("date")
	public String postedAt = new Date().toString();
	
	@Property("title")
	public String title;
	
	@Property("description")
	public String description;
	
	//FIXME should be enumeration
	@Property("taskType")
	public String taskType;
	
	@Property("icon")
	public String icon;
	
	@Property("sceneId")
	public String sceneId;
	
	@Property("actId")
	public String actId;
	
	
   public STask() {
	   
	}

   
   
    public STask(String title,String description, String taskType, String icon, String actId, String sceneId) {
    	this.postedAt = new Date().toString(); 
    	this.title = title;
        this.description = description;
        this.taskType = taskType;
        this.icon = icon;
        this.sceneId = sceneId;
        this.actId = actId;
    }
    
    
    // without ActID
    public STask(String title,String description, String taskType, String icon, String sceneId) {
    	this.postedAt = new Date().toString(); 
    	this.title = title;
        this.description = description;
        this.taskType = taskType;
        this.icon = icon;
        this.sceneId = sceneId;
        //Hint : Not using Acts in this Run
        this.actId = null;
    }
    
    
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
        		.add("id", id)
        		.add("postedAt", postedAt)
                .add("title", title)
                .add("description", description)
                .add("taskType", taskType)
                .add("icon", icon)
                .add("sceneId", sceneId)
                .add("actId", actId)
                .toString();
    }

}
