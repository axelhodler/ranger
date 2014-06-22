package xorrr.github.io.rest.routes.range;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class PutRangeRoute implements Route {

    private DatastoreFacade ds;
    private Transformator transformator;
    private RestHelperFacade restHelper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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

        if (noJsonPayload(req)) {
            logger.debug("No content/request body");
            restHelper.stopRequest(404, "todo");
        } else {
            rangeId = modifyRange(req);
        }

        return rangeId;
    }

    private String modifyRange(Request req) {
        String mediaId = req.params(MappedRoutesParams.ID);
        String userId = checkUserIdProvided(req);

        String rangeId = storeModifiedRange(req, mediaId, userId);
        return rangeId;
    }

    private boolean noJsonPayload(Request req) {
        return req.contentLength() < 1;
    }

    private String storeModifiedRange(Request req, String mediaId, String userId) {
        Range r = transformator.toRangePojo(req.body());
        String rangeId = ds.modifyRange(r, mediaId, userId);
        logger.info("Range with id: {} modified", rangeId);
        return rangeId;
    }

    private String checkUserIdProvided(Request req) {
        String userId = req.queryParams(RouteQueryParams.USER_ID);
        if (userId == null) {
            logger.debug("No user id provided");
            restHelper.stopRequest(404, "todo");
        }
        return userId;
    }

}
