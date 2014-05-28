package xorrr.github.io.rest.routes;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.User;
import xorrr.github.io.rest.transformation.Transformator;

@RunWith(MockitoJUnitRunner.class)
public class POSTuserRouteTest {

    @Mock
    Request req;
    @Mock
    Response resp;
    @Mock
    DatastoreFacade facade;
    @Mock
    Transformator trans;

    private POSTuserRoute route;
    private final String JSON = "asdf";

    private void handleRequest() {
        route.handle(req, resp);
    }

    private User createUser() {
        User u = new User();
        u.setLogin("xorrr");
        return u;
    }

    private void mockBasicBehaviour() {
        when(req.contentLength()).thenReturn(1);
    }

    @Before
    public void setUp() {
        route = new POSTuserRoute(facade, trans);
    }

    @Test
    public void implementsRoute() {
        assertTrue(route instanceof Route);
    }

    @Test
    public void checksContentLength() {
        mockBasicBehaviour();

        handleRequest();

        verify(req, times(1)).contentLength();
    }

    @Test
    public void noContentReturns204() {
        when(req.contentLength()).thenReturn(0);

        handleRequest();

        verify(resp, times(1)).status(204);
        verify(req, times(0)).body();
    }

    @Test
    public void canPostUser() {
        User u = createUser();
        mockBasicBehaviour();
        when(trans.toUserPojo(JSON)).thenReturn(u);
        when(req.body()).thenReturn(JSON);

        handleRequest();

        verify(facade, times(1)).storeUser(u);
        verify(resp, times(1)).status(201);
    }

    @Test
    public void accessJSONpayload() {
        User u = createUser();
        mockBasicBehaviour();
        when(trans.toUserPojo(JSON)).thenReturn(u);
        when(req.body()).thenReturn(JSON);

        handleRequest();

        verify(req, times(1)).body();
        verify(trans, times(1)).toUserPojo(JSON);
    }


}
