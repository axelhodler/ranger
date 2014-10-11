package xorrr.github.io.rest.routes.media;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
public class POSTmediaRouteTest {

    @Mock
    DatastoreFacade facade;
    @Mock
    Transformator transformator;
    @Mock
    Media media;

    private final String URL = "www.foo.org";
    private final String JSON = "asdf";
    private final String ID = "id";

    private void prepareRequest() {
        when(facade.storeMedia(media)).thenReturn(ID);
        when(media.getUrl()).thenReturn(URL);
    }

    @Before
    public void setUp() {
//        pmr = new POSTmediaRoute(facade, transformator);

        prepareRequest();
    }

    @Test
    public void mediaStored() {
//        handleRequest();

        verify(facade, times(1)).storeMedia(media);
    }

    @Test
    public void responseStatus201() {
    }

    @Test
    public void idReturned() {
    }

    @Test
    public void locationHeaderSet() {
        String host = "localhost:port";
        String pathInfo = "/media";
    }

    @Test
    public void dealWithNoContent() {

    }

    @Test
    public void denyPostingMediaWithEqualUrlTwice() {
        when(facade.urlStored(URL)).thenReturn(true);

        //handleRequest();

        verify(facade, times(1)).urlStored(URL);
        verify(facade, times(0)).storeMedia(media);
    }

    @Test
    public void return404IfUrlAlreadyUsed() {
        when(facade.urlStored(URL)).thenReturn(true);
    }
}
