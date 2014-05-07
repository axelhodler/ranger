package xorrr.github.io.rest.routes;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.rest.MappedRoutes;
import xorrr.github.io.rest.transformation.Transformator;

public class PostMediaRoute extends Route {

    private DatastoreFacade facade;
    private Transformator transformator;

    public PostMediaRoute(DatastoreFacade facade, Transformator transformator) {
        super(MappedRoutes.MEDIA);
        this.facade = facade;
        this.transformator = transformator;
    }

    @Override
    public Object handle(Request req, Response resp) {
        facade.storeMedia(createMediaFromRequestBody(req));

        return req.body();
    }

    private Media createMediaFromRequestBody(Request req) {
        return transformator.toMediaPojo(req.body());
    }
}
