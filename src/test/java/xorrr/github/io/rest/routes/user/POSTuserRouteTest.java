package xorrr.github.io.rest.routes.user;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.rest.transformation.Transformator;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class POSTuserRouteTest {

    @Mock
    DatastoreFacade facade;
    @Mock
    Transformator trans;

    private final String JSON = "asdf";
    private final String ID = "asdf1234";

    public void noContentReturns204() {

    }

    @Test
    public void canPostUser() {
    }

    @Test
    public void accessJSONpayload() {
    }

    @Test
    public void locationHeaderIsSet() {
        String host = "localhost:port";
        String pathInfo = "/post";
    }

    @Test
    public void userIdIsReturned() {

    }

}
