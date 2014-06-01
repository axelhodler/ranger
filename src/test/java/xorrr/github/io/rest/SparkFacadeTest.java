package xorrr.github.io.rest;

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
import xorrr.github.io.rest.routes.user.POSTuserRoute;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Spark.class })
public class SparkFacadeTest {

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

    private SparkFacade facade;

    @Before
    public void setUp() {
        facade = new SparkFacade();

        PowerMockito.mockStatic(Spark.class);
    }

    @Test
    public void canSetPort() {
        facade.setPort(1111);
        verifyStatic();
        Spark.setPort(1111);
    }

    @Test
    public void canSetGetMediaByIdRoute() {
        facade.setGetMediaByIdRoute(getMediaById);
        verifyStatic();
        Spark.get(MappedRoutes.MEDIA_BY_ID, getMediaById);
    }

    @Test
    public void canSetPostMediaRoute() {
        facade.setPostMediaRoute(postMedia);
        verifyStatic();
        Spark.post(MappedRoutes.MEDIA, postMedia);
    }

    @Test
    public void canSetPutRangeToMediaRoute() {
        facade.setPutRangeToMediaRoute(putRangeToMedia);
        verifyStatic();
        Spark.put(MappedRoutes.MEDIA_BY_ID, putRangeToMedia);
    }

    @Test
    public void canSetPostUserRoute() {
        facade.setPostUserRoute(postUser);
        verifyStatic();
        Spark.post(MappedRoutes.USERS, postUser);
    }

    @Test
    public void canSetGetMediaRoute() {
        facade.setGetMediaRoute(getMedia);
        verifyStatic();
        Spark.get(MappedRoutes.MEDIA, getMedia);
    }
}
