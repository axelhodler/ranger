package xorrr.github.io.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import xorrr.github.io.di.Module;
import xorrr.github.io.rest.RestHelperFacade;
import xorrr.github.io.rest.RestRoutingFacade;
import xorrr.github.io.rest.routes.media.GETmediaByIdRoute;
import xorrr.github.io.rest.routes.media.GETmediaRoute;
import xorrr.github.io.rest.routes.media.POSTmediaRoute;
import xorrr.github.io.rest.routes.media.PUTmediaRoute;
import xorrr.github.io.rest.routes.user.POSTuserRoute;
import xorrr.github.io.utils.EmbeddedMongo;
import xorrr.github.io.utils.EnvVars;
import xorrr.github.io.utils.IntegrationTest;
import xorrr.github.io.utils.model.RangerDB;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jayway.restassured.RestAssured;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;

@Category(IntegrationTest.class)
public class BasicIntegrationTest {

    private static MongodExecutable mongoExe;
    private static DBCollection mediaCol;

    @BeforeClass
    public static void setUpBefore() throws IOException {
        mongoExe = EmbeddedMongo.getEmbeddedMongoExecutable();
        mongoExe.start();

        MongoClient client = new MongoClient("localhost", EnvVars.MONGO_PORT);
        mediaCol = client.getDB(RangerDB.NAME)
                .getCollection(RangerDB.MEDIA_COL);

        Injector injector = Guice.createInjector(new Module());

        RestRoutingFacade rest = injector.getInstance(RestRoutingFacade.class);
        RestHelperFacade helper = injector.getInstance(RestHelperFacade.class);

        helper.setPort(EnvVars.PORT);
        rest.setPostMediaRoute(injector.getInstance(POSTmediaRoute.class));
        rest.setGetMediaByIdRoute(injector.getInstance(GETmediaByIdRoute.class));
        rest.setPutRangeToMediaRoute(injector.getInstance(PUTmediaRoute.class));
        rest.setPostUserRoute(injector.getInstance(POSTuserRoute.class));
        rest.setGetMediaRoute(injector.getInstance(GETmediaRoute.class));
        rest.setWildcardRoutes();

        RestAssured.port = EnvVars.PORT;
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

        assertEquals("Unauthorized", RestAssured.when().put("/media/12345").body().asString());
        assertEquals(401, RestAssured.when().put("/media/12345").getStatusCode());
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
