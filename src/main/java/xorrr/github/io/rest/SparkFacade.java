package xorrr.github.io.rest;

import spark.Spark;
import xorrr.github.io.rest.routes.MappedRoutes;
import xorrr.github.io.rest.routes.media.GETmediaByIdRoute;
import xorrr.github.io.rest.routes.media.GETmediaRoute;
import xorrr.github.io.rest.routes.media.POSTmediaRoute;
import xorrr.github.io.rest.routes.media.PUTmediaRoute;
import xorrr.github.io.rest.routes.user.POSTuserRoute;

public class SparkFacade {

    public void setPort(int port) {
        Spark.setPort(port);
    }

    public void setGetMediaByIdRoute(GETmediaByIdRoute getMediaById) {
        Spark.get(MappedRoutes.MEDIA_BY_ID, getMediaById);
    }

    public void setPostMediaRoute(POSTmediaRoute postMedia) {
        Spark.post(MappedRoutes.MEDIA, postMedia);
    }

    public void setPutRangeToMediaRoute(PUTmediaRoute putRangeToMedia) {
        Spark.put(MappedRoutes.MEDIA_BY_ID, putRangeToMedia);
    }

    public void setWildcardRoutes() {
        Spark.get("*", (request, response) -> "404");
    }

    public void setPostUserRoute(POSTuserRoute postUser) {
        Spark.post(MappedRoutes.USERS, postUser);
    }

    public void setGetMediaRoute(GETmediaRoute getMedia) {
        Spark.get(MappedRoutes.MEDIA, getMedia);
    }

    public void stopRequest(int statusCode, String message) {
        Spark.halt(statusCode, message);
    }

}
