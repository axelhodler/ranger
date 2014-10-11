package xorrr.github.io.rest.routes.media;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.transformation.Transformator;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class PUTmediaRouteTest {

    @Mock
    DatastoreFacade facade;
    @Mock
    Transformator transformator;
    @Mock
    Range range;
    @Mock

    private final String JSON_RANGE = "{\"startTime\":1, \"endTime\":2}";
    private final String MEDIA_ID = "536a6107ccf258bb9041663a";
    private final String USER_ID = "536a6107ccf258bb9041663b";

    private Media m;

    private void mockBehaviour() {
        when(facade.getMediaById(MEDIA_ID)).thenReturn(m);
        when(range.getStartTime()).thenReturn(1);
        when(range.getEndTime()).thenReturn(2);
        //when(transformator.toRangePojo(JSON_RANGE)).thenReturn(range);
    }

    private void handleRequest() {
        //p.handle(req, resp);
    }

    @Before
    public void setUp() {
        //p = new PUTmediaRoute(facade, transformator, restHelper);

        m = new Media("www.random.org");
    }

    @Test
    public void bodyAccessed() {
        mockBehaviour();
        when(transformator.toMediaJson(any(Media.class))).thenReturn("");

        handleRequest();
    }

    @Test
    public void return400IfRangeInvalid() {
        mockBehaviour();
        when(range.getEndTime()).thenReturn(1);
        when(range.getStartTime()).thenReturn(3);
//        when(transformator.toRangePojo(JSON_RANGE)).thenReturn(range);

        handleRequest();

        //restHelper.stopRequest(404, "Not Found");
    }

    @Test
    public void statusCode204IfNoContent() {

        handleRequest();

        //restHelper.stopRequest(204, "No Content");
    //    verify(transformator, times(0)).toRangePojo(JSON_RANGE);
    }

    @Ignore
    @Test
    public void checksForUser() {
        mockBehaviour();

        handleRequest();
    }

    @Test
    public void statusCode401IfNoUserProvided() {
        mockBehaviour();

        handleRequest();

      //  restHelper.stopRequest(401, "Unauthorized");
    }

    @Test
    public void canSuccessfullyAuthenticate() {
        mockBehaviour();

        handleRequest();
    }
}
