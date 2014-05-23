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
    private final String ID = "id";
    private PostMediaRoute pmr;

    private void handleRequest() {
        pmr.handle(req, resp);
    }

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
        handleRequest();

        verify(req, times(1)).body();
    }

    @Test
    public void bodyIsDeserializedToPojo() {
        when(req.body()).thenReturn(json);

        handleRequest();

        verify(transformator, times(1)).toMediaPojo(json);
    }

    @Test
    public void mediaIsStored() {
        when(req.body()).thenReturn(json);
        when(transformator.toMediaPojo(json)).thenReturn(media);

        handleRequest();

        verify(facade, times(1)).storeMedia(media);
    }

    @Test
    public void responseStatusIs201() {
        handleRequest();

        verify(resp, times(1)).status(201);
    }

    @Test
    public void idReturned() {
        when(req.body()).thenReturn(json);
        when(transformator.toMediaPojo(json)).thenReturn(media);
        when(facade.storeMedia(media)).thenReturn(ID);

        String mediaId = (String) pmr.handle(req, resp);

        assertEquals(ID, mediaId);
    }
}
