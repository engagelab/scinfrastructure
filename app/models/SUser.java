package models;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;

/**
 * @author Muhammad Fahied
 */

@Entity(value = "users", noClassnameStored = true, concern = "NORMAL")
public class SUser extends Model {
	
	@Property("name")
	public String name;
	
	@Property("email")
	public String email;
	
	@Property("age")
	public int age;
	
//	@Property("imageUri")
//	public String imageUri;
	
	
    // FINDERS ----------
    public static final Finder<SUser> find = new Finder<SUser>(SUser.class);
    
    public SUser() {  }

    public SUser(String name) {
    	super();
        this.name = name;
    }
    
	public SUser(String name,String email, int age, String imageUri){
		super();
		this.name = name;
		this.email = email;
		this.age = age;
//		this.imageUri = imageUri;
	}
	
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
        		.add("id", id)
                .add("name", name)
                .add("email", email)
                .add("age", age)
//                .add("imageUri", imageUri )
                .toString();
    }
}
