package xorrr.github.io.rest;

import spark.Spark;
import xorrr.github.io.rest.routes.GetMediaByIdRoute;
import xorrr.github.io.rest.routes.PostMediaRoute;
import xorrr.github.io.rest.routes.PutRangeToMediaRoute;

public class SparkFacade {

    public void setPort(int port) {
        Spark.setPort(port);
    }

    public void setGetMediaByIdRoute(GetMediaByIdRoute getMediaById) {
        Spark.get(getMediaById);
    }

    public void setPostMediaRoute(PostMediaRoute postMedia) {
        Spark.post(postMedia);
    }

    public void setPutRangeToMediaRoute(PutRangeToMediaRoute putRangeToMedia) {
        Spark.put(putRangeToMedia);
    }

}
