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
public class  SScene{
	
	@Indexed
	@Property("id")
	public String id = new ObjectId().toString();

	@Property("date")
	public String postedAt = new Date().toString();
	
	@Property("title")
	public String title;
	
	@Property("icon")
	public String icon;
	
	
	@Property("actId")
	public String actId;
	

    
	
   public SScene() {
	   
	}

   
   
    public SScene(String title, String icon, String actId) {
        this.title = title;
        this.icon = icon;
        this.actId = actId;
    }
    
    
    
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("title", title)
                .add("icon", icon)
                .add("actId", actId)
                .toString();
    }

}
