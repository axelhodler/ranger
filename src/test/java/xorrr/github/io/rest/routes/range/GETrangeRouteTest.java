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
import xorrr.github.io.utils.HttpHeaderKeys;

@RunWith(MockitoJUnitRunner.class)
public class GETrangeRouteTest {
    @Mock
    DatastoreFacade dsFacade;
    @Mock
    Transformator trans;
    @Mock
    RestHelperFacade h;
    @Mock
    Request req;
    @Mock
    Response resp;

    private GETrangeRoute route;
    private Range range = new Range(1, 2);
    private final String MEDIA_ID = "42";
    private final String USER_ID = "21";
    private final String JSON = "json";

    private String handleRequest() {
        return route.handle(req, resp);
    }

    private void willReturnMediaIdAndUserIdParameters() {
        when(req.params(MappedRoutesParams.ID)).thenReturn(MEDIA_ID);
        when(req.queryParams(RouteQueryParams.USER_ID)).thenReturn(USER_ID);
    }

    @Before
    public void setUp() {
        route = new GETrangeRoute(dsFacade, trans, h);
    }

    @Test
    public void implementsRoute() {
        assertTrue(route instanceof Route);
    }

    @Test
    public void mediaIdParamChecked() {
        handleRequest();

        verify(req, times(1)).params(MappedRoutesParams.ID);
    }

    @Test
    public void userIdQueryParamChecked() {
        handleRequest();

        verify(req, times(1)).queryParams(RouteQueryParams.USER_ID);
    }

    @Test
    public void searchesForRange() {
        willReturnMediaIdAndUserIdParameters();

        handleRequest();

        verify(dsFacade, times(1)).getRange(MEDIA_ID, USER_ID);
    }

    @Test
    public void rangeInJsonIsReturned() {
        willReturnMediaIdAndUserIdParameters();
        when(dsFacade.getRange(MEDIA_ID, USER_ID)).thenReturn(range);
        when(trans.toRangeJson(range)).thenReturn(JSON);

        String json = handleRequest();

        verify(trans, times(1)).toRangeJson(range);
        assertEquals("Correct transformation of range", json, JSON);
    }

    @Test
    public void returnAveragesIfOnlyMediaIdProvided() {
        when(req.params(MappedRoutesParams.ID)).thenReturn(MEDIA_ID);
        when(req.queryParams(RouteQueryParams.USER_ID)).thenReturn(null);
        when(dsFacade.getAverageRange(MEDIA_ID)).thenReturn(range);
        when(trans.toRangeJson(range)).thenReturn(JSON);

        String json = handleRequest();

        verify(dsFacade, times(1)).getAverageRange(MEDIA_ID);
        assertEquals("Correct transformation of range", json, JSON);
    }

    @Test
    public void returns404IfNecessaryQueryParamsNotPresent() {
        when(req.params(MappedRoutesParams.ID)).thenReturn(null);
        when(req.queryParams(RouteQueryParams.USER_ID)).thenReturn(null);

        handleRequest();

        verify(h, times(1)).stopRequest(404, "todo");
    }

    @Test
    public void corsIsTakenCareOf() {
        handleRequest();

        verify(resp, times(1)).header(HttpHeaderKeys.ACAOrigin, "*");
    }
}
