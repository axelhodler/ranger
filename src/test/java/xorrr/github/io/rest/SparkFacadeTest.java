package xorrr.github.io.rest;

import static org.mockito.Matchers.anyInt;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import spark.Spark;
import xorrr.github.io.rest.routes.GetMediaByIdRoute;
import xorrr.github.io.rest.routes.PostMediaRoute;
import xorrr.github.io.rest.routes.PutRangeToMediaRoute;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Spark.class })
public class SparkFacadeTest {

    @Mock
    GetMediaByIdRoute getMediaById;
    @Mock
    PostMediaRoute postMedia;
    @Mock
    PutRangeToMediaRoute putRangeToMedia;

    private SparkFacade facade;

    @Before
    public void setUp() {
        facade = new SparkFacade();

        PowerMockito.mockStatic(Spark.class);
    }

    @Test
    public void canSetPort() {
        facade.setPort(anyInt());
        PowerMockito.verifyStatic();
        Spark.setPort(anyInt());
    }

    @Test
    public void canSetGetMediaByIdRoute() {
        facade.setGetMediaByIdRoute(getMediaById);
        PowerMockito.verifyStatic();
        Spark.get(getMediaById);
    }

    @Test
    public void canSetPostMediaRoute() {
        facade.setPostMediaRoute(postMedia);
        PowerMockito.verifyStatic();
        Spark.post(postMedia);
    }

    @Test
    public void canSetPutRangeToMediaRoute() {
        facade.setPutRangeToMediaRoute(putRangeToMedia);
        PowerMockito.verifyStatic();
        Spark.put(putRangeToMedia);
    }
}
