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

import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.model.User;
import xorrr.github.io.utils.EmbeddedMongo;
import xorrr.github.io.utils.EnvVars;
import xorrr.github.io.utils.IntegrationTest;
import xorrr.github.io.utils.RangeCol;
import xorrr.github.io.utils.RangerDB;

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

        m = new Media("http://www.foo.org");
        u = new User();
        u.setLogin("pete");
    }

    @Test
    public void canAddRange() {
        String mediaId = mediaDs.storeMedia(m);
        String userId = userDs.storeUser(u);

        Range r = new Range(1, 2);
        rangeDs.addRange(r, mediaId, userId);

        DBObject range = rangeCol.findOne();
        assertEquals(mediaId, range.get(RangeCol.MEDIA_ID));
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
