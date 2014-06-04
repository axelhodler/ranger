package xorrr.github.io.rest;

import spark.Spark;

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
