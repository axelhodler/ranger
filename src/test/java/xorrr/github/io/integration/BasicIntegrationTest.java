package xorrr.github.io.integration;

import static org.junit.Assert.assertEquals;

import java.net.UnknownHostException;

import org.junit.BeforeClass;
import org.junit.Test;

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

import com.jayway.restassured.RestAssured;

public class BasicIntegrationTest {

    @BeforeClass
    public static void setUpBefore() throws UnknownHostException {
        UserDatastore uds = new UserMongoDatastore();
        MediaDatastore mds = new MediaMongoDatastore();
        DatastoreFacade facade = new DatastoreFacade(uds, mds);
        Transformator transformator = new Transformator();

        SparkFacade rest = new SparkFacade();
        rest.setPort(1337);
        rest.setPostMediaRoute(new PostMediaRoute(facade, transformator));
        rest.setGetMediaByIdRoute(new GetMediaByIdRoute(facade, transformator));
        rest.setPutRangeToMediaRoute(new PutOnMediaRoute(facade,
                transformator));
        RestAssured.port = 1337;
    }

    @Test
    public void nonExistingRoutesDisplay404Page() {
        assertEquals("404", RestAssured.when().get("/asdf").body().asString());
        assertEquals("404", RestAssured.when().get("/foobar").body().asString());
    }
}
