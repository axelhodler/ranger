package xorrr.github.io.rest.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import xorrr.github.io.model.User;
import xorrr.github.io.rest.Paths;

@Path(Paths.USERS)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @POST
    @Path("/")
    public Response createUser(User u) {
        return null;
    }
}
