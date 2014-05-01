package xorrr.github.io.db;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import xorrr.github.io.RangerDB;
import xorrr.github.io.UserCol;
import xorrr.github.io.model.User;

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
        col.insert(new BasicDBObject(UserCol.NAME, u.getName()));
    }

    @Override
    public User getUserById(String id) {
        DBObject dbo = col.findOne(new BasicDBObject(UserCol.ID, new ObjectId(
                id)));

        User user = new User();
        user.setName(dbo.get(UserCol.NAME).toString());

        return user;
    }

    @Override
    public void deleteUserById(String id) {
        col.remove(new BasicDBObject(UserCol.ID, new ObjectId(id)));
    }

}
