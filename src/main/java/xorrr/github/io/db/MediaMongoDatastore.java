package xorrr.github.io.db;

import java.net.UnknownHostException;
import java.util.ArrayList;

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
        col.insert(new BasicDBObject(MediaCol.URL, m.getUrl()).append(
                MediaCol.RANGES, new ArrayList<DBObject>()));

        logger.info("Stored new media object with url: " + m.getUrl());
    }

    @Override
    public void addRangeToMedia(String id, Range r) {
        DBObject rangeDbo = new BasicDBObject(MediaCol.RANGES,
                new BasicDBObject(MediaCol.START_TIME, r.getStartTime())
                        .append(MediaCol.END_TIME, r.getEndTime()));

        DBObject rangeToPush = new BasicDBObject("$push", rangeDbo);

        col.update(new BasicDBObject(MediaCol.ID, new ObjectId(id)),
                rangeToPush);

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

        logger.info("Media with id: " + id + " was requested");

        return m;
    }

}