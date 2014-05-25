package xorrr.github.io.rest.routes;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.transformation.Transformator;

public class PutOnMediaRoute implements Route {

    private DatastoreFacade facade;
    private Transformator transformator;

    public PutOnMediaRoute(DatastoreFacade facade, Transformator t) {
        this.facade = facade;
        this.transformator = t;
    }

    @Override
    public String handle(Request req, Response resp) {
        String returnMsg = "";
        boolean applied = false;

        if (noContent(req))
            resp.status(204);
        else {
            Range r = transformator.toRangePojo(req.body());
            returnMsg = req.params(MappedRoutesParams.ID);
            applied = facade.applyRangeToMedia(returnMsg, r);
        }

        if (applied) {
            resp.status(200);
        } else {
            resp.status(404);
            returnMsg = "404";
        }

        return returnMsg;
    }

    private boolean noContent(Request req) {
        return req.contentLength() < 1;
    }

}
