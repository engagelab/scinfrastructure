package models;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;


/**
 * @author Muhammad Fahied
 */

@Entity(value = "projects", noClassnameStored = true, concern = "NORMAL")
public class SProject extends Model {

	@Property("title")
	public String title;
	
	
	@Embedded()
    public List <SAct> sacts;
	
	@Embedded()
    public List <SScene> sscenes;
	
	@Embedded()
    public List <STask> stasks;
	
	// FINDERS ----------

    public static final Finder<SProject> find = new Finder<SProject>(SProject.class);
	
    // Constructors
	public SProject() {
	}
	
	
	
	
	
	
	public SProject(String title) {
		super();
		this.title = title;
		this.sacts = new ArrayList<SAct>();
		this.sscenes = new ArrayList<SScene>();
		this.stasks = new ArrayList<STask>();
	}
	
	
	
	public void addAct(SAct act) {
		this.sacts.add(act);
	}
	
	public void addScene(SScene scene) {
		this.sscenes.add(scene);
	}
	
	public void addTask(STask task) {
		this.stasks.add(task);
	}
	
	
	
	
	
	public void removeAct(SAct act) {
		this.sacts.remove(act);
	}
	
	public void removeScene(SScene scene) {
		this.sscenes.remove(scene);
	}
	
	public void removeTask(STask task) {
		this.stasks.remove(task);
	}
	
	
	
	
	
	@Override
    public String toString() {
        return Objects.toStringHelper(this).
        		add("id", id.toString())
        		.add("title", title)
                .add("sacts:", sacts)
                .add("sscenes:", sscenes)
                .add("staks:", stasks)
                .toString();
    }
	
	
}
