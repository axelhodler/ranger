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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.frontend.ember.EmberCompliance;
import xorrr.github.io.model.Media;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.transformation.Transformator;
import xorrr.github.io.utils.HttpHeaderKeys;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EmberCompliance.class)
public class GETmediaByIdRouteTest {

    @Mock
    DatastoreFacade facade;
    @Mock
    Transformator transformator;
    @Mock
    Request req;
    @Mock
    Response resp;

    private GETmediaByIdRoute r;
    private final String ID = "1324";
    private final String JSON = "foo";

    private Media prepareTransformationToJson() {
        Media m = new Media("www.foo.org");
        when(req.params(MappedRoutesParams.ID)).thenReturn(ID);
        when(facade.getMediaById(ID)).thenReturn(m);
        when(transformator.toMediaJson(m)).thenReturn(JSON);
        return m;
    }

    private void handleRequest() {
        r.handle(req, resp);
    }

    @Before
    public void setUp() {
        PowerMockito.mockStatic(EmberCompliance.class);

        r = new GETmediaByIdRoute(facade, transformator);
    }

    @Test
    public void getMediaByIdRouteSubsRoute() {
        assertTrue(r instanceof Route);
    }

    @Test
    public void idIsAccessed() {
        handleRequest();

        verify(req, times(1)).params(MappedRoutesParams.ID);
    }

    @Test
    public void mediaIsReturnedFromDatastore() {
        when(req.params(MappedRoutesParams.ID)).thenReturn(ID);

        handleRequest();

        verify(facade, times(1)).getMediaById(ID);
    }

    @Test
    public void mediaIsTransformedToJson() {
        Media m = prepareTransformationToJson();

        handleRequest();

        verify(transformator, times(1)).toMediaJson(m);
    }

    @Test
    public void mediaJsonIsReturned() {
        prepareTransformationToJson();

        assertEquals("Media serialized to JSON is returned",
                EmberCompliance.formatMedia(JSON), r.handle(req, resp));
    }

    @Test
    public void sameOriginPolicyIsDealthWith() {
        handleRequest();

        verify(resp, times(1)).header(HttpHeaderKeys.ACAOrigin, "*");
    }

    @Test
    public void returnValueIsFormattedToComplyWithEmber() {
        prepareTransformationToJson();

        handleRequest();

        PowerMockito.verifyStatic();
        EmberCompliance.formatMedia(JSON);
    }
}