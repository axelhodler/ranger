package xorrr.github.io.rest.routes;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.transformation.Transformator;

public class PutRangeToMediaRoute implements Route {

    private DatastoreFacade facade;
    private Transformator transformator;

    public PutRangeToMediaRoute(DatastoreFacade facade, Transformator t) {
        this.facade = facade;
        this.transformator = t;
    }

    @Override
    public String handle(Request req, Response resp) {
        String mediaId = req.params(MappedRoutesParams.ID);
        Media m = facade.getMediaById(mediaId);
        Range r = transformator.toRangePojo(req.body());
        facade.applyRangeToMedia(mediaId, r);

        return transformator.toMediaJson(m);
    }

}
