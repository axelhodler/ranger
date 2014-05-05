package xorrr.github.io.learning;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import xorrr.github.io.model.User;

public class JacksonLearningTest {

    @Test
    public void canTransform() throws JsonGenerationException,
            JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        User u = new User();
        u.setId("5367f4f1bdef0dea21f040e7");
        u.setName("xorrr");

        String jsonUser = mapper.writeValueAsString(u);

        assertEquals(u, mapper.readValue(jsonUser, User.class));
    }
}
