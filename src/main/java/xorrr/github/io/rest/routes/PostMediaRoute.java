package xorrr.github.io.rest.routes;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.rest.transformation.Transformator;

public class PostMediaRoute implements Route {

    private DatastoreFacade facade;
    private Transformator transformator;

    public PostMediaRoute(DatastoreFacade facade, Transformator transformator) {
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
        String mediaId;
        resp.status(201);
        mediaId = facade.storeMedia(createMediaFromRequestBody(req));
        resp.header("Location", "http://" + req.host() + req.pathInfo()
                + "/" + mediaId);
        return mediaId;
    }

    private boolean noContent(Request req) {
        return req.contentLength() < 1;
    }

    private Media createMediaFromRequestBody(Request req) {
        return transformator.toMediaPojo(req.body());
    }
}
