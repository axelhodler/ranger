package xorrr.github.io.rest.routes.range;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.RestHelperFacade;
import xorrr.github.io.rest.RouteQueryParams;
import xorrr.github.io.rest.transformation.Transformator;

import com.google.inject.Inject;

public class PutRangeRoute implements Route{

    private DatastoreFacade ds;
    private Transformator transformator;
    private RestHelperFacade restHelper;

    @Inject
    public PutRangeRoute(DatastoreFacade dsFacade, Transformator trans,
            RestHelperFacade h) {
        ds = dsFacade;
        transformator = trans;
        restHelper = h;
    }

    @Override
    public String handle(Request req, Response resp) {
        String rangeId = "";

        if (req.contentLength() < 1) {
            restHelper.stopRequest(404, "todo");
        } else {
            String mediaId = req.params(MappedRoutesParams.ID);
            String userId = req.queryParams(RouteQueryParams.USER_ID);
            if (userId == null) {
                restHelper.stopRequest(404, "todo");
            }

            Range r = transformator.toRangePojo(req.body());
            rangeId = ds.modifyRange(r, mediaId, userId);
        }

        return rangeId;
    }

}
