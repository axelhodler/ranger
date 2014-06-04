package xorrr.github.io.di;

import xorrr.github.io.db.MediaDatastore;
import xorrr.github.io.db.MediaMongoDatastore;
import xorrr.github.io.db.UserDatastore;
import xorrr.github.io.db.UserMongoDatastore;
import xorrr.github.io.frontend.JsonCompliance;
import xorrr.github.io.frontend.ember.EmberCompliance;
import xorrr.github.io.rest.RestHelperFacade;
import xorrr.github.io.rest.RestRoutingFacade;
import xorrr.github.io.rest.spark.SparkHelperFacade;
import xorrr.github.io.rest.spark.SparkRoutingFacade;

import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(JsonCompliance.class).to(EmberCompliance.class);

        bind(MediaDatastore.class).to(MediaMongoDatastore.class);

        bind(UserDatastore.class).to(UserMongoDatastore.class);

        bind(RestHelperFacade.class).to(SparkHelperFacade.class);

        bind(RestRoutingFacade.class).to(SparkRoutingFacade.class);
    }
}
