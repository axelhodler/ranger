package xorrr.github.io.db.mongo;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import xorrr.github.io.db.UserDatastore;
import xorrr.github.io.model.User;
import xorrr.github.io.utils.EnvVars;
import xorrr.github.io.utils.model.RangerDB;
import xorrr.github.io.utils.model.UserCol;

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
    public String storeUser(User u) {
        ObjectId id = new ObjectId();
        col.insert(new BasicDBObject(UserCol.ID, id).append(UserCol.LOGIN,
                u.getLogin()));
        return id.toString();
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

    @SuppressWarnings("unchecked")
    private User createUser(DBObject dbo) {
        User user = new User();
        user.setLogin(dbo.get(UserCol.LOGIN).toString());

        return user;
    }

    private DBObject findUser(String id) {
        return col.findOne(new BasicDBObject(UserCol.ID, new ObjectId(id)));
    }
}
