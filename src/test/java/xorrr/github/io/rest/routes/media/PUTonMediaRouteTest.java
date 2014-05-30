package xorrr.github.io.rest.routes.media;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.transformation.Transformator;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Spark.class })
public class PUTonMediaRouteTest {

    @Mock
    Request req;
    @Mock
    Response resp;
    @Mock
    DatastoreFacade facade;
    @Mock
    Transformator transformator;
    @Mock
    Range range;

    private final String JSON_RANGE = "{\"startTime\":1, \"endTime\":2}";
    private final String ID = "536a6107ccf258bb9041663a";
    private PUTmediaRoute p;
    private Media m;

    private void mockBehaviour() {
        when(req.contentLength()).thenReturn(1);
        when(req.params(MappedRoutesParams.ID)).thenReturn(ID);
        when(req.body()).thenReturn(JSON_RANGE);
        when(facade.getMediaById(ID)).thenReturn(m);
        when(range.getStartTime()).thenReturn(1);
        when(range.getEndTime()).thenReturn(2);
        when(transformator.toRangePojo(JSON_RANGE)).thenReturn(range);
        when(req.headers("user")).thenReturn("xorrr");
    }

    private void handleRequest() {
        p.handle(req, resp);
    }

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Spark.class);

        p = new PUTmediaRoute(facade, transformator);

        m = new Media("www.random.org");
        m.setAvgStartTime(5);
        m.setAvgEndTime(10);
        m.setChoicesByUsers(25);
    }

    @Test
    public void implementsRoute() {
        assertTrue(p instanceof Route);
    }

    @Test
    public void bodyAccessed() {
        mockBehaviour();
        when(transformator.toMediaJson(any(Media.class))).thenReturn("");

        handleRequest();

        verify(req, times(1)).body();
    }

    @Test
    public void idParamChecked() {
        mockBehaviour();
        when(transformator.toMediaJson(any(Media.class))).thenReturn("");

        handleRequest();

        verify(req, times(1)).params(MappedRoutesParams.ID);
    }

    @Test
    public void bodyTransformedToRange() {
        mockBehaviour();

        handleRequest();

        verify(transformator, times(1)).toRangePojo(JSON_RANGE);
    }

    @Test
    public void applyRange() {
        mockBehaviour();

        handleRequest();

        verify(facade, times(1)).applyRangeToMedia(ID, range);
    }

    @Test
    public void statusCode200() {
        mockBehaviour();
        when(req.contentLength()).thenReturn(1);
        when(facade.applyRangeToMedia(anyString(), any(Range.class)))
                .thenReturn(true);

        handleRequest();

        verify(resp, times(1)).status(200);
    }

    @Test
    public void return400IfRangeInvalid() {
        mockBehaviour();
        when(range.getEndTime()).thenReturn(1);
        when(range.getStartTime()).thenReturn(3);
        when(transformator.toRangePojo(JSON_RANGE)).thenReturn(range);

        handleRequest();

        verify(resp, times(1)).status(400);
    }

    @Test
    public void statusCode204IfNoContent() {
        when(req.contentLength()).thenReturn(-1);

        handleRequest();

        verify(resp, times(1)).status(204);
        verify(transformator, times(0)).toRangePojo(JSON_RANGE);
    }

    @Test
    public void checksForUser() {
        mockBehaviour();

        handleRequest();

        verify(req, times(1)).headers("user");
    }

    @Test
    public void statusCode401IfNoUserProvided() {
        mockBehaviour();
        when(req.headers("user")).thenReturn(null);

        handleRequest();

        PowerMockito.verifyStatic();
        Spark.halt(401, "Unauthorized");
    }

    @Test
    public void canSuccessfullyAuthenticate() {
        mockBehaviour();

        handleRequest();

        verify(req, times(1)).contentLength();
    }
}
