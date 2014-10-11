package xorrr.github.io.rest.routes.media;

import static org.mockito.Mockito.when;

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
    }

    @Test
    public void return400IfRangeInvalid() {
        mockBehaviour();
        when(range.getEndTime()).thenReturn(1);
        when(range.getStartTime()).thenReturn(3);
    }

    @Test
    public void statusCode204IfNoContent() {

    }

    @Ignore
    @Test
    public void checksForUser() {

    }

    @Test
    public void statusCode401IfNoUserProvided() {

    }

    @Test
    public void canSuccessfullyAuthenticate() {

    }
}
