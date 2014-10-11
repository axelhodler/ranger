package xorrr.github.io.rest.routes.range;

import static org.mockito.Matchers.anyString;
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
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.transformation.Transformator;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class GETrangeRouteTest {
    @Mock
    DatastoreFacade dsFacade;
    @Mock
    Transformator trans;
    @Mock
    Range mockedRange;

    private Range range = new Range(1, 2);
    private final String MEDIA_ID = "42";
    private final String USER_ID = "21";
    private final String JSON = "json";

    private void willReturnMediaIdAndUserIdParameters() {
    }

    private void willTriggerAverages() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void searchesForRange() {
        willReturnMediaIdAndUserIdParameters();

        verify(dsFacade, times(1)).getRange(MEDIA_ID, USER_ID);
    }

    @Test
    public void rangeInJsonIsReturned() {
        willReturnMediaIdAndUserIdParameters();
        when(dsFacade.getRange(MEDIA_ID, USER_ID)).thenReturn(range);
        when(trans.toRangeJson(range)).thenReturn(JSON);


        verify(trans, times(1)).toRangeJson(range);
        //assertEquals("Correct transformation of range", json, JSON);
//        verify(h, times(0)).stopRequest(anyInt(), anyString());
    }

    @Test
    public void returnAveragesIfOnlyMediaIdProvided() {
        willTriggerAverages();
        when(dsFacade.getAverageRange(MEDIA_ID)).thenReturn(range);
        when(trans.toRangeJson(range)).thenReturn(JSON);

        verify(dsFacade, times(1)).getAverageRange(MEDIA_ID);
//        /assertEquals("Correct transformation of range", json, JSON);
    }

    @Test
    public void returns404IfNecessaryQueryParamsNotPresent() {
//        when(req.params(MappedRoutesParams.ID)).thenReturn(null);

//        verify(h, times(1)).stopRequest(404, "todo");
    }

    @Test
    public void corsIsTakenCareOf() {

    }

    @Test
    public void objectIdSetOnAverageRange() {
        willTriggerAverages();
        when(dsFacade.getAverageRange(MEDIA_ID)).thenReturn(mockedRange);

        verify(mockedRange, times(1)).setObjectId(anyString()); // sucks
    }
}