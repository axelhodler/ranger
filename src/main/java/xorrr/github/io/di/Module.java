package xorrr.github.io.di;

import xorrr.github.io.db.MediaDatastore;
import xorrr.github.io.db.RangeDatastore;
import xorrr.github.io.db.UserDatastore;
import xorrr.github.io.db.mongo.MediaMongoDatastore;
import xorrr.github.io.db.mongo.RangeMongoDatastore;
import xorrr.github.io.db.mongo.UserMongoDatastore;
import xorrr.github.io.frontend.JsonFormatter;
import xorrr.github.io.frontend.ember.EmberJsonFormatter;
import xorrr.github.io.utils.logging.PutRangeRouteLogger;
import xorrr.github.io.utils.logging.impl.PutRangeRouteSlf4jLogger;

import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(JsonFormatter.class).to(EmberJsonFormatter.class);

        bind(MediaDatastore.class).to(MediaMongoDatastore.class);

        bind(UserDatastore.class).to(UserMongoDatastore.class);

        bind(RangeDatastore.class).to(RangeMongoDatastore.class);

        bind(PutRangeRouteLogger.class).to(PutRangeRouteSlf4jLogger.class);
    }
}
