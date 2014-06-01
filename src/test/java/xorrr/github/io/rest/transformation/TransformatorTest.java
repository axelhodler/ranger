package xorrr.github.io.rest.transformation;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private final String URL = "www.foo.org";

    private User createExampleUser() {
        User u = new User();
        u.setLogin("xorrr");
        u.setObjectId("5367f4f1bdef0dea21f040e7");
        return u;
    }

    private Media createExampleMedia(String url) {
        Media m = new Media(url);
        m.setAvgStartTime(20);
        m.setAvgEndTime(40);
        m.setChoicesByUsers(10);

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
        Media m = createExampleMedia(URL);

        assertEquals(t.toMediaJson(m), mapper.writeValueAsString(m));
    }

    @Test
    public void canTransformMediaJsonToPojo() throws JsonGenerationException,
            JsonMappingException, IOException {
        Media m = createExampleMedia(URL);

        String jsonMedia = mapper.writeValueAsString(m);

        assertEquals(m, t.toMediaPojo(jsonMedia));
    }

    @Test
    public void canTransformRangeToJson() throws JsonGenerationException,
            JsonMappingException, IOException {
        Range r = new Range(1, 2);

        String jsonRange = mapper.writeValueAsString(r);

        assertEquals(r, t.toRangePojo(jsonRange));
    }

    @Test
    public void canTransformJsonToRange() throws JsonGenerationException,
            JsonMappingException, IOException {
        Range r = new Range(1, 2);

        assertEquals(t.toRangeJson(r), mapper.writeValueAsString(r));
    }

    @Test
    public void canTransformMediaListToJson() throws JsonGenerationException,
            JsonMappingException, IOException {
        Media m1 = createExampleMedia(URL);
        Media m2 = createExampleMedia("www.bar.org");
        List<Media> medias = new ArrayList<>();
        medias.add(m1);
        medias.add(m2);

        String jsonMedias = t.toMediaJson(medias);

        assertEquals(jsonMedias, mapper.writeValueAsString(medias));
    }
}
