package xorrr.github.io.rest.transformation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import xorrr.github.io.frontend.JsonCompliance;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.model.User;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class TransformatorTest {

    @Mock
    JsonCompliance compliance;

    private Transformator transformator;
    private ObjectMapper mapper;
    private final String URL = "www.foo.org";

    private Range range;

    private User createExampleUser() {
        User u = new User();
        u.setLogin("xorrr");
        u.setObjectId("5367f4f1bdef0dea21f040e7");
        return u;
    }

    private Media createExampleMedia(String url) {
        Media m = new Media(url);

        return m;
    }

    private List<Media> createSampleMediaList() {
        Media m1 = createExampleMedia(URL);
        Media m2 = createExampleMedia("www.bar.org");
        List<Media> medias = new ArrayList<>();
        medias.add(m1);
        medias.add(m2);
        return medias;
    }

    private void mockMediaListTransformation() {
        when(compliance.formatMediaList(anyString())).thenReturn("formatted");
    }

    private String transform(List<Media> medias) throws IOException,
            JsonGenerationException, JsonMappingException {
        return mapper.writeValueAsString(medias);
    }

    @Before
    public void setUp() {
        transformator = new Transformator(compliance);
        mapper = new ObjectMapper();
        range = new Range(1, 2);
    }

    @Test
    public void canTransformToJson() throws JsonGenerationException,
            JsonMappingException, IOException {
        User u = createExampleUser();

        assertEquals(transformator.toUserJson(u), mapper.writeValueAsString(u));
    }

    @Test
    public void canTransformMediaToJson() throws JsonGenerationException,
            JsonMappingException, IOException {
        Media m = createExampleMedia(URL);
        when(compliance.formatMedia(anyString())).thenReturn("formatted");

        String mediaJson = transformator.toMediaJson(m);

        verify(compliance, times(1)).formatMedia(mapper.writeValueAsString(m));
        assertEquals("formatted", mediaJson);
    }

    @Test
    public void canTransformRangeToJson() throws JsonGenerationException,
            JsonMappingException, IOException {
        String expectedInner = mapper.writeValueAsString(range);
        when(compliance.formatRange(expectedInner)).thenReturn("formatted");

        assertEquals("formatted", transformator.toRangeJson(range));
        verify(compliance, times(1)).formatRange(expectedInner);
    }

    @Test
    public void canTransformMediaListToJson() throws JsonGenerationException,
            JsonMappingException, IOException {
        List<Media> medias = createSampleMediaList();
        mockMediaListTransformation();

        String jsonMedias = transformator.toMediaListJson(medias);

        verify(compliance, times(1)).formatMediaList(transform(medias));
        assertEquals("formatted", jsonMedias);
    }

}
