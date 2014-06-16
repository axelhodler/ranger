package xorrr.github.io.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.UnknownHostException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import xorrr.github.io.model.User;
import xorrr.github.io.utils.EmbeddedMongo;
import xorrr.github.io.utils.IntegrationTest;
import xorrr.github.io.utils.model.RangerDB;
import xorrr.github.io.utils.model.UserCol;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;

@Category(IntegrationTest.class)
public class UserMongoDatastoreTest {

    private static MongodExecutable mongodExe;
    private MongoClient client;
    private UserDatastore ds;
    private DBCollection userCol;
    private User user;
    private String userId;

    private void createTestUser() {
        user = new User();
        user.setLogin("xorrr");
    }

    private String storeUserAndGetId() {
        createTestUser();

        ds.storeUser(user);

        DBObject dbo = findStoredUser();
        String id = dbo.get(UserCol.ID).toString();

        return id;
    }

    private DBObject findStoredUser() {
        return userCol.findOne(new BasicDBObject(UserCol.LOGIN, "xorrr"));
    }

    @BeforeClass
    public static void setUpEmbeddedMongo() throws Exception {
        mongodExe = EmbeddedMongo.getEmbeddedMongoExecutable();
        mongodExe.start();
    }

    @Before
    public void setUp() throws UnknownHostException {
        client = new MongoClient("localhost", 12345);
        ds = new UserMongoDatastore();
        userCol = client.getDB(RangerDB.NAME).getCollection(RangerDB.USER_COL);

        userId = storeUserAndGetId();
    }

    @Test
    public void canStoreUser() throws UnknownHostException {
        createTestUser();

        ds.storeUser(user);

        DBObject dbo = findStoredUser();
        assertEquals("xorrr", dbo.get(UserCol.LOGIN));
    }

    @Test
    public void canGetStoredUser() throws UnknownHostException {
        assertEquals("xorrr", ds.getUserById(userId).getLogin());
    }

    @Test
    public void canDeleteStoredUser() throws Exception {
        ds.deleteUserById(userId);

        assertNull(findStoredUser());
    }

    @After
    public void tearDown() {
        userCol.drop();
    }

    @AfterClass
    public static void stopEmbeddedMongo() {
        mongodExe.stop();
    }
}
