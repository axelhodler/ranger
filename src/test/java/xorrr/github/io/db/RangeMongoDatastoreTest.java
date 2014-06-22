package xorrr.github.io.db;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import xorrr.github.io.db.mongo.MediaMongoDatastore;
import xorrr.github.io.db.mongo.RangeMongoDatastore;
import xorrr.github.io.db.mongo.UserMongoDatastore;
import xorrr.github.io.exceptions.AlreadyStoredException;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.model.User;
import xorrr.github.io.utils.EmbeddedMongo;
import xorrr.github.io.utils.EnvVars;
import xorrr.github.io.utils.IntegrationTest;
import xorrr.github.io.utils.model.RangeCol;
import xorrr.github.io.utils.model.RangerDB;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;

@Category(IntegrationTest.class)
public class RangeMongoDatastoreTest {

    private static MongodExecutable mongoExe;
    private RangeMongoDatastore rangeDs;
    private DBCollection rangeCol;
    private MediaMongoDatastore mediaDs;
    private UserMongoDatastore userDs;
    private Media m;
    private User u;
    private String mediaId;
    private String userId;
    private String userId2;

    private void createInitialMedia() {
        m = new Media("http://www.foo.org");
        mediaId = mediaDs.storeMedia(m);
    }

    private void createInitialUser() {
        u = new User();
        u.setLogin("pete");
        userId = userDs.storeUser(u);
        u.setLogin("mey");
        userId2 = userDs.storeUser(u);
    }

    @BeforeClass
    public static void setUpEmbeddedMongo() throws UnknownHostException,
            IOException {
        mongoExe = EmbeddedMongo.getEmbeddedMongoExecutable();
        mongoExe.start();
    }

    @Before
    public void setUp() throws UnknownHostException {
        MongoClient client = new MongoClient("localhost", EnvVars.MONGO_PORT);
        rangeCol = client.getDB(RangerDB.NAME)
                .getCollection(RangerDB.RANGE_COL);

        rangeDs = new RangeMongoDatastore();
        mediaDs = new MediaMongoDatastore();
        userDs = new UserMongoDatastore();

        createInitialMedia();
        createInitialUser();
    }

    @Test
    public void canSetRange() throws AlreadyStoredException {
        Range r = new Range(1, 2);
        rangeDs.storeRange(r, mediaId, userId);

        DBObject range = rangeCol.findOne();

        assertEquals(mediaId, range.get(RangeCol.MEDIA_ID));
        assertEquals(userId, range.get(RangeCol.USER_ID));
        assertEquals(1, range.get(RangeCol.START_TIME));
        assertEquals(2, range.get(RangeCol.END_TIME));
    }

    @Test(expected = AlreadyStoredException.class)
    public void canOnlyAddOneRangePerUserPerMedia()
            throws AlreadyStoredException {
        Range r = new Range(1, 2);
        rangeDs.storeRange(r, mediaId, userId);
        Range r2 = new Range(3, 4);
        rangeDs.storeRange(r2, mediaId, userId);
    }

    @Test
    public void canModifyRange() throws AlreadyStoredException {
        Range r = new Range(1, 2);
        rangeDs.storeRange(r, mediaId, userId);
        Range r2 = new Range(3, 4);
        rangeDs.modifyRange(r2, mediaId, userId);

        assertEquals(1, rangeCol.find().size());
        DBObject dbo = rangeCol.findOne();
        assertEquals(3, dbo.get(RangeCol.START_TIME));
        assertEquals(4, dbo.get(RangeCol.END_TIME));
    }

    @Test
    public void modifyRangeReturnsId() throws AlreadyStoredException {
        Range r = new Range(1, 2);
        rangeDs.storeRange(r, mediaId, userId);
        Range r2 = new Range(3, 4);
        String actualId = rangeDs.modifyRange(r2, mediaId, userId);

        String expectedId = (String) rangeCol.findOne().get(RangeCol.ID);
        assertEquals(expectedId, actualId);
    }

    @Test
    public void canGetAverageRange() throws AlreadyStoredException {
        Range r = new Range(1, 2);
        rangeDs.storeRange(r, mediaId, userId);
        Range r2 = new Range(3, 4);
        rangeDs.storeRange(r2, mediaId, userId2);

        Range avgRange = rangeDs.getAverages(mediaId);

        assertEquals(2, avgRange.getStartTime());
        assertEquals(3, avgRange.getEndTime());
    }

    @Test
    public void roundingWorksForAverages() throws AlreadyStoredException {
        Range r = new Range(1, 2);
        rangeDs.storeRange(r, mediaId, userId);
        Range r2 = new Range(4, 5);
        rangeDs.storeRange(r2, mediaId, userId2);

        Range avgRange = rangeDs.getAverages(mediaId);

        assertEquals(3, avgRange.getStartTime());
        assertEquals(4, avgRange.getEndTime());
    }

    @Test
    public void canGetRangeForUserAndMedia() throws AlreadyStoredException {
        Range r = new Range(1, 2);
        rangeDs.storeRange(r, mediaId, userId);

        Range range = rangeDs.getRangeFor(mediaId, userId);

        assertEquals(1, range.getStartTime());
        assertEquals(2, range.getEndTime());
    }

    @After
    public void tearDown() {
        rangeCol.drop();
    }

    @AfterClass
    public static void stopEmbeddedMongo() {
        mongoExe.stop();
    }
}
