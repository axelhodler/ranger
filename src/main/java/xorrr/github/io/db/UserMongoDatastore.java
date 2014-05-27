package xorrr.github.io.db;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.types.ObjectId;

import xorrr.github.io.model.Range;
import xorrr.github.io.model.User;
import xorrr.github.io.utils.RangeConst;
import xorrr.github.io.utils.RangerDB;
import xorrr.github.io.utils.UserCol;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class UserMongoDatastore implements UserDatastore {

    private DBCollection col;

    public UserMongoDatastore() throws UnknownHostException {
        MongoClient client = new MongoClient("localhost", 12345);
        col = client.getDB(RangerDB.NAME).getCollection(RangerDB.USER_COL);
    }

    @Override
    public void storeUser(User u) {
        col.insert(new BasicDBObject(UserCol.LOGIN, u.getLogin()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getUserById(String id) {
        DBObject dbo = col.findOne(new BasicDBObject(UserCol.ID, new ObjectId(
                id)));

        User user = new User();
        user.setLogin(dbo.get(UserCol.LOGIN).toString());

        List<DBObject> ranges = (List<DBObject>) dbo.get(UserCol.RANGES);

        if (ranges != null)
            for (DBObject range : ranges) {
                user.addRange(range.get("mediaId").toString(),
                        new Range((int) range.get(RangeConst.START_TIME),
                                (int) range.get(RangeConst.END_TIME)));
            }

        return user;
    }

    @Override
    public void deleteUserById(String id) {
        col.remove(new BasicDBObject(UserCol.ID, new ObjectId(id)));
    }

    @Override
    public boolean setRange(String userId, String mediaId, Range r) {
        DBObject range = new BasicDBObject("mediaId", mediaId).append(
                RangeConst.START_TIME, r.getStartTime()).append(
                RangeConst.END_TIME, r.getEndTime());
        DBObject ranges = new BasicDBObject(UserCol.RANGES, range);
        DBObject pushRange = new BasicDBObject("$push", ranges);
        col.update(new BasicDBObject(UserCol.ID, new ObjectId(
                userId)), pushRange);

        return false;
    }
}
