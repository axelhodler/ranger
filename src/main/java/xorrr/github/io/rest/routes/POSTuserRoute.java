package xorrr.github.io.rest.routes;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.User;
import xorrr.github.io.rest.transformation.Transformator;

public class POSTuserRoute implements Route {

    private DatastoreFacade facade;
    private Transformator transformator;

    public POSTuserRoute(DatastoreFacade f, Transformator t) {
        this.facade = f;
        this.transformator = t;
    }

    @Override
    public Object handle(Request req, Response resp) {
        if (req.contentLength() <1)
            resp.status(204);
        else {
            User u = transformator.toUserPojo(req.body());
            facade.storeUser(u);
            resp.status(201);
        }

        return null;
    }

}
