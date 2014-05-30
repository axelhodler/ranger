package xorrr.github.io.rest.routes.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.User;
import xorrr.github.io.rest.transformation.Transformator;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Spark.class })
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
    private final String ID = "asdf1234";
    private User u;

    private String handleRequest() {
        return (String) route.handle(req, resp);
    }

    private void createUser() {
        u = new User();
        u.setLogin("xorrr");
    }

    private void mockBasicBehaviour() {
        when(req.contentLength()).thenReturn(1);
        when(trans.toUserPojo(JSON)).thenReturn(u);
        when(req.body()).thenReturn(JSON);
    }

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Spark.class);
        route = new POSTuserRoute(facade, trans);
        createUser();
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

        PowerMockito.verifyStatic();
        Spark.halt(204, "No content provided");
        verify(req, times(0)).body();
    }

    @Test
    public void canPostUser() {
        mockBasicBehaviour();

        handleRequest();

        verify(facade, times(1)).storeUser(u);
        verify(resp, times(1)).status(201);
    }

    @Test
    public void accessJSONpayload() {
        mockBasicBehaviour();

        handleRequest();

        verify(req, times(1)).body();
        verify(trans, times(1)).toUserPojo(JSON);
    }

    @Test
    public void locationHeaderIsSet() {
        String host = "localhost:port";
        String pathInfo = "/post";
        mockBasicBehaviour();
        when(req.host()).thenReturn(host);
        when(req.pathInfo()).thenReturn(pathInfo);
        when(facade.storeUser(u)).thenReturn(ID);

        handleRequest();

        verify(req, times(1)).host();
        verify(req, times(1)).pathInfo();
        verify(resp, times(1)).header("Location",
                "http://" + host + pathInfo + "/" + ID);
    }

    @Test
    public void userIdIsReturned() {
        mockBasicBehaviour();
        when(facade.storeUser(u)).thenReturn(ID);

        String msg = handleRequest();

        assertEquals(ID, msg);
    }

}
