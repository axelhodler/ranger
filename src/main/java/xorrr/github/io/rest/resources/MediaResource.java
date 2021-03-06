package xorrr.github.io.rest.resources;

import io.dropwizard.auth.Auth;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.User;
import xorrr.github.io.rest.Paths;
import xorrr.github.io.rest.transformation.Transformator;
import xorrr.github.io.utils.HttpHeaderKeys;

import com.google.inject.Inject;

@Path(Paths.MEDIA)
@Produces(MediaType.APPLICATION_JSON)
public class MediaResource {

    private DatastoreFacade facade;
    private Transformator transformator;

    @Inject
    public MediaResource(DatastoreFacade f, Transformator t) {
        this.facade = f;
        this.transformator = t;
    }

    @GET
    @Path("{id}")
    public Response getMediaById(@PathParam("id") final String id) {
        Response resp = null;

        Media m = facade.getMediaById(id);

        if (m == null)
            resp = Response.status(Status.NOT_FOUND).header(HttpHeaderKeys.ACAOrigin, "*").build();
        else {
            String returnMsg = transformator.toMediaJson(m);
            resp = Response.ok().header(HttpHeaderKeys.ACAOrigin, "*").entity(returnMsg).build();
        }

        return resp;
    }

    @GET
    @Path("/")
    public Response getMedias(@Auth User user) {
        return Response.ok().entity(transformator.toMediaListJson(facade.getMedia())).header(HttpHeaderKeys.ACAOrigin,
                "*").build();
    }

    @POST
    @Path("/")
    public Response addMedia(@Context UriInfo uriInfo, Media m) throws URISyntaxException {
        Response resp = null;

        if (facade.urlStored(m.getUrl())) {
            resp = Response.status(Status.NOT_FOUND).build();
        } else {
            String mediaId = facade.storeMedia(m);

            resp = Response.created(createLocationHeader(uriInfo, mediaId)).build();
        }

        return resp;
    }

    private URI createLocationHeader(UriInfo uriInfo, String mediaId) throws URISyntaxException {
        return new URI(
                String.format("%s/%s", uriInfo.getAbsolutePath().toString(), mediaId));
    }
}
