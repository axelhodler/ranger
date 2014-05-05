package xorrr.github.io.rest.routes;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.rest.MappedRoutes;

public class PostMediaRoute extends Route {

    private DatastoreFacade facade;

    public PostMediaRoute(DatastoreFacade facade) {
        super(MappedRoutes.MEDIA);
        this.facade = facade;
    }

    @Override
    public Object handle(Request req, Response resp) {
        return null;
    }

}
