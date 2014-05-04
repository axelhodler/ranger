package xorrr.github.io.db;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Before
    public void setUp() {
        facade = new DatastoreFacade(userDs, mediaDs);
    }

    @Test
    public void canTriggerToStoreUser() throws UnknownHostException {
        User u = new User();
        u.setName("xorrr");

        facade.storeUser(u);

        verify(userDs, times(1)).storeUser(u);
    }

    @Test
    public void canTriggerToGetUser() {
        facade.getUserById(ID);

        verify(userDs, times(1)).getUserById(ID);
    }

    @Test
    public void canTriggerToDeleteUser() throws Exception {
        facade.deleteUserById(ID);

        verify(userDs, times(1)).deleteUserById(ID);
    }

    @Test
    public void canTriggerToStoreMedia() {
        Media m = new Media("www.url.org");
        facade.storeMedia(m);

        verify(mediaDs, times(1)).storeMedia(m);
    }

    @Test
    public void canTriggerToAddRangeToMedia() {
        Range r = new Range(42,43);
        String id = "1234";

        facade.addRangeToMedia(id, r);

        verify(mediaDs, times(1)).addRangeToMedia(id, r);
    }

    @Test
    public void canTriggerToGetAverageRange() {
        String id = "1234";

        facade.getAverageRangeFor(id);

        verify(mediaDs, times(1)).getAverageRange(id);
    }
}
