package xorrr.github.io.db.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xorrr.github.io.db.MediaDatastore;
import xorrr.github.io.model.Media;
import xorrr.github.io.utils.EnvVars;
import xorrr.github.io.utils.model.MediaCol;
import xorrr.github.io.utils.model.RangerDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MediaMongoDatastore implements MediaDatastore {

    private final Logger logger = LoggerFactory.getLogger(MediaMongoDatastore.class);
    private DBCollection col;

    public MediaMongoDatastore() throws UnknownHostException {
        MongoClient client = new MongoClient("localhost", EnvVars.MONGO_PORT);
        col = client.getDB(RangerDB.NAME).getCollection(RangerDB.MEDIA_COL);
    }

    @Override
    public String storeMedia(Media m) {
        ObjectId id = new ObjectId();

        col.insert(createBasicDboFromMedia(id, m));

        logger.info("Stored new media object with url: {}", m.getUrl());

        return id.toString();
    }

    @Override
    public Media getMediaById(String id) {
        Media m = null;
        DBObject dbo = null;
        if (ObjectId.isValid(id)){
            dbo = findMedia(id);
        }

        if (idExists(dbo)) {
            m = toMedia(dbo);
            logger.info("Media with id: {} was FOUND", id);
        } else
            logger.info("Media with id: {} was NOT FOUND", id);

        return m;
    }

    @Override
    public List<Media> getMedia() {
        List<DBObject> mediaDbos = col.find().toArray();
        List<Media> medias = new ArrayList<>();
        for (DBObject dbo : mediaDbos) {
            Media m = toMedia(dbo);
            medias.add(m);
        }
        return medias;
    }

    @Override
    public boolean urlStored(String url) {
        DBCursor cur = col.find(new BasicDBObject(MediaCol.URL, url)).limit(1);
        boolean exists = false;

        if (cur.count() > 0) {
            exists = true;
        }
        return exists;
    }

    private boolean mediaFound(DBObject mediaToChange) {
        return mediaToChange != null;
    }

    private boolean idExists(DBObject dbo) {
        return mediaFound(dbo);
    }

    private DBObject findMedia(String id) {
        return col.findOne(queryDbForMediaId(id));
    }

    private Media toMedia(DBObject dbo) {
        Media m = new Media(dbo.get(MediaCol.URL).toString());
        m.setObjectId(dbo.get(MediaCol.ID).toString());
        return m;
    }

    private BasicDBObject createBasicDboFromMedia(ObjectId id, Media m) {
        return new BasicDBObject(MediaCol.ID, id)
                .append(MediaCol.URL, m.getUrl());
    }

    private BasicDBObject queryDbForMediaId(String id) {
        return new BasicDBObject(MediaCol.ID, new ObjectId(id));
    }
}