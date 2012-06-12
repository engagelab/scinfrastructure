package utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;

import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

/**
 * @author Muhammad Fahied
 */

public class GridFsHelper {
	
	@Inject
    public static Datastore datastore; // requestStaticInjection(..)

	public static GridFSDBFile getFile(String id) throws MongoException, IOException {
		GridFSDBFile file = getGridFS().findOne(new ObjectId(id));
		return file;
	}
	
	public static List<GridFSDBFile> getFiles() throws MongoException, IOException {
		return getGridFS().find(new BasicDBObject());
	}

	public static String  storeFile(File image) throws IOException {
		GridFS fs = getGridFS();
		fs.remove(image.getName()); // delete the old file
		GridFSInputFile gridFile = fs.createFile(image);
		gridFile.save();
		gridFile.setContentType("image/" + FilenameUtils.getExtension(image.getName()));
		gridFile.setFilename(image.getName());
		gridFile.save();
		String id = gridFile.getId().toString();
		return id;
	}

	private static GridFS getGridFS( ) throws IOException, MongoException {
		//String host = "127.0.0.1";
		//Mongo mongo = new Mongo(host , 27017);
		//DB db = mongo.getDB("scinfra");
		DB db = datastore.getDB();
		// GridfsCollectionName = upload; define whater you like
		GridFS fs = new GridFS(db, "upload");
		return fs;
	}
	

}
