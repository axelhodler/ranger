package xorrr.github.io.rest.spark;

import spark.Spark;
import xorrr.github.io.rest.RestHelperFacade;

public class SparkHelperFacade implements RestHelperFacade {

    @Override
    public void setPort(int port) {
        Spark.setPort(port);
    }

    @Override
    public void stopRequest(int statusCode, String message) {
        Spark.halt(statusCode, message);
    }

}
