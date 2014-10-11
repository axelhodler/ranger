package xorrr.github.io;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import xorrr.github.io.di.Module;
import xorrr.github.io.rest.resources.MediaResource;
import xorrr.github.io.rest.resources.RangeResource;
import xorrr.github.io.rest.resources.UserResource;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class RangerApplication extends Application<RangerConfiguration> {

    private Injector injector;

    public static void main(String[] args) throws Exception {
        new RangerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<RangerConfiguration> bootstrap) {
        injector = Guice.createInjector(new Module());
    }

    @Override
    public void run(RangerConfiguration configuration, Environment env) throws Exception {
        MediaResource mediaResource = new MediaResource();
        RangeResource rangeResource = new RangeResource();
        UserResource userResource = new UserResource();

        env.jersey().register(mediaResource);
        env.jersey().register(rangeResource);
        env.jersey().register(userResource);
    }

}
