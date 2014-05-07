package xorrr.github.io.db;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.utils.EmbeddedMongoProperties;
import xorrr.github.io.utils.MediaCol;
import xorrr.github.io.utils.RangerDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MediaMongoDatastore implements MediaDatastore {

    private DBCollection col;

    @SuppressWarnings("unchecked")
    private List<DBObject> findRanges(String id) {
        return (List<DBObject>) col.findOne(
                new BasicDBObject(MediaCol.ID, new ObjectId(id))).get(
                MediaCol.RANGES);
    }

    private Range createAverageRange(List<DBObject> ranges, int avgStart,
            int avgEnd) {
        return new Range(avgStart / ranges.size(), avgEnd / ranges.size());
    }

    private Integer currentStartTime(DBObject range) {
        return Integer.valueOf(range.get(MediaCol.START_TIME).toString());
    }

    private Integer currentEndTime(DBObject range) {
        return Integer.valueOf(range.get(MediaCol.END_TIME).toString());
    }

    @SuppressWarnings("unchecked")
    private void addRangesToMedia(DBObject dbo, Media m) {
        List<DBObject> ranges = (List<DBObject>) dbo.get(MediaCol.RANGES);

        for (DBObject range : ranges) {
            Range r = new Range((int) range.get(MediaCol.START_TIME),
                    (int) range.get(MediaCol.END_TIME));
            m.addRange(r);
        }
    }

    public MediaMongoDatastore() throws UnknownHostException {
        MongoClient client = new MongoClient("localhost",
                EmbeddedMongoProperties.PORT);
        col = client.getDB(RangerDB.NAME).getCollection(RangerDB.MEDIA_COL);
    }

    @Override
    public void storeMedia(Media m) {
        col.insert(new BasicDBObject(MediaCol.URL, m.getUrl()).append(
                MediaCol.RANGES, new ArrayList<DBObject>()));
    }

    @Override
    public void addRangeToMedia(String id, Range r) {
        DBObject rangeDbo = new BasicDBObject(MediaCol.RANGES,
                new BasicDBObject(MediaCol.START_TIME, r.getStartTime())
                        .append(MediaCol.END_TIME, r.getEndTime()));

        DBObject rangeToPush = new BasicDBObject("$push", rangeDbo);

        col.update(new BasicDBObject(MediaCol.ID, new ObjectId(id)),
                rangeToPush);
    }

    @Override
    public Range getAverageRange(String id) {
        List<DBObject> ranges = findRanges(id);

        int avgStart = 0, avgEnd = 0;
        for (DBObject range : ranges) {
            avgStart += currentStartTime(range);
            avgEnd += currentEndTime(range);
        }

        return createAverageRange(ranges, avgStart, avgEnd);
    }

    @Override
    public Media getMediaById(String id) {
        DBObject dbo = col.findOne(new BasicDBObject(MediaCol.ID, new ObjectId(
                id)));

        Media m = new Media(dbo.get(MediaCol.URL).toString());
        m.setObjectId(dbo.get(MediaCol.ID).toString());

        addRangesToMedia(dbo, m);

        return m;
    }

}