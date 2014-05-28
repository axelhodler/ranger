package xorrr.github.io.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import spark.Spark;
import xorrr.github.io.rest.routes.GETmediaByIdRoute;
import xorrr.github.io.rest.routes.POSTmediaRoute;
import xorrr.github.io.rest.routes.PUTmediaRoute;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Spark.class })
public class SparkFacadeTest {

    @Mock
    GETmediaByIdRoute getMediaById;
    @Mock
    POSTmediaRoute postMedia;
    @Mock
    PUTmediaRoute putRangeToMedia;

    private SparkFacade facade;

    @Before
    public void setUp() {
        facade = new SparkFacade();

        PowerMockito.mockStatic(Spark.class);
    }

    @Test
    public void canSetPort() {
        facade.setPort(1111);
        PowerMockito.verifyStatic();
        Spark.setPort(1111);
    }

    @Test
    public void canSetGetMediaByIdRoute() {
        facade.setGetMediaByIdRoute(getMediaById);
        PowerMockito.verifyStatic();
        Spark.get(MappedRoutes.MEDIA_BY_ID, getMediaById);
    }

    @Test
    public void canSetPostMediaRoute() {
        facade.setPostMediaRoute(postMedia);
        PowerMockito.verifyStatic();
        Spark.post(MappedRoutes.MEDIA, postMedia);
    }

    @Test
    public void canSetPutRangeToMediaRoute() {
        facade.setPutRangeToMediaRoute(putRangeToMedia);
        PowerMockito.verifyStatic();
        Spark.put(MappedRoutes.MEDIA_BY_ID, putRangeToMedia);
    }
}
