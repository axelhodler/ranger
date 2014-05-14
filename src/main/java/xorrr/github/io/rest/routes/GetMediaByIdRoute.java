package xorrr.github.io.rest.routes;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.transformation.Transformator;

public class GetMediaByIdRoute implements Route {

    private DatastoreFacade facade;
    private Transformator transformator;

    public GetMediaByIdRoute(DatastoreFacade f, Transformator t) {
        this.facade = f;
        this.transformator = t;
    }

    @Override
    public String handle(Request request, Response response) {
        String id = request.params(MappedRoutesParams.ID);
        String json = returnMediaJson(id);
        return json;
    }

    private String returnMediaJson(String id) {
        String json;
        Media m = facade.getMediaById(id);
        json = transformator.toMediaJson(m);
        return json;
    }

}
