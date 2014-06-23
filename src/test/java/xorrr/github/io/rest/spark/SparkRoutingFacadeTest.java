package xorrr.github.io.rest.spark;

import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import spark.Spark;
import xorrr.github.io.rest.routes.MappedRoutes;
import xorrr.github.io.rest.routes.media.GETmediaByIdRoute;
import xorrr.github.io.rest.routes.media.GETmediaRoute;
import xorrr.github.io.rest.routes.media.POSTmediaRoute;
import xorrr.github.io.rest.routes.media.PUTmediaRoute;
import xorrr.github.io.rest.routes.range.GETrangeRoute;
import xorrr.github.io.rest.routes.range.POSTrangeRoute;
import xorrr.github.io.rest.routes.range.PutRangeRoute;
import xorrr.github.io.rest.routes.user.POSTuserRoute;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Spark.class })
public class SparkRoutingFacadeTest {

    @Mock
    GETmediaByIdRoute getMediaById;
    @Mock
    POSTmediaRoute postMedia;
    @Mock
    PUTmediaRoute putRangeToMedia;
    @Mock
    POSTuserRoute postUser;
    @Mock
    GETmediaRoute getMedia;
    @Mock
    GETrangeRoute getRange;
    @Mock
    POSTrangeRoute postRange;
    @Mock
    PutRangeRoute putRange;

    private SparkRoutingFacade facade;
    private final String DEFAULT_ACCEPT_TYPE = "application/json";

    @Before
    public void setUp() {
        facade = new SparkRoutingFacade();

        PowerMockito.mockStatic(Spark.class);
    }

    @Test
    public void canSetGetMediaByIdRoute() {
        facade.setGetMediaByIdRoute(getMediaById);
        verifyStatic();
        Spark.get(MappedRoutes.MEDIA_BY_ID, DEFAULT_ACCEPT_TYPE, getMediaById);
    }

    @Test
    public void canSetPostMediaRoute() {
        facade.setPostMediaRoute(postMedia);
        verifyStatic();
        Spark.post(MappedRoutes.MEDIA, DEFAULT_ACCEPT_TYPE, postMedia);
    }

    @Test
    public void canSetPutRangeToMediaRoute() {
        facade.setPutRangeToMediaRoute(putRangeToMedia);
        verifyStatic();
        Spark.put(MappedRoutes.MEDIA_BY_ID, DEFAULT_ACCEPT_TYPE,
                putRangeToMedia);
    }

    @Test
    public void canSetPostUserRoute() {
        facade.setPostUserRoute(postUser);
        verifyStatic();
        Spark.post(MappedRoutes.USERS, DEFAULT_ACCEPT_TYPE, postUser);
    }

    @Test
    public void canSetGetMediaRoute() {
        facade.setGetMediaRoute(getMedia);
        verifyStatic();
        Spark.get(MappedRoutes.MEDIA, DEFAULT_ACCEPT_TYPE, getMedia);
    }

    @Test
    public void canSetGetRangeRoute() {
        facade.setGetRangeRoute(getRange);
        verifyStatic();
        Spark.get(MappedRoutes.RANGE_FOR_MEDIAID, DEFAULT_ACCEPT_TYPE, getRange);
    }

    @Test
    public void canSetPostRangeRoute() {
        facade.setPostRangeRoute(postRange);
        verifyStatic();
        Spark.post(MappedRoutes.RANGE_FOR_MEDIAID, DEFAULT_ACCEPT_TYPE,
                postRange);
    }

    @Test
    public void canSetPutRangeRoute() {
        facade.setPutRangeRoute(putRange);
        verifyStatic();
        Spark.put(MappedRoutes.RANGE_FOR_MEDIAID, DEFAULT_ACCEPT_TYPE, putRange);
    }
}
