package xorrr.github.io.rest.routes.range;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.RestHelperFacade;
import xorrr.github.io.rest.RouteQueryParams;
import xorrr.github.io.rest.transformation.Transformator;

import com.google.inject.Inject;

public class GETrangeRoute implements Route{

    private DatastoreFacade facade;
    private Transformator transformator;
    private RestHelperFacade restHelper;

    @Inject
    public GETrangeRoute(DatastoreFacade f, Transformator t, RestHelperFacade h) {
        facade = f;
        transformator = t;
        restHelper = h;
    }

    @Override
    public String handle(Request req, Response resp) {
        Range range = null;

        String mediaId = req.queryParams(RouteQueryParams.MEDIA_ID);
        String userId = req.queryParams(RouteQueryParams.USER_ID);

        if (mediaId != null && userId != null) {
            range = facade.getRange(mediaId, userId);
        } if (userId == null && mediaId != null) {
            range = facade.getAverageRange(mediaId);
        } else {
            restHelper.stopRequest(404, "todo");
        }

        return transformator.toRangeJson(range);
    }

}
