package xorrr.github.io.rest.routes.media;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.SparkFacade;
import xorrr.github.io.rest.transformation.Transformator;

public class PUTmediaRoute implements Route {

    private DatastoreFacade facade;
    private Transformator transformator;
    private SparkFacade spark;

    public PUTmediaRoute(DatastoreFacade facade, Transformator t, SparkFacade spark) {
        this.facade = facade;
        this.transformator = t;
        this.spark = spark;
    }

    @Override
    public String handle(Request req, Response resp) {
        String responseBody = "";
        boolean applied = false;

        if (req.headers("user") == null)
            spark.stopRequest(401, "Unauthorized");
        if (noContent(req))
            spark.stopRequest(204, "No Content");
        else {
            Range r = transformator.toRangePojo(req.body());
            if (rangeInvalid(r)) {
                responseBody = handleInvalidRange(resp);
            } else {
                responseBody = req.params(MappedRoutesParams.ID);
                applied = changeMedia(responseBody, r);
                facade.modifyRanges(req.headers("user"), responseBody, r);
            }
        }

        if (applied) {
            resp.status(200);
        } else {
            spark.stopRequest(404, "Not Found");
        }

        return responseBody;
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
