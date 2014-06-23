package xorrr.github.io.frontend.ember;

import xorrr.github.io.frontend.JsonCompliance;

public class EmberCompliance implements JsonCompliance {

    @Override
    public String formatMediaList(String media) {
        return format(media, EmberJsonObjects.MEDIA);
    }

    @Override
    public String formatMedia(String medium) {
        return format(medium, EmberJsonObjects.MEDIUM);
    }

    @Override
    public String formatRange(String range) {
        return format(range, EmberJsonObjects.RANGE);
    }

    private String format(String toFormat, String outerObject) {
        return "{" + outerObject + ":" + toFormat + "}";
    }
}
