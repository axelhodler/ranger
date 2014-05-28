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
        String responseBody = "";
        boolean applied = false;

        if (noContent(req))
            resp.status(204);
        else {
            Range r = transformator.toRangePojo(req.body());
            if (rangeInvalid(r)) {
                responseBody = handleInvalidRange(resp);
            } else {
                responseBody = req.params(MappedRoutesParams.ID);
                applied = changeMedia(responseBody, r);
            }
        }

        if (applied) {
            resp.status(200);
        } else {
            responseBody = handleChangeNotApplied(resp);
        }

        return responseBody;
    }

    private String handleChangeNotApplied(Response resp) {
        resp.status(404);
        String returnMsg = "404";
        return returnMsg;
    }

    private boolean changeMedia(String returnMsg, Range r) {
        return facade.applyRangeToMedia(returnMsg, r);
    }

    private String handleInvalidRange(Response resp) {
        String returnMsg;
        resp.status(400);
        returnMsg = "startTime has to be greater than endTime";
        return returnMsg;
    }

    private boolean rangeInvalid(Range r) {
        return r.getEndTime() <= r.getStartTime();
    }

    private boolean noContent(Request req) {
        return req.contentLength() < 1;
    }

}
