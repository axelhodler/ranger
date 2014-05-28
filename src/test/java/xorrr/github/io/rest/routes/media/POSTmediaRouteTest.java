package xorrr.github.io.rest.routes.media;

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
import xorrr.github.io.rest.routes.media.POSTmediaRoute;
import xorrr.github.io.rest.transformation.Transformator;

@RunWith(MockitoJUnitRunner.class)
public class POSTmediaRouteTest {

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

    private final String JSON = "asdf";
    private final String ID = "id";

    private POSTmediaRoute pmr;

    private void handleRequest() {
        pmr.handle(req, resp);
    }

    private void prepareRequest() {
        when(req.contentLength()).thenReturn(1);
        when(req.body()).thenReturn(JSON);
        when(transformator.toMediaPojo(JSON)).thenReturn(media);
        when(facade.storeMedia(media)).thenReturn(ID);
    }

    @Before
    public void setUp() {
        pmr = new POSTmediaRoute(facade, transformator);

        prepareRequest();
    }

    @Test
    public void postRouteSubsRoute() {
        assertTrue(pmr instanceof Route);
    }

    @Test
    public void bodyAccessed() {
        handleRequest();

        verify(req, times(1)).body();
    }

    @Test
    public void bodyDeserializedToPojo() {
        handleRequest();

        verify(transformator, times(1)).toMediaPojo(JSON);
    }

    @Test
    public void mediaStored() {
        handleRequest();

        verify(facade, times(1)).storeMedia(media);
    }

    @Test
    public void responseStatus201() {
        handleRequest();

        verify(resp, times(1)).status(201);
    }

    @Test
    public void idReturned() {
        String mediaId = (String) pmr.handle(req, resp);

        assertEquals(ID, mediaId);
    }

    @Test
    public void locationHeaderSet() {
        String host = "localhost:port";
        String pathInfo = "/media";
        when(req.host()).thenReturn(host);
        when(req.pathInfo()).thenReturn(pathInfo);

        handleRequest();

        verify(req, times(1)).host();
        verify(req, times(1)).pathInfo();
        verify(resp, times(1)).header("Location",
                "http://" + host + pathInfo + "/" + ID);
    }

    @Test
    public void dealWithNoContent() {
        when(req.contentLength()).thenReturn(-1);

        handleRequest();

        verify(resp, times(1)).status(204);
        verify(facade, times(0)).storeMedia(media);
        verify(req, times(0)).host();
        verify(req, times(0)).pathInfo();
    }
}
