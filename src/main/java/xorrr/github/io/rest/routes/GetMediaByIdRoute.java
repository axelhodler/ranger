package xorrr.github.io.rest.routes;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.rest.MappedRoutes;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.transformation.Transformator;

public class GetMediaByIdRoute extends Route {

    private DatastoreFacade facade;
    private Transformator transformator;

    public GetMediaByIdRoute(DatastoreFacade f, Transformator t) {
        super(MappedRoutes.MEDIA_BY_ID);
        this.facade = f;
        this.transformator = t;
    }

    @Override
    public Object handle(Request request, Response response) {
        String id = request.params(MappedRoutesParams.ID);
        Media m = facade.getMediaById(id);
        String json =transformator.toMediaJson(m);

        return json;
    }

}
