package xorrr.github.io.rest.transformation;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import xorrr.github.io.model.User;

public class TransformatorTest {

    private Transformator t;
    private ObjectMapper mapper;

    private User createExampleUser() {
        User u = new User();
        u.setName("xorrr");
        u.setId("5367f4f1bdef0dea21f040e7");
        return u;
    }

    @Before
    public void setUp() {
        t = new Transformator();
        mapper = new ObjectMapper();
    }

    @Test
    public void canTransformToJson() throws JsonGenerationException,
            JsonMappingException, IOException {
        User u = createExampleUser();

        assertEquals(t.toJson(u), mapper.writeValueAsString(u));
    }

    @Test
    public void canTransformToPojo() throws JsonGenerationException,
            JsonMappingException, IOException {
        User u = createExampleUser();

        String jsonUser = mapper.writeValueAsString(u);

        assertEquals(u, t.toUserPojo(jsonUser));
    }
}
