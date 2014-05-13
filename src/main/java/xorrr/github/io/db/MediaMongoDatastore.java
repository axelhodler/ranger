package xorrr.github.io.db;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.utils.EnvVars;
import xorrr.github.io.utils.MediaCol;
import xorrr.github.io.utils.RangerDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MediaMongoDatastore implements MediaDatastore {

    final Logger logger = LoggerFactory.getLogger(MediaMongoDatastore.class);
    private DBCollection col;

    public MediaMongoDatastore() throws UnknownHostException {
        MongoClient client = new MongoClient("localhost", EnvVars.MONGO_PORT);
        col = client.getDB(RangerDB.NAME).getCollection(RangerDB.MEDIA_COL);
    }

    @Override
    public void storeMedia(Media m) {
        col.insert(createBasicDboFromMedia(m));

        logger.info("Stored new media object with url: " + m.getUrl());
    }

    @Override
    public void applyRangeToMedia(String id, Range r) {
        DBObject mediaToChange = findMediaById(id);

        calculateNewAverages(r, mediaToChange);

        col.update(queryDbForMediaId(id), mediaToChange);

        logger.info("Added range with startTime: " + r.getStartTime()
                + " and endTime: " + r.getEndTime() + " to media with id: "
                + id);
    }

    @Override
    public Media getMediaById(String id) {
        DBObject dbo = findMediaById(id);

        Media m = createMediaFromDbo(dbo);

        logger.info("Media with id: " + id + " was requested");

        return m;
    }

    private DBObject findMediaById(String id) {
        return col.findOne(queryDbForMediaId(id));
    }

    private Media createMediaFromDbo(DBObject dbo) {
        Media m = new Media(dbo.get(MediaCol.URL).toString());
        m.setObjectId(dbo.get(MediaCol.ID).toString());
        m.setAvgStartTime((double) dbo.get(MediaCol.AVG_START_TIME));
        m.setAvgEndTime((double) dbo.get(MediaCol.AVG_END_TIME));
        m.setChoicesByUsers((int) dbo.get(MediaCol.CHOICES_BY_USERS));
        return m;
    }

    private void calculateNewAverages(Range r, DBObject mediaToChange) {
        int divisor = (int) mediaToChange.get(MediaCol.CHOICES_BY_USERS);
        divisor++;
        double currentAvgStartTime = (double) mediaToChange
                .get(MediaCol.AVG_START_TIME);
        double currentAvgEndTime = (double) mediaToChange.get(MediaCol.AVG_END_TIME);

        double nextAvgStartTime = (r.getStartTime() + currentAvgStartTime)
                / divisor;
        double nextAvgEndTime = (r.getEndTime() + currentAvgEndTime) / divisor;

        mediaToChange.put(MediaCol.AVG_START_TIME, nextAvgStartTime);
        mediaToChange.put(MediaCol.AVG_END_TIME, nextAvgEndTime);
        mediaToChange.put(MediaCol.CHOICES_BY_USERS, divisor);
    }

    private BasicDBObject createBasicDboFromMedia(Media m) {
        return new BasicDBObject(MediaCol.URL, m.getUrl())
                .append(MediaCol.CHOICES_BY_USERS, 0)
                .append(MediaCol.AVG_START_TIME, 0.0)
                .append(MediaCol.AVG_END_TIME, 0.0);
    }

    private BasicDBObject queryDbForMediaId(String id) {
        return new BasicDBObject(MediaCol.ID, new ObjectId(id));
    }
}