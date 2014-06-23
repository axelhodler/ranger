package xorrr.github.io;

import java.net.UnknownHostException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import xorrr.github.io.di.Module;
import xorrr.github.io.rest.RestHelperFacade;
import xorrr.github.io.rest.RestRoutingFacade;
import xorrr.github.io.rest.routes.media.GETmediaByIdRoute;
import xorrr.github.io.rest.routes.media.GETmediaRoute;
import xorrr.github.io.rest.routes.media.POSTmediaRoute;
import xorrr.github.io.rest.routes.media.PUTmediaRoute;
import xorrr.github.io.rest.routes.range.GETrangeRoute;
import xorrr.github.io.rest.routes.range.POSTrangeRoute;
import xorrr.github.io.rest.routes.range.PutRangeRoute;
import xorrr.github.io.rest.routes.user.POSTuserRoute;
import xorrr.github.io.utils.EnvVars;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO);

        Injector injector = Guice.createInjector(new Module());

        RestRoutingFacade rest = injector.getInstance(RestRoutingFacade.class);
        RestHelperFacade helper = injector.getInstance(RestHelperFacade.class);

        helper.setPort(EnvVars.PORT);

        rest.setPostMediaRoute(injector.getInstance(POSTmediaRoute.class));
        rest.setGetMediaByIdRoute(injector.getInstance(GETmediaByIdRoute.class));
        rest.setPutRangeToMediaRoute(injector.getInstance(PUTmediaRoute.class));
        rest.setPostUserRoute(injector.getInstance(POSTuserRoute.class));
        rest.setGetMediaRoute(injector.getInstance(GETmediaRoute.class));
        rest.setGetRangeRoute(injector.getInstance(GETrangeRoute.class));
        rest.setPostRangeRoute(injector.getInstance(POSTrangeRoute.class));
        rest.setPutRangeRoute(injector.getInstance(PutRangeRoute.class));
    }
}
