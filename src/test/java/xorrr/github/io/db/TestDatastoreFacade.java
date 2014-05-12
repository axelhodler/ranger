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
public class TestDatastoreFacade {

    @Mock
    UserDatastore userDs;
    @Mock
    MediaDatastore mediaDs;

    private DatastoreFacade facade;
    private final String ID = "1234";

    private User createExemplaryUser() {
        User u = new User();
        u.setName("xorrr");
        return u;
    }

    private Media createExemplaryMedia() {
        return new Media("www.url.org");
    }

    @Before
    public void setUp() {
        facade = new DatastoreFacade(userDs, mediaDs);
    }

    @Test
    public void canTriggerToStoreUser() throws UnknownHostException {
        User u = createExemplaryUser();

        facade.storeUser(u);

        verify(userDs, times(1)).storeUser(u);
    }

    @Test
    public void canTriggerToGetUser() {
        User exampleUser = createExemplaryUser();
        when(facade.getUserById(ID)).thenReturn(exampleUser);

        User u = facade.getUserById(ID);

        verify(userDs, times(1)).getUserById(ID);
        assertEquals("Correct user is returned", exampleUser, u);
    }

    @Test
    public void canTriggerToDeleteUser() throws Exception {
        facade.deleteUserById(ID);

        verify(userDs, times(1)).deleteUserById(ID);
    }

    @Test
    public void canTriggerToStoreMedia() {
        Media m = createExemplaryMedia();
        facade.storeMedia(m);

        verify(mediaDs, times(1)).storeMedia(m);
    }

    @Test
    public void canTriggerToGetMediaById() {
        Media exampleMedia = createExemplaryMedia();
        when(mediaDs.getMediaById(ID)).thenReturn(exampleMedia);

        Media m = facade.getMediaById(ID);

        verify(mediaDs, times(1)).getMediaById(ID);
        assertEquals("Correct media is returned", exampleMedia, m);
    }

    @Test
    public void canTriggerToAddRangeToMedia() {
        Range r = new Range(42,43);

        facade.applyRangeToMedia(ID, r);

        verify(mediaDs, times(1)).applyRangeToMedia(ID, r);
    }
}
