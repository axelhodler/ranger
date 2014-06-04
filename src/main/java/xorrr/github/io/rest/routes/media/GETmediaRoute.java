package xorrr.github.io.rest.routes.media;

import java.util.List;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.rest.transformation.Transformator;
import xorrr.github.io.utils.HttpHeaderKeys;

public class GETmediaRoute implements Route {

    private DatastoreFacade facade;
    private Transformator transformator;

    public GETmediaRoute(DatastoreFacade facade, Transformator transformator) {
        this.facade = facade;
        this.transformator = transformator;
    }

    @Override
    public Object handle(Request request, Response response) {
        response.header(HttpHeaderKeys.ACAOrigin, "*");

        List<Media> medias = facade.getMedia();

        return transformator.toMediaListJson(medias);
    }

}
