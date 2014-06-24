package xorrr.github.io.rest.routes.range;

import org.bson.types.ObjectId;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.RestHelperFacade;
import xorrr.github.io.rest.RouteQueryParams;
import xorrr.github.io.rest.transformation.Transformator;
import xorrr.github.io.utils.HttpHeaderKeys;

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
        allowCors(resp);

        Range range = null;

        String mediaId = req.params(MappedRoutesParams.ID);
        String userId = req.queryParams(RouteQueryParams.USER_ID);

        if (bothProvided(mediaId, userId)) {
            range = facade.getRange(mediaId, userId);
        } else if (onlyMediaIdProvided(mediaId, userId)) {
            range = getAverageRange(mediaId);
        } else {
            restHelper.stopRequest(404, "todo");
        }

        return transformator.toRangeJson(range);
    }

    private Range getAverageRange(String mediaId) {
        Range range;
        range = facade.getAverageRange(mediaId);
        range.setObjectId(new ObjectId().toString());
        return range;
    }

    private boolean bothProvided(String mediaId, String userId) {
        return mediaId != null && userId != null;
    }

    private boolean onlyMediaIdProvided(String mediaId, String userId) {
        return userId == null && mediaId != null;
    }

    private void allowCors(Response resp) {
        resp.header(HttpHeaderKeys.ACAOrigin, "*");
    }

}
