package xorrr.github.io.rest.routes.media;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.rest.transformation.Transformator;

import com.google.inject.Inject;

public class POSTmediaRoute implements Route {

    private DatastoreFacade facade;
    private Transformator transformator;

    @Inject
    public POSTmediaRoute(DatastoreFacade facade, Transformator transformator) {
        this.facade = facade;
        this.transformator = transformator;
    }

    @Override
    public Object handle(Request req, Response resp) {
        String mediaId = "";
        if (noContent(req))
            resp.status(204);
        else {
            mediaId = dealWithContent(req, resp);
        }

        return mediaId;
    }

    private String dealWithContent(Request req, Response resp) {
        String mediaId = null;
        resp.status(201);
        Media m = transformator.toMediaPojo(req.body());
        if (!facade.urlStored(m.getUrl())) {
            mediaId = facade.storeMedia(m);
            resp.header("Location", "http://" + req.host() + req.pathInfo()
                    + "/" + mediaId);
        } else {
            resp.status(404);
        }

        return mediaId;
    }

    private boolean noContent(Request req) {
        return req.contentLength() < 1;
    }
}
