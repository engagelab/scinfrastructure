package models;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Reference;
import com.google.common.base.Objects;

/**
 * @author Muhammad Fahied
 */

@Entity(value = "groups", noClassnameStored = true, concern = "NORMAL")
public class SGroup extends Model {

	@Property("name")
	public String name;
	
	@Property("runId")
	public int runId;
    
	@Reference()
    public List <SUser> susers;
	
	@Embedded()
    public List <SPostit> spostits;
	
	@Embedded()
    public List <SImage> simages;
	
	@Embedded()
    public List <SVideo> svideos;
	
	// FINDERS ----------

    public static final Finder<SGroup> find = new Finder<SGroup>(SGroup.class);
	
    // Constructors
	public SGroup() {
	}
	
	public SGroup(String name, int runId) {
		super();
		this.name = name;
		this.runId = runId;
		this.susers = new ArrayList<SUser>();
		this.spostits = new ArrayList<SPostit>();
		this.simages = new ArrayList<SImage>();
		this.svideos = new ArrayList<SVideo>();
	}
	
	
	
	
	
	//Queries
	
	
	
	public void addMember(SUser user) {
		this.susers.add(user);
	}
	
	
	public void addPostit(SPostit postit) {
		this.spostits.add(postit);
	}
	
	public void addImage(SImage image) {
		this.simages.add(image);
	}
	
	public void addVideo(SVideo video) {
		this.svideos.add(video);
	}
	
	
	
	
	
	public void removeMember(SUser user) {
		this.susers.remove(user);
	}
	
	
	public void removePostit(SPostit postit) {
		this.spostits.remove(postit);
	}
	
	public void removeImage(SImage image) {
		this.simages.remove(image);
	}
	
	public void removeVideo(SVideo video) {
		this.svideos.remove(video);
	}
	
	

	
	
	
	@Override
    public String toString() {
        return Objects.toStringHelper(this).
        		add("_id", id.toString())
        		.add("name", name)
                .add("susers:", susers)
                .add("spostits:", spostits)
                .add("simages:", simages)
                .add("svideos:", svideos)
                .toString();
    }



}
