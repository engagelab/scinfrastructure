package models;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;


/**
 * @author Muhammad Fahied
 */


@Embedded
public class  SPostit{
	
	@Indexed
	@Property("id")
	public String id = new ObjectId().toString();
	
	@Property("date")
	public String postedAt = new Date().toString();

	@Property("content")
	public String content;
	
	@Property("isPortfolio")
	public Boolean isPortfolio;
	
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
	
	@Property("taskId")
	public String taskId;
	
	
	@Embedded()
    public List <SComment> scomments;
	
//	@PrePersist
//	public void prePersist(){
//		//postedAt = new Date().toString();
//		wxpos = 0;
//		wypos = 0;
//	}
	
    
   public SPostit() {

	}

    public SPostit(String content) {
        this.content = content;
    }
    
    // for flash
    // {"content":"hurray", "xpos":120, "ypos":32}
	public SPostit(String content, int xpos, int ypos, String taskId){
		this.content = content;
		this.isPortfolio = false;
		this.xpos = xpos;
		this.ypos = ypos;
		this.taskId = taskId;
	}
	
	// for web
	public SPostit(int wxpos, int wypos){
		this.wxpos = wxpos;
		this.wypos = wypos;
	}
	
	//delete all comments on this postit
	public void clearSComments(){
	    this.scomments = null;
	}
	
	public SComment postComment(String content) {
		SComment newComment = new SComment(content);
		this.scomments.add(newComment);
		return newComment;
	}
	
//	public SComment findCommentByAuthorId(String authorId) {
//        if (null == authorId) return null;
//        for (SComment comment: this.scomments) {
//           if (authorId.equals(comment.authorId) return comment;
//        }
//        return null;
//    }
	
	

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
        		.add("id", id)
                .add("content", content)
                .add("xpos", xpos)
                .add("ypos", ypos)
                .add("wxpos", wxpos)
                .add("wypos", wypos)
                .add("postedAt", postedAt)
                .add("taskId", taskId)
                .toString();
    }

}
