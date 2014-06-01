package xorrr.github.io.rest.routes.media;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
public class GETmediaRouteTest {

    @Mock
    Request req;
    @Mock
    Response resp;
    @Mock
    DatastoreFacade facade;
    @Mock
    Transformator transformator;

    private GETmediaRoute route;
    private List<Media> medias;
    private Media media;
    private Media media2;

    private void createMedia() {
        medias = new ArrayList<>();
        media = new Media("www.foo.org");
        media2 = new Media("www.bar.org");
        medias.add(media);
        medias.add(media2);
    }

    @Before
    public void setUp() {
        route = new GETmediaRoute(facade, transformator);
    }

    @Test
    public void implementsRoute() {
        assertTrue(route instanceof Route);
    }

    @Test
    public void willGetAllRoutes() {
        route.handle(req, resp);

        verify(facade, times(1)).getMedia();
    }

    @Test
    public void transformRoutesToJson() {
        createMedia();
        when(facade.getMedia()).thenReturn(medias);

        route.handle(req, resp);

        verify(transformator, times(1)).toMediaJson(media);
        verify(transformator, times(1)).toMediaJson(media2);
    }

    @Test
    public void returnsMediaAsJson() {
        createMedia();
        when(facade.getMedia()).thenReturn(medias);
        when(transformator.toMediaJson(media)).thenReturn("a");
        when(transformator.toMediaJson(media2)).thenReturn("b");

        String medias = (String) route.handle(req, resp);

        assertEquals("ab", medias);
    }
}