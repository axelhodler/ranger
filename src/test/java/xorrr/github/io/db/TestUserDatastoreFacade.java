package xorrr.github.io.db;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import xorrr.github.io.model.User;

@RunWith(MockitoJUnitRunner.class)
public class TestUserDatastoreFacade {

    @Mock
    UserDatastore ds;

    private UserDatastoreFacade facade;
    private final String ID = "1234";

    @Before
    public void setUp() {
        facade = new UserDatastoreFacade(ds);
    }

    @Test
    public void canTriggerToStoreUser() throws UnknownHostException {
        User u = new User();
        u.setName("xorrr");

        facade.storeUser(u);

        verify(ds, times(1)).storeUser(u);
    }

    @Test
    public void canTriggerToGetUser() {
        facade.getUserById(ID);

        verify(ds, times(1)).getUserById(ID);
    }

    @Test
    public void canTriggerToDeleteUser() throws Exception {
        facade.deleteUserById(ID);

        verify(ds, times(1)).deleteUserById(ID);
    }
}
