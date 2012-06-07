package models;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;

@Embedded
public class SVideo {
	
	@Property("title")
    public String title;
	
	@Property("uri")
    public String uri;
	
	@Property("wxpos")
    public int wxpos;
	
	@Property("wypos")
    public int wypos;

	@Embedded("comments")
	public List<SComment> sComments;
	
	 public SVideo(String title, String uri, int wxpos, int wypos) {
	    	this.title = title;
	        this.uri = uri;
	        this.wxpos = wxpos;
	        this.wypos = wypos;
	        this.sComments = new ArrayList<SComment>();
		}


		@Override
	    public String toString() {
	        return Objects.toStringHelper(this)
	        		.add("title", title)
	                .add("uri", uri)
	                .add("wxpos", wxpos)
	                .add("wypos", wypos)
	                .add("comments", sComments)
	                .toString();
	    }

}
