package xorrr.github.io;

import io.dropwizard.Application;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import xorrr.github.io.auth.SimpleAuthenticator;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.di.Module;
import xorrr.github.io.rest.resources.MediaResource;
import xorrr.github.io.rest.resources.RangeResource;
import xorrr.github.io.rest.resources.UserResource;
import xorrr.github.io.rest.transformation.Transformator;

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
        DatastoreFacade dsf = injector.getInstance(DatastoreFacade.class);
        Transformator tf = injector.getInstance(Transformator.class);

        MediaResource mediaResource = new MediaResource(dsf, tf);
        RangeResource rangeResource = new RangeResource(dsf, tf);
        UserResource userResource = new UserResource();

        env.jersey().register(mediaResource);
        env.jersey().register(rangeResource);
        env.jersey().register(userResource);

        env.jersey().register(new BasicAuthProvider<>(new SimpleAuthenticator(), "SUPER SECRET STUFF"));
    }

}
