package xorrr.github.io.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xorrr.github.io.RangerDB;
import xorrr.github.io.UserCol;
import xorrr.github.io.model.User;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class TestUserMongoDatastore {

    private MongoClient client;
    private UserDatastore ds;
    private DBCollection userCol;
    private User user;

    private void createTestUser() {
        user = new User();
        user.setName("xorrr");
    }

    private String storeUserAndGetId() {
        createTestUser();

        ds.storeUser(user);

        DBObject dbo = findStoredUser();
        String id = dbo.get(UserCol.ID).toString();
        return id;
    }

    private DBObject findStoredUser() {
        return userCol
                .findOne(new BasicDBObject(UserCol.NAME, "xorrr"));
    }

    @Before
    public void setUp() throws UnknownHostException {
        client = new MongoClient("localhost");
        ds = new UserMongoDatastore();
        userCol = client.getDB(RangerDB.NAME).getCollection(RangerDB.USER_COL);
    }

    @Test
    public void canStoreUser() throws UnknownHostException {
        createTestUser();

        ds.storeUser(user);

        DBObject dbo = findStoredUser();
        assertEquals("xorrr", dbo.get(UserCol.NAME));
    }

    @Test
    public void canGetStoredUser() throws UnknownHostException {
        String id = storeUserAndGetId();

        assertEquals("xorrr", ds.getUserById(id).getName());
    }

    @Test
    public void canDeleteStoredUser() throws Exception {
        String id = storeUserAndGetId();

        ds.deleteUserById(id);

        assertNull(findStoredUser());
    }

    @After
    public void tearDown() {
        userCol.drop();
    }
}
