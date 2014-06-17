package xorrr.github.io.db;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.model.User;

@RunWith(MockitoJUnitRunner.class)
public class DatastoreFacadeTest {

    @Mock
    UserDatastore userDs;
    @Mock
    MediaDatastore mediaDs;
    @Mock
    RangeDatastore rangeDs;

    private DatastoreFacade facade;
    private final String ID = "1234";

    private User createExemplaryUser() {
        User u = new User();
        u.setLogin("xorrr");
        return u;
    }

    private Media createExemplaryMedia() {
        return new Media("www.url.org");
    }

    @Before
    public void setUp() {
        facade = new DatastoreFacade(userDs, mediaDs, rangeDs);
    }

    @Test
    public void canTriggerToStoreUser() throws UnknownHostException {
        User u = createExemplaryUser();
        String fakeId = "1234";
        when(userDs.storeUser(u)).thenReturn(fakeId);

        String userId = facade.storeUser(u);

        verify(userDs, times(1)).storeUser(u);
        assertEquals(userId, fakeId);
    }

    @Test
    public void canGetUser() {
        User exampleUser = createExemplaryUser();
        when(facade.getUserById(ID)).thenReturn(exampleUser);

        User u = facade.getUserById(ID);

        verify(userDs, times(1)).getUserById(ID);
        assertEquals("Correct user is returned", exampleUser, u);
    }

    @Test
    public void canDeleteUser() throws Exception {
        facade.deleteUserById(ID);

        verify(userDs, times(1)).deleteUserById(ID);
    }

    @Test
    public void canStoreMedia() {
        Media m = createExemplaryMedia();
        facade.storeMedia(m);

        verify(mediaDs, times(1)).storeMedia(m);
    }

    @Test
    public void storedMediaIdReturned() {
        Media m = createExemplaryMedia();
        when(mediaDs.storeMedia(m)).thenReturn(ID);

        assertEquals("storeMedia returns id", ID, facade.storeMedia(m));
    }

    @Test
    public void canGetMediaById() {
        Media m = createExemplaryMedia();
        when(mediaDs.getMediaById(ID)).thenReturn(m);

        Media foundMedia = facade.getMediaById(ID);

        verify(mediaDs, times(1)).getMediaById(ID);
        assertEquals("Correct media is returned", m, foundMedia);
    }

    @Test
    public void canCheckUrlExists() {
        facade.urlStored("asdf");

        verify(mediaDs, times(1)).urlStored("asdf");
    }

    @Test
    public void canGetMedia() {
        facade.getMedia();

        verify(mediaDs, times(1)).getMedia();
    }

    @Test
    public void canGetRange() {
        facade.getRange("1", "2");

        verify(rangeDs, times(1)).getRangeFor("1", "2");
    }

    @Test
    public void canGetAverages() {
        facade.getAverageRange("mediaId");

        verify(rangeDs, times(1)).getAverages("mediaId");
    }

    @Test
    public void canSetRange() {
        Range r = new Range(1,2);
        facade.addRange(r, "mediaId", "userId");

        verify(rangeDs, times(1)).setRange(r, "mediaId", "userId");
    }
}
