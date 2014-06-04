package xorrr.github.io.rest;

import spark.Spark;
import xorrr.github.io.rest.routes.MappedRoutes;
import xorrr.github.io.rest.routes.media.GETmediaByIdRoute;
import xorrr.github.io.rest.routes.media.GETmediaRoute;
import xorrr.github.io.rest.routes.media.POSTmediaRoute;
import xorrr.github.io.rest.routes.media.PUTmediaRoute;
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

}
