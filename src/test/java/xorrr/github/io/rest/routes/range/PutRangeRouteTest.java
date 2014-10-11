package xorrr.github.io.rest.routes.range;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.transformation.Transformator;
import xorrr.github.io.utils.logging.PutRangeRouteLogger;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class PutRangeRouteTest {

    @Mock
    DatastoreFacade ds;
    @Mock
    Transformator tf;
    @Mock
    PutRangeRouteLogger logger;

    private final String MEDIA_ID = "mediaId";
    private final String USER_ID = "userId";
    private final String RANGE_ID = "rangeId";
    private final String RANGE_JSON = "json";
    private Range range = new Range(1, 2);

    @Test
    public void modifiesRange() {

    }

    @Test
    public void returns404IfNoContent() {

    }

    @Test
    public void returns404IfNoUserIdInQueryParams() {

    }

    @Test
    public void idOfSavedRangeReturned() {

    }
}
