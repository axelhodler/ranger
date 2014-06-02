package xorrr.github.io.frontend.ember;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EmberComplianceTest {

    @Test
    public void canComplyWithEmberJsonFormatting() {
        String test = "{bla}";

        assertEquals("{\"medias\":" + test + "}",
                EmberCompliance.formatMediaList(test));
    }

    @Test
    public void canFormatMedia() {
        String test = "{bla}";

        assertEquals("{\"media\":" + test + "}",
                EmberCompliance.formatMedia(test));
    }
}
