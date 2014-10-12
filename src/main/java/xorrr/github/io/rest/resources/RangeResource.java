package xorrr.github.io.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.exceptions.AlreadyStoredException;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.Paths;
import xorrr.github.io.rest.transformation.Transformator;
import xorrr.github.io.utils.HttpHeaderKeys;

import com.google.inject.Inject;

@Path(Paths.RANGES)
@Produces(MediaType.APPLICATION_JSON)
public class RangeResource {

    private DatastoreFacade dataStore;
    private Transformator transformator;

    @Inject
    public RangeResource(DatastoreFacade dsFacade, Transformator trans) {
        this.dataStore = dsFacade;
        this.transformator = trans;
    }

    @GET
    @Path("{mediaId}")
    public Response getAvergageRange(@PathParam("mediaId") final String mediaId) {
        Range r = dataStore.getAverageRange(mediaId);

        String range = transformator.toRangeJson(r);

        return Response.ok().entity(range).header(HttpHeaderKeys.ACAOrigin, "*").build();
    }

    @POST
    @Path("{mediaId}")
    public Response addRange(@PathParam("mediaId") final String mediaId, final Range range) {
        try {
            dataStore.storeRange(range, mediaId, "");
        } catch (AlreadyStoredException e) {
            e.printStackTrace();
        }

        return null;
    }

    @PUT
    @Path("/")
    public Response changeRange(Range range) {
        return null;
    }
}
