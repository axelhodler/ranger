package xorrr.github.io.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import xorrr.github.io.MediaCol;
import xorrr.github.io.RangerDB;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.utils.IntegrationTest;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;

@Category(IntegrationTest.class)
public class TestMediaMongoDatastore {

    private static MongodExecutable mongoExe;

    private DBCollection mediaCol;
    private MediaDatastore ds;

    @SuppressWarnings("unchecked")
    private List<DBObject> getMediaRanges() {
        return (List<DBObject>) mediaCol.findOne().get(
                MediaCol.RANGES);
    }

    private void storeSampleMedia() {
        Media m = new Media("www.foobar.org");
        ds.storeMedia(m);
    }

    private void addSampleRangesToMedia(String id) {
        Range r = new Range(60, 120);
        Range r2 = new Range(90, 150);
        Range r3 = new Range(61, 121);
        ds.addRangeToMedia(id, r);
        ds.addRangeToMedia(id, r2);
        ds.addRangeToMedia(id, r3);
    }

    @BeforeClass
    public static void setUpEmbeddedMongo() throws UnknownHostException,
            IOException {
        mongoExe = EmbeddedMongo.getEmbeddedMongoExecutable();
        mongoExe.start();
    }

    @Before
    public void setUp() throws UnknownHostException {
        MongoClient client = new MongoClient("localhost",
                EmbeddedMongoProperties.PORT);
        mediaCol = client.getDB(RangerDB.NAME)
                .getCollection(RangerDB.MEDIA_COL);

        ds = new MediaMongoDatastore();
    }

    @Test
    public void canStoreMedia() {
        storeSampleMedia();

        assertEquals("www.foobar.org", mediaCol.findOne().get(MediaCol.URL)
                .toString());
        assertNotNull(mediaCol.findOne().get(MediaCol.RANGES));
    }

    @Test
    public void canAddRanges() {
        storeSampleMedia();

        DBObject dbo = mediaCol.findOne();
        String id = dbo.get(MediaCol.ID).toString();

        addSampleRangesToMedia(id);

        List<DBObject> ranges = getMediaRanges();
        assertEquals(60, ranges.get(0).get(MediaCol.START_TIME));
        assertEquals(120, ranges.get(0).get(MediaCol.END_TIME));
        assertEquals(90, ranges.get(1).get(MediaCol.START_TIME));
        assertEquals(150, ranges.get(1).get(MediaCol.END_TIME));
    }

    @Test
    public void canGetAverageRange() {
        storeSampleMedia();

        DBObject dbo = mediaCol.findOne();
        String id = dbo.get(MediaCol.ID).toString();

        addSampleRangesToMedia(id);

        Range r = ds.getAverageRange(id);

        assertEquals(70, r.getStartTime());
        assertEquals(130, r.getEndTime());
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
