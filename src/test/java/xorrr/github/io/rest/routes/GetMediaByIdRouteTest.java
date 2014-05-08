package xorrr.github.io.rest.routes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.QueryParams;
import xorrr.github.io.rest.transformation.Transformator;

@RunWith(MockitoJUnitRunner.class)
public class GetMediaByIdRouteTest {

    @Mock
    DatastoreFacade facade;
    @Mock
    Transformator transformator;
    @Mock
    Request req;
    @Mock
    Response resp;
    @Mock
    Set<String> set;

    private GetMediaByIdRoute r;
    private final String ID = "1324";
    private final String FAKE_JSON = "foo";

    private Media prepareTransformationToJson() {
        Media m = new Media("www.foo.org");
        when(req.params(MappedRoutesParams.ID)).thenReturn(ID);
        when(facade.getMediaById(ID)).thenReturn(m);
        when(transformator.toMediaJson(m)).thenReturn(FAKE_JSON);
        return m;
    }

    private void prepareQueryParams() {
        when(req.queryParams()).thenReturn(set);
        when(set.size()).thenReturn(1);
        when(req.queryParams(QueryParams.AVG_RANGE)).thenReturn("true");
    }

    @Before
    public void setUp() {
        r = new GetMediaByIdRoute(facade, transformator);
    }

    @Test
    public void getMediaByIdRouteSubsRoute() {
        assertTrue(Route.class.isAssignableFrom(GetMediaByIdRoute.class));
    }

    @Test
    public void idIsAccessed() {
        r.handle(req, resp);

        verify(req, times(1)).params(MappedRoutesParams.ID);
    }

    @Test
    public void mediaIsReturnedFromDatastore() {
        when(req.params(MappedRoutesParams.ID)).thenReturn(ID);

        r.handle(req, resp);

        verify(facade, times(1)).getMediaById(ID);
    }

    @Test
    public void mediaIsTransformedToJson() {
        Media m = prepareTransformationToJson();

        r.handle(req, resp);

        verify(transformator, times(1)).toMediaJson(m);
    }

    @Test
    public void mediaJsonIsReturned() {
        prepareTransformationToJson();

        assertEquals("Media serialized to JSON is returned", FAKE_JSON,
                r.handle(req, resp));
    }

    @Test
    public void checkForQueryParam() {
        prepareQueryParams();

        r.handle(req, resp);

        verify(req, times(1)).queryParams(QueryParams.AVG_RANGE);
    }

    @Test
    public void calcAverageIfAvgRangeQryParamSet() {
        prepareQueryParams();
        when(req.params(MappedRoutesParams.ID)).thenReturn(ID);

        r.handle(req, resp);

        verify(facade, times(1)).getAverageRangeFor(ID);
        verify(facade, times(0)).getMediaById(ID);
    }

    @Test
    public void canReturnAvgRange() {
        String expectedJson = "{\"startTime\":1,\"endTime\":2}";
        Range range = new Range(1, 2);
        prepareQueryParams();
        when(req.params(MappedRoutesParams.ID)).thenReturn(ID);
        when(facade.getAverageRangeFor(ID)).thenReturn(range);
        when(transformator.toRangeJson(range)).thenReturn(expectedJson);

        String rangeJson = r.handle(req, resp);

        assertEquals(rangeJson, expectedJson);
    }
}
