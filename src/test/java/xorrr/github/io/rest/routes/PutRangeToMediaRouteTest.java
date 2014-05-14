package xorrr.github.io.rest.routes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.transformation.Transformator;

@RunWith(MockitoJUnitRunner.class)
public class PutRangeToMediaRouteTest {

    @Mock
    Request req;
    @Mock
    Response resp;
    @Mock
    DatastoreFacade facade;
    @Mock
    Transformator transformator;

    private final String JSON_RANGE = "{\"startTime\":1, \"endTime\":2}";
    private final String ID = "536a6107ccf258bb9041663a";
    private PutRangeToMediaRoute p;
    private Media m;
    private Range r;

    private void setNecessaryBehaviour() {
        when(req.params(MappedRoutesParams.ID)).thenReturn(ID);
        when(req.body()).thenReturn(JSON_RANGE);
        when(facade.getMediaById(ID)).thenReturn(m);
        when(transformator.toRangePojo(JSON_RANGE)).thenReturn(r);
    }

    @Before
    public void setup() {
        p = new PutRangeToMediaRoute(facade, transformator);

        m = new Media("www.random.org");
        m.setAvgStartTime(5);
        m.setAvgEndTime(10);
        m.setChoicesByUsers(25);
    }

    @Test
    public void canCreatePutRangeToMediaRoute() {
        assertTrue(p instanceof Route);
    }

    @Test
    public void bodyIsAccessed() {
        setNecessaryBehaviour();
        when(transformator.toMediaJson(any(Media.class))).thenReturn("");

        p.handle(req, resp);

        verify(req, times(1)).body();
    }

    @Test
    public void idParamIsAccessed() {
        setNecessaryBehaviour();
        when(transformator.toMediaJson(any(Media.class))).thenReturn("");

        p.handle(req, resp);

        verify(req, times(1)).params(MappedRoutesParams.ID);
    }

    @Test
    public void mediaIsFoundById() throws Exception {
        setNecessaryBehaviour();
        when(transformator.toMediaJson(any(Media.class))).thenReturn("");
        when(req.params(MappedRoutesParams.ID)).thenReturn(ID);

        p.handle(req, resp);

        verify(facade, times(1)).getMediaById(ID);
    }

    @Test
    public void bodyTransformedAndAddedToMedia() {
        setNecessaryBehaviour();

        p.handle(req, resp);

        verify(transformator, times(1)).toRangePojo(JSON_RANGE);
    }

    @Test
    public void rangeAppliedToMedia() {
        setNecessaryBehaviour();

        p.handle(req, resp);

        verify(facade, times(1)).applyRangeToMedia(ID, r);
    }

    @Test
    public void changedMediaIsReturned() {
        Transformator t = new Transformator();
        setNecessaryBehaviour();
        String correctMediaJson = t.toMediaJson(m);

        when(transformator.toMediaJson(m)).thenReturn(correctMediaJson);

        String changedMedia = p.handle(req, resp);

        assertEquals("Changed media i returned in json", changedMedia,
                correctMediaJson);
    }

}
