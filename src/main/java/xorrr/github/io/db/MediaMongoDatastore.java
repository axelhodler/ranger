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
        col.insert(new BasicDBObject(MediaCol.URL, m.getUrl())
                .append(MediaCol.CHOICES_BY_USERS, 0)
                .append(MediaCol.AVG_START_TIME, 0)
                .append(MediaCol.AVG_END_TIME, 0));

        logger.info("Stored new media object with url: " + m.getUrl());
    }

    @Override
    public void addRangeToMedia(String id, Range r) {
        DBObject mediaToChange = col.findOne(new BasicDBObject(MediaCol.ID,
                new ObjectId(id)));

        mediaToChange.put(MediaCol.AVG_START_TIME, r.getStartTime());
        mediaToChange.put(MediaCol.AVG_END_TIME, r.getEndTime());
        mediaToChange.put(MediaCol.CHOICES_BY_USERS, 1);

        col.update(new BasicDBObject(MediaCol.ID, new ObjectId(id)),
                mediaToChange);

        logger.info("Added range with startTime: " + r.getStartTime()
                + " and endTime: " + r.getEndTime() + " to media with id: "
                + id);
    }

    @Override
    public Media getMediaById(String id) {
        DBObject dbo = col.findOne(new BasicDBObject(MediaCol.ID, new ObjectId(
                id)));

        Media m = new Media(dbo.get(MediaCol.URL).toString());
        m.setObjectId(dbo.get(MediaCol.ID).toString());
        m.setAvgStartTime(Integer.valueOf(dbo.get(MediaCol.AVG_START_TIME)
                .toString()));
        m.setAvgEndTime(Integer.valueOf(dbo.get(MediaCol.AVG_END_TIME)
                .toString()));
        m.setChoicesByUsers(1);

        logger.info("Media with id: " + id + " was requested");

        return m;
    }

}