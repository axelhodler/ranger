package xorrr.github.io.rest.routes.user;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.User;
import xorrr.github.io.rest.RestHelperFacade;
import xorrr.github.io.rest.transformation.Transformator;

import com.google.inject.Inject;

public class POSTuserRoute implements Route {

    private DatastoreFacade facade;
    private Transformator transformator;
    private RestHelperFacade restHelper;

    @Inject
    public POSTuserRoute(DatastoreFacade f, Transformator t, RestHelperFacade h) {
        this.facade = f;
        this.transformator = t;
        this.restHelper = h;
    }

    @Override
    public Object handle(Request req, Response resp) {
        String userId = "";

        if (req.contentLength() <1)
            restHelper.stopRequest(204, "No content provided");
        else {
            userId = handleContent(req, resp);
        }

        return userId;
    }

    private String handleContent(Request req, Response resp) {
        User u = transformator.toUserPojo(req.body());
        String userId = facade.storeUser(u);
        resp.header("Location", "http://" + req.host() + req.pathInfo()
                + "/" + userId);
        resp.status(201);
        return userId;
    }

}
