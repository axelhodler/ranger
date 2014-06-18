package xorrr.github.io.rest.routes.range;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.RestHelperFacade;
import xorrr.github.io.rest.RouteQueryParams;
import xorrr.github.io.rest.transformation.Transformator;

@RunWith(MockitoJUnitRunner.class)
public class POSTrangeRouteTest {
    @Mock
    DatastoreFacade ds;
    @Mock
    Transformator tf;
    @Mock
    RestHelperFacade restHelper;
    @Mock
    Request req;
    @Mock
    Response resp;

    private POSTrangeRoute route;
    private final String MEDIA_ID = "mediaId";
    private final String USER_ID = "userId";
    private final String RANGE_ID = "rangeId";
    private final String RANGE_JSON = "json";
    private Range range = new Range(1, 2);

    private String handleRequest() {
        return route.handle(req, resp);
    }

    @Before
    public void setUp() {
        route = new POSTrangeRoute(ds, tf, restHelper);

        when(req.contentLength()).thenReturn(1);
    }

    @Test
    public void implementsRoute() {
        assertTrue(route instanceof Route);
    }

    @Test
    public void bodyIsChecked() {
        handleRequest();

        verify(req, times(1)).body();
    }

    @Test
    public void bodyContainsRangeJson() {
        when(req.body()).thenReturn(RANGE_JSON);

        handleRequest();

        verify(tf, times(1)).toRangePojo(RANGE_JSON);
    }

    @Test
    public void mediaIdParameterChecked() {
        when(req.params(MappedRoutesParams.ID)).thenReturn(MEDIA_ID);

        handleRequest();

        verify(req, times(1)).params(MappedRoutesParams.ID);
    }

    @Test
    public void userIdQueryParameterChecked() {
        handleRequest();

        verify(req, times(1)).queryParams(RouteQueryParams.USER_ID);
    }

    @Test
    public void rangeSaved() {
        when(req.body()).thenReturn(RANGE_JSON);
        when(req.params(MappedRoutesParams.ID)).thenReturn(MEDIA_ID);
        when(req.queryParams(RouteQueryParams.USER_ID)).thenReturn(USER_ID);
        when(tf.toRangePojo(RANGE_JSON)).thenReturn(range);

        handleRequest();

        verify(ds, times(1)).addRange(range, MEDIA_ID, USER_ID);
    }

    @Test
    public void returns404IfNoContent() {
        when(req.contentLength()).thenReturn(0);

        handleRequest();

        verify(restHelper, times(1)).stopRequest(404, "todo");
    }

    @Test
    public void returns404IfNoUserIdInQueryParams() {
        when(req.queryParams(RouteQueryParams.USER_ID)).thenReturn(null);

        handleRequest();

        verify(restHelper, times(1)).stopRequest(404, "todo");
    }

    @Test
    public void idOfSavedRangeReturned() {
        when(req.body()).thenReturn(RANGE_JSON);
        when(req.params(MappedRoutesParams.ID)).thenReturn(MEDIA_ID);
        when(req.queryParams(RouteQueryParams.USER_ID)).thenReturn(USER_ID);
        when(tf.toRangePojo(RANGE_JSON)).thenReturn(range);
        when(ds.addRange(range, MEDIA_ID, USER_ID)).thenReturn(RANGE_ID);

        String rangeId = handleRequest();

        assertEquals(rangeId, RANGE_ID);
    }
}
