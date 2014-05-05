package xorrr.github.io.rest.transformation;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import xorrr.github.io.model.User;

public class Transformator {

    private ObjectMapper mapper;

    public Transformator() {
        mapper = new ObjectMapper();
    }

    public String toJson(User u) throws JsonGenerationException,
            JsonMappingException, IOException {
        return mapper.writeValueAsString(u);
    }

    public User toUserPojo(String jsonUser) throws JsonParseException,
            JsonMappingException, IOException {
        return mapper.readValue(jsonUser, User.class);
    }

}
