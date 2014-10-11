package xorrr.github.io.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import xorrr.github.io.model.Range;
import xorrr.github.io.rest.Paths;

@Path(Paths.RANGES)
@Produces(MediaType.APPLICATION_JSON)
public class RangeResource {

    @GET
    @Path("{id}")
    public Response getRange(@PathParam("id") final String id) {
       return null; 
    }

    @POST
    @Path("/")
    public Response addRange(Range range) {
        return null;
    }

    @PUT
    @Path("/")
    public Response changeRange(Range range) {
        return null;
    }
}
