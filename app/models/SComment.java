package models;

import java.util.Date;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.PrePersist;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;

@Embedded
public class  SComment extends Model{
	
	@Property("content")
	public String content;
	
	@Property("date")
	public Date postedAt;
	
	@PrePersist
	public void prePersist(){
		postedAt = new Date();
	}
	

	// FINDERS ----------
    public static final Finder<SComment> find = new Finder<SComment>(SComment.class);
    
   public SComment() {
	   
	}

    public SComment(String content) {
        this.content = content;
    }
    

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
        		.add("_id", id)
                .add("content", content)
                .toString();
    }

}
