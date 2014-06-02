package xorrr.github.io.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

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
public class MediaMongoDatastoreTest {

    private static MongodExecutable mongoExe;

    private DBCollection mediaCol;
    private MediaDatastore ds;
    private final String URL = "www.foo.org";

    private String storeSampleMedia(String url) {
        Media m = new Media(url);
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
        storeSampleMedia(URL);

        assertEquals(URL, mediaCol.findOne().get(MediaCol.URL)
                .toString());
    }

    @Test
    public void canGetMediaById() {
        storeSampleMedia(URL);

        String id = getStoredSampleMediaId();

        Media m = ds.getMediaById(id);
        assertEquals(URL, m.getUrl());
    }

    @Test
    public void wrongMediaIdReturnsNull() {
        Media m = ds.getMediaById(new ObjectId().toString());

        assertNull(m);
    }

    @Test
    public void invalidMediaIdReturnsFalse() {
        assertFalse(ds.applyRangeToMedia("asdf", null));
    }

    @Test
    public void nonExistentMediaIdReturnsFalse() {
        assertFalse(ds.applyRangeToMedia(new ObjectId().toString(), null));
    }

    @Test
    public void nonObjectIdStyleMediaIdReturnsNull() {
        Media m = ds.getMediaById("asdf");

        assertNull(m);
    }

    @Test
    public void canApplyMultipleRangesToMediaAfterAnother() {
        storeSampleMedia(URL);
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
        storeSampleMedia(URL);
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
        String id = storeSampleMedia(URL);

        String mediaId = getStoredSampleMediaId();

        assertEquals(id, mediaId);
    }

    @Test
    public void canGetAllMedias() {
        storeSampleMedia(URL);
        storeSampleMedia("www.bar.org");

        List<Media> medias = ds.getMedia();

        assertEquals(URL, medias.get(0).getUrl());
        assertEquals("www.bar.org", medias.get(1).getUrl());
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
