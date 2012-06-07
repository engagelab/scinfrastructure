package models;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Reference;
import com.google.common.base.Objects;


@Entity(value = "placemark", noClassnameStored = true, concern = "NORMAL")
public class SGroup extends Model {
	
	@Property("name")
	public String name;
	
	@Property("runId")
	public int runId;
    
	@Reference("groupMembers")
    public List <SUser> sUsers;
	
	@Embedded("sPostits")
    public List <SPostit> sPostits;
	
	@Embedded("sImage")
    public List <SImage> sImages;
	
	@Embedded("sVideo")
    public List <SVideo> sVideos;
	
	// FINDERS ----------

    public static final Finder<SGroup> find = new Finder<SGroup>(SGroup.class);
	
    // Constructors
	public SGroup() {
		this.sUsers = new ArrayList<SUser>();
		this.sPostits = new ArrayList<SPostit>();
		this.sImages = new ArrayList<SImage>();
		this.sVideos = new ArrayList<SVideo>();
	}
	
	public SGroup(String name, int runId) {
		this.name = name;
		this.runId = runId;
		this.sUsers = new ArrayList<SUser>();
		this.sPostits = new ArrayList<SPostit>();
		this.sImages = new ArrayList<SImage>();
		this.sVideos = new ArrayList<SVideo>();
	}
	
	@Override
    public String toString() {
        return Objects.toStringHelper(this).
        		add("_id", id)
        		.add("name", name)
                .add("sUsers:", sUsers)
                .add("sPostits:", sPostits)
                .add("sImages:", sImages)
                .add("sVideos:", sVideos)
                .toString();
    }

}
