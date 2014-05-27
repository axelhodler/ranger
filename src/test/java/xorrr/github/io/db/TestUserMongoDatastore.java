package xorrr.github.io.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import xorrr.github.io.model.Range;
import xorrr.github.io.model.User;
import xorrr.github.io.utils.EmbeddedMongo;
import xorrr.github.io.utils.IntegrationTest;
import xorrr.github.io.utils.RangerDB;
import xorrr.github.io.utils.UserCol;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;

@Category(IntegrationTest.class)
public class TestUserMongoDatastore {

    private static MongodExecutable mongodExe;
    private MongoClient client;
    private UserDatastore ds;
    private DBCollection userCol;
    private User user;

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
        return userCol
                .findOne(new BasicDBObject(UserCol.LOGIN, "xorrr"));
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
        String id = storeUserAndGetId();

        assertEquals("xorrr", ds.getUserById(id).getLogin());
    }

    @Test
    public void canDeleteStoredUser() throws Exception {
        String id = storeUserAndGetId();

        ds.deleteUserById(id);

        assertNull(findStoredUser());
    }

    @Test
    public void canSetNewRangeInUser() {
        String userId = storeUserAndGetId();
        String mediaId = new ObjectId().toString();
        Range r = new Range(1, 2);

        ds.setRange(userId, mediaId, r);

        User u = ds.getUserById(userId);
        assertEquals(1, u.getRanges().size());
        assertEquals(1, u.getRanges().get(mediaId).getStartTime());
        assertEquals(2, u.getRanges().get(mediaId).getEndTime());
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
