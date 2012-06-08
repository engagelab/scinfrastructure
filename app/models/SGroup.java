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
    public List <SPicture> spictures;
	
	@Embedded()
    public List <SVideo> svideos;
	
	// FINDERS ----------

    public static final Finder<SGroup> find = new Finder<SGroup>(SGroup.class);
	
    // Constructors
	public SGroup() {
	}
	
	public SGroup(String name, int runId) {
		this.name = name;
		this.runId = runId;
		this.susers = new ArrayList<SUser>();
		this.spostits = new ArrayList<SPostit>();
		this.spictures = new ArrayList<SPicture>();
		this.svideos = new ArrayList<SVideo>();
	}
	
	@Override
    public String toString() {
        return Objects.toStringHelper(this).
        		add("_id", id)
        		.add("name", name)
                .add("sUsers:", susers)
                .add("sPostits:", spostits)
                .add("sPictures:", spictures)
                .add("sVideos:", svideos)
                .toString();
    }

	public void addMember(SUser user) {
		
		this.susers.add(user);
		this.save();
		
	}

}
