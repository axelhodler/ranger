package xorrr.github.io.db.mongo;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.types.ObjectId;

import xorrr.github.io.db.RangeDatastore;
import xorrr.github.io.model.Range;
import xorrr.github.io.utils.EnvVars;
import xorrr.github.io.utils.model.RangeCol;
import xorrr.github.io.utils.model.RangerDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class RangeMongoDatastore implements RangeDatastore {

    private DBCollection col;

    public RangeMongoDatastore() throws UnknownHostException {
        MongoClient client = new MongoClient("localhost", EnvVars.MONGO_PORT);
        col = client.getDB(RangerDB.NAME).getCollection(RangerDB.RANGE_COL);
    }

    @Override
    public void setRange(Range r, String mediaId, String userId) {
        if (rangeNotSet(mediaId, userId)) {
            storeRange(r, mediaId, userId);
        } else {
            modifyRange(r, mediaId, userId);
        }
    }

    @Override
    public Range getAverages(String mediaId) {
        List<DBObject> ranges = col.find(
                new BasicDBObject(RangeCol.RANGES + "." + RangeCol.MEDIA_ID,
                        mediaId)).toArray();
        double startTime = 0;
        double endTime = 0;

        for (DBObject dbo : ranges) {
            DBObject range = (DBObject) dbo.get(RangeCol.RANGES);
            startTime += Integer.valueOf(range.get(RangeCol.START_TIME)
                    .toString());
            endTime += Integer.valueOf(range.get(RangeCol.END_TIME).toString());
        }

        int avgStartTime = (int) Math.round(startTime / ranges.size());
        int avgEndTime = (int) Math.round(endTime / ranges.size());

        Range r = new Range(avgStartTime, avgEndTime);

        return r;
    }

    @Override
    public Range getRangeFor(String mediaId, String userId) {
        DBObject dbo = col.findOne(rangeQuery(mediaId, userId));
        DBObject ranges = (DBObject) dbo.get(RangeCol.RANGES);
        Range r = new Range((int) ranges.get(RangeCol.START_TIME),
                (int) ranges.get(RangeCol.END_TIME));
        return r;
    }

    private DBCursor findRange(String mediaId, String userId) {
        return col.find(rangeQuery(mediaId, userId)).limit(1);
    }

    private BasicDBObject rangeQuery(String mediaId, String userId) {
        return new BasicDBObject(RangeCol.RANGES + "." + RangeCol.MEDIA_ID,
                mediaId).append(RangeCol.RANGES + "." + RangeCol.USER_ID,
                userId);
    }

    private void modifyRange(Range r, String mediaId, String userId) {
        col.update(
                rangeQuery(mediaId, userId),
                new BasicDBObject("$set", new BasicDBObject(RangeCol.RANGES
                        + "." + RangeCol.START_TIME, r.getStartTime()).append(
                        RangeCol.RANGES + "." + RangeCol.END_TIME,
                        r.getEndTime())));
    }

    private void storeRange(Range r, String mediaId, String userId) {
        col.insert(new BasicDBObject("_id", new ObjectId().toString()).append(
                RangeCol.RANGES,
                new BasicDBObject(RangeCol.USER_ID, userId)
                        .append(RangeCol.MEDIA_ID, mediaId)
                        .append(RangeCol.START_TIME, r.getStartTime())
                        .append(RangeCol.END_TIME, r.getEndTime())));
    }

    private boolean rangeNotSet(String mediaId, String userId) {
        return findRange(mediaId, userId).size() < 1;
    }
}
