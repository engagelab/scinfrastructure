package models;

import java.util.Date;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.PrePersist;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;


/**
 * @author Muhammad Fahied
 */

@Embedded
public class  SComment{
	
	@Property("content")
	public String content;
	
	@Property("date")
	public String postedAt = new Date().toString();;
	//public Date postedAt;

    
   public SComment() {
	   
	}

    public SComment(String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("content", content)
                .toString();
    }

}
