package xorrr.github.io.utils.logging.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xorrr.github.io.rest.routes.range.PutRangeRoute;
import xorrr.github.io.utils.logging.PutRangeRouteLogger;

public class PutRangeRouteSlf4jLogger implements PutRangeRouteLogger{

    private Logger logger;

    public PutRangeRouteSlf4jLogger() {
        this.logger = LoggerFactory.getLogger(PutRangeRoute.class);
    }

    @Override
    public void logNoRangePayloadProvided() {
        logger.debug("No content/request body");
    }

    @Override
    public void logNoUserIdProvided() {
        logger.debug("No user id provided");
    }

    @Override
    public void logRangeModified(String rangeId) {
        logger.info("Range with id: {} modified", rangeId);
    }

}
