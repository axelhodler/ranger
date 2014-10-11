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
public class GETmediaByIdRouteTest {

    @Mock
    DatastoreFacade facade;
    @Mock
    Transformator transformator;

    private final String ID = "1324";
    private final String JSON = "foo";

    private Media prepareTransformationToJson() {
        Media m = new Media("www.foo.org");
//        when(req.params(MappedRoutesParams.ID)).thenReturn(ID);
        when(facade.getMediaById(ID)).thenReturn(m);
        when(transformator.toMediaJson(m)).thenReturn(JSON);
        return m;
    }

    private void handleRequest() {
        //route.handle(req, resp);
    }

    @Before
    public void setUp() {
        //route = new GETmediaByIdRoute(facade, transformator);
    }

    @Test
    public void getMediaByIdRouteSubsRoute() {
        //assertTrue(route instanceof Route);
    }

    @Test
    public void idIsAccessed() {
        handleRequest();

        //verify(req, times(1)).params(MappedRoutesParams.ID);
    }

    @Test
    public void mediaIsReturnedFromDatastore() {
        //when(req.params(MappedRoutesParams.ID)).thenReturn(ID);

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

        //String msg = route.handle(req, resp);

        //assertEquals(JSON, msg);
    }

    @Test
    public void sameOriginPolicyIsDealthWith() {
        handleRequest();
    }
}