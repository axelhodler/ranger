package xorrr.github.io.frontend.ember;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import xorrr.github.io.frontend.JsonCompliance;

public class EmberComplianceTest {

    private JsonCompliance compliance;
    private final String TEST_JSON = "{bla}";

    private String expectedFormatting(String object) {
        return "{" + object + ":" + TEST_JSON + "}";
    }

    @Before
    public void setUp() {
        compliance = new EmberCompliance();
    }

    @Test
    public void canComplyWithEmberJsonFormatting() {
        assertEquals(expectedFormatting(EmberJsonObjects.MEDIA),
                compliance.formatMediaList(TEST_JSON));
    }

    @Test
    public void canFormatMedia() {
        assertEquals(expectedFormatting(EmberJsonObjects.MEDIUM),
                compliance.formatMedia(TEST_JSON));
    }

    @Test
    public void canFormatRange() {
        assertEquals(expectedFormatting(EmberJsonObjects.RANGE),
                compliance.formatRange(TEST_JSON));
    }
}
