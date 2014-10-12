package xorrr.github.io.rest.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

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
import xorrr.github.io.utils.TestHelpers;

@RunWith(MockitoJUnitRunner.class)
public class TestRangeResource {

    @Mock
    private DatastoreFacade datastore;
    @Mock
    private Transformator trans;

    private final Range RANGE = new Range(1, 2);
    private final String RANGE_JSON = "rangeJson"; 
    private RangeResource resource;
    private final String MEDIA_ID = "mediaId";

    @Before
    public void setUp() {
        resource = new RangeResource(datastore, trans);
    }

    @Test
    public void searchesForRange() {
        resource.getAvergageRange(MEDIA_ID);

        verify(datastore, times(1)).getAverageRange(MEDIA_ID);
    }

    @Test
    public void rangeInJsonCompliant() {
        when(datastore.getAverageRange(MEDIA_ID)).thenReturn(RANGE);

        resource.getAvergageRange(MEDIA_ID);

        verify(trans, times(1)).toRangeJson(RANGE);
    }

    @Test
    public void rangeIsReturned() {
        when(datastore.getAverageRange(MEDIA_ID)).thenReturn(RANGE);
        when(trans.toRangeJson(RANGE)).thenReturn(RANGE_JSON);

        Response resp = resource.getAvergageRange(MEDIA_ID);

        assertEquals(RANGE_JSON, resp.getEntity().toString());
    }

    @Test
    public void rangeSaved() throws AlreadyStoredException {
        resource.addRange(MEDIA_ID, RANGE);

        verify(datastore, times(1)).storeRange(RANGE, MEDIA_ID, "");
    }

    @Test
    public void returns404IfNoContent() {

    }

    @Test
    public void returns404IfNoUserIdInQueryParams() {

    }

    @Test
    public void idOfSavedRangeReturned() throws AlreadyStoredException {

    }

    @Test
    public void modifiesRange() {

    }

    @Test
    public void returns404IfNoContent2() {

    }

    @Test
    public void returns404IfNoUserIdInQueryParams2() {

    }

    @Test
    public void idOfSavedRangeReturned2() {

    }

    @Ignore
    @Test
    public void canReturnRangeChosenByUser() {
        
    }

    @Test
    public void corsIsTakenCareOf() {
        Response resp  = resource.getAvergageRange(MEDIA_ID);

        assertEquals("*", TestHelpers.getSameOriginHeader(resp));
    }

    @Test
    public void objectIdSetOnAverageRange() {

    }
}
