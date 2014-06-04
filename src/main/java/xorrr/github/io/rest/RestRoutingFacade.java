package xorrr.github.io.rest;

import xorrr.github.io.rest.routes.media.GETmediaByIdRoute;
import xorrr.github.io.rest.routes.media.GETmediaRoute;
import xorrr.github.io.rest.routes.media.POSTmediaRoute;
import xorrr.github.io.rest.routes.media.PUTmediaRoute;
import xorrr.github.io.rest.routes.user.POSTuserRoute;

public interface RestRoutingFacade {
    public void setGetMediaByIdRoute(GETmediaByIdRoute getMediaById);

    public void setPostMediaRoute(POSTmediaRoute postMedia);

    public void setPutRangeToMediaRoute(PUTmediaRoute putRangeToMedia);

    public void setWildcardRoutes();

    public void setPostUserRoute(POSTuserRoute postUser);

    public void setGetMediaRoute(GETmediaRoute getMedia);
}
