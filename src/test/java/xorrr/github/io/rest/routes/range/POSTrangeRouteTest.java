package xorrr.github.io.rest.routes.range;

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
import xorrr.github.io.exceptions.AlreadyStoredException;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.transformation.Transformator;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class POSTrangeRouteTest {
    @Mock
    DatastoreFacade ds;
    @Mock
    Transformator tf;

    private final String MEDIA_ID = "mediaId";
    private final String USER_ID = "userId";
    private final String RANGE_ID = "rangeId";
    private final String RANGE_JSON = "json";
    private Range range = new Range(1, 2);

    @Before
    public void setUp() {

    }

    @Test
    public void rangeSaved() throws AlreadyStoredException {
        verify(ds, times(1)).storeRange(range, MEDIA_ID, USER_ID);
    }

    @Test
    public void returns404IfNoContent() {
//        verify(restHelper, times(1)).stopRequest(404, "todo");
    }

    @Test
    public void returns404IfNoUserIdInQueryParams() {
//        verify(restHelper, times(1)).stopRequest(404, "todo");
    }

    @Test
    public void idOfSavedRangeReturned() throws AlreadyStoredException {
        when(ds.storeRange(range, MEDIA_ID, USER_ID)).thenReturn(RANGE_ID);
    }
}
