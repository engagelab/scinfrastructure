package models;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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
	
	@Property("sceneId")
	public String sceneId;
	
	@Property("actId")
	public String actId;
	

    
	
   public STask() {
	   
	}

   
   
    public STask(String title, String actId, String sceneId) {
        this.title = title;
        this.sceneId = sceneId;
        this.actId = actId;
    }
    
    

    
    
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
        		.add("id", id)
        		.add("postedAt", postedAt)
                .add("title", title)
                .add("description", description)
                .add("sceneId", sceneId)
                .add("actId", actId)
                .toString();
    }

}
