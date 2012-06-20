package models;

import static play.libs.Json.toJson;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.query.UpdateOperations;
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

	public void addMember(SUser user) {
		this.susers.add(user);
		//this.save();
		
	}
	

}
