package xorrr.github.io.db;

import java.net.UnknownHostException;

import xorrr.github.io.model.Range;
import xorrr.github.io.utils.EnvVars;
import xorrr.github.io.utils.RangeCol;
import xorrr.github.io.utils.RangerDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class RangeMongoDatastore implements RangeDatastore {

    private DBCollection col;

    public RangeMongoDatastore() throws UnknownHostException {
        MongoClient client = new MongoClient("localhost", EnvVars.MONGO_PORT);
        col = client.getDB(RangerDB.NAME).getCollection(RangerDB.RANGE_COL);
    }

    @Override
    public void addRange(Range r, String mediaId, String userId) {
        col.insert(new BasicDBObject(RangeCol.MEDIA_ID, mediaId));
    }

    @Override
    public Range getAverages(String mediaId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Range getRangeFor(String mediaId, String userId) {
        // TODO Auto-generated method stub
        return null;
    }

}
