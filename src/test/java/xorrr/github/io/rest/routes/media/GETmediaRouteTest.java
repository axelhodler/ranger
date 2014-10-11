package xorrr.github.io.rest.routes.media;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.rest.transformation.Transformator;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class GETmediaRouteTest {

    @Mock
    DatastoreFacade facade;
    @Mock
    Transformator transformator;

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
        //route = new GETmediaRoute(facade, transformator);
    }

    @Test
    public void implementsRoute() {
    //    assertTrue(route instanceof Route);
    }

    @Test
    public void willGetAllRoutes() {
      //  route.handle(req, resp);

        verify(facade, times(1)).getMedia();
    }

    @Test
    public void transformRoutesToJson() {
        createMedia();
        when(facade.getMedia()).thenReturn(medias);

//        route.handle(req, resp);

        verify(transformator, times(1)).toMediaListJson(medias);
    }

    @Test
    public void returnsMediaAsJson() {
        createMedia();
        when(facade.getMedia()).thenReturn(medias);
        when(transformator.toMediaListJson(medias)).thenReturn("ab");

  //      String medias = (String) route.handle(req, resp);

        assertEquals("ab", medias);
    }

    @Test
    public void sameOriginPolicyIsDealthWith() {
    }
}
