package xorrr.github.io.rest.routes;

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
import xorrr.github.io.model.Media;
import xorrr.github.io.rest.transformation.Transformator;

@RunWith(MockitoJUnitRunner.class)
public class TestPostMediaRoute {

    @Mock
    DatastoreFacade facade;
    @Mock
    Transformator transformator;
    @Mock
    Request req;
    @Mock
    Response resp;
    @Mock
    Media media;

    private final String json = "asdf";
    private PostMediaRoute pmr;

    @Before
    public void setUp() {
        pmr = new PostMediaRoute(facade, transformator);
    }

    @Test
    public void postRouteSubsRoute() {
        assertTrue(pmr instanceof Route);
    }

    @Test
    public void bodyIsAccessed() {
        pmr.handle(req, resp);

        verify(req, times(2)).body();
    }

    @Test
    public void bodyIsDeserializedToPojo() {
        when(req.body()).thenReturn(json);

        pmr.handle(req, resp);

        verify(transformator, times(1)).toMediaPojo(json);
    }

    @Test
    public void mediaIsStored() {
        when(req.body()).thenReturn(json);
        when(transformator.toMediaPojo(json)).thenReturn(media);

        pmr.handle(req, resp);

        verify(facade, times(1)).storeMedia(media);
    }

    @Test
    public void bodyIsReturned() {
        when(req.body()).thenReturn(json);

        assertEquals(json, pmr.handle(req, resp));
    }
}
