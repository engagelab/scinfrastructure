package utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bson.types.ObjectId;


import com.google.code.morphia.Datastore;
import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;

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

	private static String host = "intermedia-prod03.uio.no";
    private static String db = "scinfrastructure";
    
    private static Mongo _mongo = null;
    private static Mongo getMongo()
        throws Exception {
        if ( _mongo == null )
            _mongo = new Mongo( host );
        return _mongo;
    }
    
    private static GridFS _gridfs;
    private static GridFS getGridFS()
        throws Exception {
        if ( _gridfs == null )
            _gridfs = new GridFS( getMongo().getDB( db ),"upload" );
        return _gridfs;
    }
	
	
	
	public static GridFSDBFile getFile(String id) throws Exception {
		
		GridFSDBFile file =  getGridFS().findOne(new ObjectId(id));
		return file;
	}
	
	
	
	
	
	
	public static void deleteFile(String id) throws Exception {
		
		getGridFS().remove(new ObjectId(id));
	
	}
	
	
	
	
	
	public static List<GridFSDBFile> getFiles() throws Exception {
		return getGridFS().find(new BasicDBObject());
	}

	
	
	
	
	
	
	public static String  storeFile(File image, String fileName, String contentType) throws Exception {
		GridFS fs = getGridFS();
		//fs.remove(fileName); // delete the old file
		GridFSInputFile gridFile = fs.createFile(image);
		//gridFile.save();
		gridFile.setContentType(contentType);
		gridFile.setFilename(fileName);
		gridFile.save();
		String id = gridFile.getId().toString();
		return id;
	}
	
	
	
	
	
	
	
	   
	

}
