package xorrr.github.io.rest.spark;

import spark.Spark;
import xorrr.github.io.rest.RestRoutingFacade;
import xorrr.github.io.rest.routes.MappedRoutes;
import xorrr.github.io.rest.routes.media.GETmediaByIdRoute;
import xorrr.github.io.rest.routes.media.GETmediaRoute;
import xorrr.github.io.rest.routes.media.POSTmediaRoute;
import xorrr.github.io.rest.routes.media.PUTmediaRoute;
import xorrr.github.io.rest.routes.range.GETrangeRoute;
import xorrr.github.io.rest.routes.range.POSTrangeRoute;
import xorrr.github.io.rest.routes.range.PutRangeRoute;
import xorrr.github.io.rest.routes.user.POSTuserRoute;

public class SparkRoutingFacade implements RestRoutingFacade {

    private final String DEFAULT_ACCEPT_TYPE = "application/json";

    @Override
    public void setGetMediaByIdRoute(GETmediaByIdRoute getMediaById) {
        Spark.get(MappedRoutes.MEDIA_BY_ID, DEFAULT_ACCEPT_TYPE, getMediaById);
    }

    @Override
    public void setPostMediaRoute(POSTmediaRoute postMedia) {
        Spark.post(MappedRoutes.MEDIA, DEFAULT_ACCEPT_TYPE, postMedia);
    }

    @Override
    public void setPutRangeToMediaRoute(PUTmediaRoute putRangeToMedia) {
        Spark.put(MappedRoutes.MEDIA_BY_ID, DEFAULT_ACCEPT_TYPE,
                putRangeToMedia);
    }

    @Override
    public void setWildcardRoutes() {
        Spark.get("*", (request, response) -> "404");
    }

    @Override
    public void setPostUserRoute(POSTuserRoute postUser) {
        Spark.post(MappedRoutes.USERS, DEFAULT_ACCEPT_TYPE, postUser);
    }

    @Override
    public void setGetMediaRoute(GETmediaRoute getMedia) {
        Spark.get(MappedRoutes.MEDIA, DEFAULT_ACCEPT_TYPE, getMedia);
    }

    @Override
    public void setGetRangeRoute(GETrangeRoute getRange) {
        Spark.get(MappedRoutes.RANGE, DEFAULT_ACCEPT_TYPE, getRange);
    }

    @Override
    public void setPostRangeRoute(POSTrangeRoute postRange) {
        Spark.post(MappedRoutes.RANGE_FOR_MEDIAID, DEFAULT_ACCEPT_TYPE,
                postRange);
    }

    @Override
    public void setPutRangeRoute(PutRangeRoute putRange) {
        Spark.put(MappedRoutes.RANGE, DEFAULT_ACCEPT_TYPE, putRange);
    }

}
