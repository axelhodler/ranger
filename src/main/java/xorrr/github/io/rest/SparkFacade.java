package xorrr.github.io.rest;

import spark.Spark;
import xorrr.github.io.rest.routes.GetMediaByIdRoute;
import xorrr.github.io.rest.routes.PostMediaRoute;
import xorrr.github.io.rest.routes.PutOnMediaRoute;

public class SparkFacade {

    public void setPort(int port) {
        Spark.setPort(port);
    }

    public void setGetMediaByIdRoute(GetMediaByIdRoute getMediaById) {
        Spark.get(MappedRoutes.MEDIA_BY_ID, getMediaById);
    }

    public void setPostMediaRoute(PostMediaRoute postMedia) {
        Spark.post(MappedRoutes.MEDIA, postMedia);
    }

    public void setPutRangeToMediaRoute(PutOnMediaRoute putRangeToMedia) {
        Spark.put(MappedRoutes.MEDIA_BY_ID, putRangeToMedia);

        Spark.get("*", (request, response) -> {
            return "404";
        });
    }

}
