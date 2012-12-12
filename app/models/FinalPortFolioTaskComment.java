package models;

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.common.base.Objects;

/**
 * @author Jeremy Toussaint
 */

@Embedded
public class FinalPortFolioTaskComment {
	@Indexed
	@Property("id")
	public String id = new ObjectId().toString();

	@Property("date")
	public String postedAt = new Date().toString();

	@Property("text")
	public String text;

	@Property("taskId")
	public String taskId;

	public FinalPortFolioTaskComment() {
		// TODO Auto-generated constructor stub
	}

	public FinalPortFolioTaskComment(String text, String taskId) {
		this.text = text;
		this.taskId = taskId;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id).add("text", text)
				.add("postedAt", postedAt).add("taskId", taskId).toString();
	}

}
