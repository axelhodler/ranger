package xorrr.github.io.rest.transformation;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
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

    private Media createExampleMedia() {
        Media m = new Media("www.url.org");
        Range r = new Range(60, 80);
        m.addRange(r);
        return m;
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

        assertEquals(t.toUserJson(u), mapper.writeValueAsString(u));
    }

    @Test
    public void canTransformToPojo() throws JsonGenerationException,
            JsonMappingException, IOException {
        User u = createExampleUser();

        String jsonUser = mapper.writeValueAsString(u);

        assertEquals(u, t.toUserPojo(jsonUser));
    }

    @Test
    public void canTransformMediaToJson() throws JsonGenerationException,
            JsonMappingException, IOException {
        Media m = createExampleMedia();

        assertEquals(t.toMediaJson(m), mapper.writeValueAsString(m));
    }

    @Test
    public void canTransformMediaJsonToPojo() throws JsonGenerationException,
            JsonMappingException, IOException {
        Media m = createExampleMedia();

        String jsonMedia = mapper.writeValueAsString(m);

        assertEquals(m, t.toMediaPojo(jsonMedia));
    }
}
