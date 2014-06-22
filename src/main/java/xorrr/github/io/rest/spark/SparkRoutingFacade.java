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

    @Override
    public void setGetMediaByIdRoute(GETmediaByIdRoute getMediaById) {
        Spark.get(MappedRoutes.MEDIA_BY_ID, getMediaById);
    }

    @Override
    public void setPostMediaRoute(POSTmediaRoute postMedia) {
        Spark.post(MappedRoutes.MEDIA, postMedia);
    }

    @Override
    public void setPutRangeToMediaRoute(PUTmediaRoute putRangeToMedia) {
        Spark.put(MappedRoutes.MEDIA_BY_ID, putRangeToMedia);
    }

    @Override
    public void setWildcardRoutes() {
        Spark.get("*", (request, response) -> "404");
    }

    @Override
    public void setPostUserRoute(POSTuserRoute postUser) {
        Spark.post(MappedRoutes.USERS, postUser);
    }

    @Override
    public void setGetMediaRoute(GETmediaRoute getMedia) {
        Spark.get(MappedRoutes.MEDIA, getMedia);
    }

    @Override
    public void setGetRangeRoute(GETrangeRoute getRange) {
        Spark.get(MappedRoutes.RANGE, getRange);
    }

    @Override
    public void setPostRangeRoute(POSTrangeRoute postRange) {
        Spark.post(MappedRoutes.RANGE_FOR_MEDIAID, postRange);
    }

    @Override
    public void setPutRangeRoute(PutRangeRoute putRange) {
        Spark.put(MappedRoutes.RANGE, putRange);
    }

}
