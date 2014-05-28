package xorrr.github.io.db;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.types.ObjectId;

import xorrr.github.io.model.Range;
import xorrr.github.io.model.User;
import xorrr.github.io.utils.EnvVars;
import xorrr.github.io.utils.RangerDB;
import xorrr.github.io.utils.UserCol;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class UserMongoDatastore implements UserDatastore {

    private DBCollection col;

    public UserMongoDatastore() throws UnknownHostException {
        MongoClient client = new MongoClient("localhost", EnvVars.MONGO_PORT);
        col = client.getDB(RangerDB.NAME).getCollection(RangerDB.USER_COL);
    }

    @Override
    public void storeUser(User u) {
        ObjectId id = new ObjectId();
        col.insert(new BasicDBObject(UserCol.ID, id).append(UserCol.LOGIN,
                u.getLogin()));
    }

    @Override
    public User getUserById(String id) {
        DBObject dbo = findUser(id);
        User user = createUser(dbo);
        return user;
    }

    @Override
    public void deleteUserById(String id) {
        col.remove(new BasicDBObject(UserCol.ID, new ObjectId(id)));
    }

    @Override
    public boolean setRange(String userId, String mediaId, Range r) {
        // check if range for mediaId already set
        DBObject rangeX = col.findOne(new BasicDBObject(UserCol.ID,
                new ObjectId(userId)).append(UserCol.RANGES + "."
                + UserCol.MEDIA_ID, mediaId));
        if (rangeX == null) {
            DBObject range = new BasicDBObject(UserCol.MEDIA_ID, mediaId)
                    .append(UserCol.START_TIME, r.getStartTime()).append(
                            UserCol.END_TIME, r.getEndTime());
            DBObject ranges = new BasicDBObject(UserCol.RANGES, range);
            DBObject pushRange = new BasicDBObject("$push", ranges);
            col.update(new BasicDBObject(UserCol.ID, new ObjectId(userId)),
                    pushRange);
        } else {
            col.update(
                    new BasicDBObject(UserCol.ID, new ObjectId(userId)).append(
                            UserCol.RANGES + "." + UserCol.MEDIA_ID, mediaId),
                    new BasicDBObject("$set", new BasicDBObject(UserCol.RANGES
                            + ".$." + UserCol.START_TIME, r.getStartTime())
                            .append(UserCol.RANGES + ".$." + UserCol.END_TIME,
                                    r.getEndTime())));
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private User createUser(DBObject dbo) {
        User user = new User();
        user.setLogin(dbo.get(UserCol.LOGIN).toString());
        List<DBObject> ranges = (List<DBObject>) dbo.get(UserCol.RANGES);

        if (ranges != null)
            addRanges(user, ranges);
        return user;
    }

    private void addRanges(User user, List<DBObject> ranges) {
        for (DBObject range : ranges) {
            user.addRange(
                    range.get(UserCol.MEDIA_ID).toString(),
                    new Range((int) range.get(UserCol.START_TIME), (int) range
                            .get(UserCol.END_TIME)));
        }
    }

    private DBObject findUser(String id) {
        return col.findOne(new BasicDBObject(UserCol.ID, new ObjectId(id)));
    }
}
