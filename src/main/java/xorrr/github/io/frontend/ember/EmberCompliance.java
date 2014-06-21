package xorrr.github.io.frontend.ember;

import xorrr.github.io.frontend.JsonCompliance;

public class EmberCompliance implements JsonCompliance {

    @Override
    public String formatMediaList(String media) {
        String formatted = "{" + EmberJsonObjects.MEDIA + ":" + media + "}";
        return formatted;
    }

    @Override
    public String formatMedia(String medium) {
        String formatted = "{" + EmberJsonObjects.MEDIUM + ":" + medium + "}";
        return formatted;
    }
}
