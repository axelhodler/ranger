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
        String mediaId = "";

        if (noContent(req))
            resp.status(204);
        else {
            mediaId = dealWithContent(req, resp);
        }

        return mediaId;
    }

    private String dealWithContent(Request req, Response resp) {
        String mediaId;
        resp.status(200);
        Range r = transformator.toRangePojo(req.body());
        mediaId = req.params(MappedRoutesParams.ID);
        facade.applyRangeToMedia(mediaId, r);
        return mediaId;
    }

    private boolean noContent(Request req) {
        return req.contentLength() < 1;
    }

}
