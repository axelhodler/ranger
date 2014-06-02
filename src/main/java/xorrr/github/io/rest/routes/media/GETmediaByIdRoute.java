package xorrr.github.io.rest.routes.media;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.frontend.ember.EmberCompliance;
import xorrr.github.io.model.Media;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.transformation.Transformator;
import xorrr.github.io.utils.HttpHeaderKeys;

public class GETmediaByIdRoute implements Route {

    private DatastoreFacade facade;
    private Transformator transformator;

    public GETmediaByIdRoute(DatastoreFacade f, Transformator t) {
        this.facade = f;
        this.transformator = t;
    }

    @Override
    public String handle(Request request, Response response) {
        response.header(HttpHeaderKeys.ACAOrigin, "*");
        String id = request.params(MappedRoutesParams.ID);
        String json = returnMediaJson(id, response);
        return json;
    }

    private String returnMediaJson(String id, Response response) {
        String returnMsg;
        Media m = facade.getMediaById(id);
        if (m == null) {
            response.status(404);
            returnMsg = "404";
        } else {
            returnMsg = EmberCompliance.formatMedia(transformator
                    .toMediaJson(m));
        }
        return returnMsg;
    }

}
