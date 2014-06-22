package xorrr.github.io.rest.spark;

import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import spark.Spark;
import xorrr.github.io.rest.RestHelperFacade;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Spark.class)
public class SparkHelperFacadeTest {

    private RestHelperFacade facade;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Spark.class);
        
        facade = new SparkHelperFacade();
    }

    @Test
    public void canStopRequest() {
        facade.stopRequest(404, "message");
        verifyStatic();
        Spark.halt(404, "message");
    }

    @Test
    public void canSetPort() {
        facade.setPort(1111);
        verifyStatic();
        Spark.setPort(1111);
    }

}
