package xorrr.github.io.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.UnknownHostException;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.utils.EmbeddedMongo;
import xorrr.github.io.utils.EnvVars;
import xorrr.github.io.utils.IntegrationTest;
import xorrr.github.io.utils.MediaCol;
import xorrr.github.io.utils.RangerDB;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;

@Category(IntegrationTest.class)
public class TestMediaMongoDatastore {

    private static MongodExecutable mongoExe;

    private DBCollection mediaCol;
    private MediaDatastore ds;

    private String storeSampleMedia() {
        Media m = new Media("www.foobar.org");
        String id = ds.storeMedia(m);
        return id;
    }

    private String getStoredSampleMediaId() {
        DBObject dbo = mediaCol.findOne();
        String id = dbo.get(MediaCol.ID).toString();
        return id;
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
        mediaCol = client.getDB(RangerDB.NAME)
                .getCollection(RangerDB.MEDIA_COL);

        ds = new MediaMongoDatastore();
    }

    @Test
    public void canStoreMedia() {
        storeSampleMedia();

        assertEquals("www.foobar.org", mediaCol.findOne().get(MediaCol.URL)
                .toString());
    }

    @Test
    public void canGetMediaById() {
        storeSampleMedia();

        String id = getStoredSampleMediaId();

        Media m = ds.getMediaById(id);
        assertEquals("www.foobar.org", m.getUrl());
    }

    @Test
    public void wrongMediaIdReturnsNull() {
        Media m = ds.getMediaById(new ObjectId().toString());

        assertNull(m);
    }

    @Test
    public void nonObjectIdStyleMediaIdReturnsNull() {
        Media m = ds.getMediaById("asdf");

        assertNull(m);
    }

    @Test
    public void canApplyMultipleRangesToMediaAfterAnother() {
        storeSampleMedia();
        String mediaId = getStoredSampleMediaId();
        Range r = new Range(20, 40);
        Range r2 = new Range(40, 60);

        ds.applyRangeToMedia(mediaId, r);
        ds.applyRangeToMedia(mediaId, r2);

        Media m = ds.getMediaById(mediaId);
        assertEquals(30, m.getAvgStartTime(), 1);
        assertEquals(50, m.getAvgEndTime(), 1);
        assertEquals("Two users have provided ranges", 2, m.getChoicesByUsers());
    }

    @Test
    public void canDealWithRangesCreatingFloats() {
        storeSampleMedia();
        String mediaId = getStoredSampleMediaId();
        Range r = new Range(20, 40);
        Range r2 = new Range(25, 45);

        ds.applyRangeToMedia(mediaId, r);
        ds.applyRangeToMedia(mediaId, r2);

        Media m = ds.getMediaById(mediaId);
        assertEquals(22.5, m.getAvgStartTime(), 0.2);
        assertEquals(42.5, m.getAvgEndTime(), 0.2);
    }

    @Test
    public void storeSampleMediaReturnsId() {
        String id = storeSampleMedia();

        String mediaId = getStoredSampleMediaId();

        assertEquals(id, mediaId);
    }

    @After
    public void tearDown() {
        mediaCol.drop();
    }

    @AfterClass
    public static void stopEmbeddedMongo() {
        mongoExe.stop();
    }
}
