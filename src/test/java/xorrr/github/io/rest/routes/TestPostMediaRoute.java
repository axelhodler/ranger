package xorrr.github.io.rest.routes;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import spark.Route;
import xorrr.github.io.db.DatastoreFacade;

@RunWith(MockitoJUnitRunner.class)
public class TestPostMediaRoute {

    @Mock
    DatastoreFacade facade;

    @Test
    public void canCreatePostMediaRoute() {
        PostMediaRoute pmr = new PostMediaRoute(facade);

        assertTrue(Route.class.isAssignableFrom(PostMediaRoute.class));
    }

}
