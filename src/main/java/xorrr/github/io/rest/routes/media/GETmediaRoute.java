package xorrr.github.io.rest.routes.media;

import java.util.List;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.rest.transformation.Transformator;

public class GETmediaRoute implements Route {

    private DatastoreFacade facade;
    private Transformator transformator;

    public GETmediaRoute(DatastoreFacade facade, Transformator transformator) {
        this.facade = facade;
        this.transformator = transformator;
    }

    @Override
    public Object handle(Request request, Response response) {
        String jsonMedias = "";
        List<Media> medias = facade.getMedia();
        for (Media m : medias)
            jsonMedias += transformator.toMediaJson(m);

        return jsonMedias;
    }

}
