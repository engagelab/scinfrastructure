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

@Entity(value = "projects", noClassnameStored = true, concern = "NORMAL")
public class SProject extends Model {

	@Property("title")
	public String title;
	
	
	@Embedded()
    public List <SAct> sActs;
	
	@Embedded()
    public List <SScene> sScenes;
	
	@Embedded()
    public List <STask> sTasks;
	
	// FINDERS ----------

    public static final Finder<SProject> find = new Finder<SProject>(SProject.class);
	
    // Constructors
	public SProject() {
	}
	
	
	
	
	
	
	public SProject(String name, int runId) {
		super();
		this.title = name;
		this.sActs = new ArrayList<SAct>();
		this.sScenes = new ArrayList<SScene>();
		this.sTasks = new ArrayList<STask>();
	}
	
	
	
	public void addAct(SAct act) {
		this.sActs.add(act);
	}
	
	public void addScene(SScene scene) {
		this.sScenes.add(scene);
	}
	
	public void addTask(STask task) {
		this.sTasks.add(task);
	}
	
	
	
	
	
	public void removeAct(SAct act) {
		this.sActs.remove(act);
	}
	
	public void removeScene(SScene scene) {
		this.sScenes.remove(scene);
	}
	
	public void removeTask(STask task) {
		this.sTasks.remove(task);
	}
	
	
	
	
	
	@Override
    public String toString() {
        return Objects.toStringHelper(this).
        		add("id", id.toString())
        		.add("title", title)
                .add("sActs:", sActs)
                .add("sScenes:", sScenes)
                .add("sTasks:", sTasks)
                .toString();
    }
	
	
}
