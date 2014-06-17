package xorrr.github.io.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

import xorrr.github.io.db.MediaDatastore;
import xorrr.github.io.db.mongo.MediaMongoDatastore;
import xorrr.github.io.model.Media;
import xorrr.github.io.utils.EmbeddedMongo;
import xorrr.github.io.utils.EnvVars;
import xorrr.github.io.utils.IntegrationTest;
import xorrr.github.io.utils.model.MediaCol;
import xorrr.github.io.utils.model.RangerDB;

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
    public void nonObjectIdStyleMediaIdReturnsNull() {
        Media m = ds.getMediaById("asdf");

        assertNull(m);
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

    @Test
    public void canCheckForUrlDuplicates() {
        storeSampleMedia(URL);

        assertTrue(ds.urlStored(URL));
    }

    @Test
    public void isNotADuplicate() {
        assertFalse(ds.urlStored(URL));
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
