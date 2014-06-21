package xorrr.github.io.frontend.ember;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EmberComplianceTest {

    private EmberCompliance compliance;

    @Before
    public void setUp() {
        compliance = new EmberCompliance();
    }

    @Test
    public void canComplyWithEmberJsonFormatting() {
        String test = "{bla}";

        assertEquals("{" + EmberJsonObjects.MEDIA + ":" + test + "}",
                compliance.formatMediaList(test));
    }

    @Test
    public void canFormatMedia() {
        String test = "{bla}";

        assertEquals("{" + EmberJsonObjects.MEDIUM + ":" + test + "}",
                compliance.formatMedia(test));
    }
}
