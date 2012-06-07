package models;

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.PrePersist;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;

@Embedded
public class  SPostit extends Model{
	
	@Property("_id")
	public ObjectId id;
	
	@Property("content")
	public String content;
	
	@Property("date")
	public Date postedAt;
	
	// Variables to store xy position of  on Flash App
	@Property("xpos")
	public int xpos;
	@Property("ypos")
	public int ypos;

	// Variables to store xy position of  on Web App
	@Property("wxpos")
	public int wxpos;
	@Property("wypos")
	public int wypos;
	
	@PrePersist
	public void prePersist(){
		postedAt = new Date();
		wxpos = 0;
		wypos = 0;
	}
	

	// FINDERS ----------
    public static final Finder<SPostit> find = new Finder<SPostit>(SPostit.class);
    
   public SPostit() {

	}

    public SPostit(String content) {
        this.content = content;
    }
    
	public SPostit(String content, int xpos, int ypos){
		this.content = content;
		this.xpos = xpos;
		this.ypos = ypos;
	}

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
        		.add("_id", id)
                .add("content", content)
                .add("xpos", xpos)
                .add("ypos", ypos)
                .toString();
    }

}
