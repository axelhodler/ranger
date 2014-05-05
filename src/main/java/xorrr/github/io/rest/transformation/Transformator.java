package xorrr.github.io.rest.transformation;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import xorrr.github.io.model.Media;
import xorrr.github.io.model.User;

public class Transformator {

    private ObjectMapper mapper;

    public Transformator() {
        mapper = new ObjectMapper();
    }

    public String toUserJson(User u) throws JsonGenerationException,
            JsonMappingException, IOException {
        return mapper.writeValueAsString(u);
    }

    public User toUserPojo(String jsonUser) throws JsonParseException,
            JsonMappingException, IOException {
        return mapper.readValue(jsonUser, User.class);
    }

    public String toMediaJson(Media m) throws JsonGenerationException,
            JsonMappingException, IOException {
        return mapper.writeValueAsString(m);
    }

    public Media toMediaPojo(String jsonMedia) throws JsonParseException,
            JsonMappingException, IOException {
        return mapper.readValue(jsonMedia, Media.class);
    }

}
