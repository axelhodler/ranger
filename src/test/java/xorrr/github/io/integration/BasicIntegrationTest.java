package xorrr.github.io.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.db.MediaDatastore;
import xorrr.github.io.db.MediaMongoDatastore;
import xorrr.github.io.db.UserDatastore;
import xorrr.github.io.db.UserMongoDatastore;
import xorrr.github.io.rest.SparkFacade;
import xorrr.github.io.rest.routes.GetMediaByIdRoute;
import xorrr.github.io.rest.routes.PostMediaRoute;
import xorrr.github.io.rest.routes.PutOnMediaRoute;
import xorrr.github.io.rest.transformation.Transformator;
import xorrr.github.io.utils.EmbeddedMongo;
import xorrr.github.io.utils.EnvVars;
import xorrr.github.io.utils.IntegrationTest;
import xorrr.github.io.utils.RangerDB;

import com.jayway.restassured.RestAssured;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;

@Category(IntegrationTest.class)
public class BasicIntegrationTest {

    private static MongodExecutable mongoExe;
    private static DBCollection mediaCol;
    private static DatastoreFacade facade;

    @BeforeClass
    public static void setUpBefore() throws IOException {
        mongoExe = EmbeddedMongo.getEmbeddedMongoExecutable();
        mongoExe.start();

        MongoClient client = new MongoClient("localhost", EnvVars.MONGO_PORT);
        mediaCol = client.getDB(RangerDB.NAME)
                .getCollection(RangerDB.MEDIA_COL);

        UserDatastore uds = new UserMongoDatastore();
        MediaDatastore mds = new MediaMongoDatastore();
        facade = new DatastoreFacade(uds, mds);
        Transformator transformator = new Transformator();

        SparkFacade rest = new SparkFacade();
        rest.setPort(1337);
        rest.setPostMediaRoute(new PostMediaRoute(facade, transformator));
        rest.setGetMediaByIdRoute(new GetMediaByIdRoute(facade, transformator));
        rest.setPutRangeToMediaRoute(new PutOnMediaRoute(facade, transformator));
        rest.setWildcardRoutes();
        RestAssured.port = 1337;
    }

    @Test
    public void nonExistingRoutesDisplay404Page() {
        assertEquals("404", RestAssured.when().get("/asdf").body().asString());
        assertEquals("404", RestAssured.when().get("/foobar").body().asString());
    }

    @Test
    public void nonExistentIdReturns404() {
        assertEquals(404, RestAssured.when().get("/media/12345").getStatusCode());
        assertEquals("404", RestAssured.when().get("/media/12345").body().asString());

        assertEquals("404", RestAssured.when().put("/media/12345").body().asString());
        assertEquals(404, RestAssured.when().put("/media/12345").getStatusCode());
    }

    @After
    public void tearDown() {
        mediaCol.drop();
    }

    @AfterClass
    public static void stopEmbedded() {
        mongoExe.stop();
    }
}
