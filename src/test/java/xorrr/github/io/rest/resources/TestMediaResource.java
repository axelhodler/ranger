package xorrr.github.io.rest.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.transformation.Transformator;
import xorrr.github.io.utils.TestHelpers;

import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class TestMediaResource {

    @Mock
    Transformator t;
    @Mock
    DatastoreFacade ds;
    @Mock
    UriInfo uriInfo;

    private final String MEDIA_ID = "mediaId";
    private static Media MEDIA = new Media();
    private final String URL = "www.foo.org";
    private final Range RANGE = new Range();
    private URI LOCATION_URI;

    private MediaResource res;

    @Before
    public void setUp() throws URISyntaxException {
        MEDIA.setUrl(URL);
        res = new MediaResource(ds, t);

        LOCATION_URI = new URI("location");

        when(uriInfo.getAbsolutePath()).thenReturn(LOCATION_URI);
    }

    /*
     * GET
     */
    @Test
    public void mediaIsReturnedFromDatastore() {
        res.getMediaById(MEDIA_ID);

        verify(ds, times(1)).getMediaById(MEDIA_ID);
    }

    @Test
    public void mediaIsTransformedToJson() {
        when(ds.getMediaById(MEDIA_ID)).thenReturn(MEDIA);

        res.getMediaById(MEDIA_ID);

        verify(t, times(1)).toMediaJson(MEDIA);
    }

    @Test
    public void sameOriginPolicyIsDealthWithWhenAccessingSingleMedium() {
        Response resp = res.getMediaById(MEDIA_ID);

        assertEquals("*", TestHelpers.getSameOriginHeader(resp));
    }

    @Test
    public void getsAllMedia() {
        res.getMedias();

        verify(ds, times(1)).getMedia();
    }

    @Test
    public void transformsMediaToJSON() {
        List<Media> media = Lists.newArrayList(MEDIA);
        when(ds.getMedia()).thenReturn(media);

        res.getMedias();

        verify(t, times(1)).toMediaListJson(media);
    }

    @Test
    public void sameOriginPolicyIsDealthWith() {
        Response resp = res.getMedias();

        assertEquals("*", TestHelpers.getSameOriginHeader(resp));
    }

    /*
     * POST
     */
    @Test
    public void mediaStored() throws URISyntaxException {
        res.addMedia(uriInfo, MEDIA);

        verify(ds, times(1)).storeMedia(MEDIA);
    }

    @Test
    public void status201WhenAddingMedia() throws URISyntaxException {
        Response resp = res.addMedia(uriInfo, MEDIA);

        assertEquals(201, resp.getStatus());
    }

    @Test
    public void locationHeaderSet() throws URISyntaxException {
        when(ds.storeMedia(MEDIA)).thenReturn(MEDIA_ID);

        Response resp = res.addMedia(uriInfo, MEDIA);

        assertEquals("location/" + MEDIA_ID, getLocationHeader(resp));
    }

    @Test
    public void return404IfUrlAlreadyUsed() throws URISyntaxException {
        when(ds.urlStored(URL)).thenReturn(true);

        assertEquals(404, res.addMedia(uriInfo, MEDIA).getStatus());
    }

    private String getLocationHeader(Response resp) {
        return resp.getMetadata().get("Location").get(0).toString();
    }
}
