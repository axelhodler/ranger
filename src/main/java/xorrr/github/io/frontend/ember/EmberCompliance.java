package xorrr.github.io.frontend.ember;

import xorrr.github.io.frontend.JsonCompliance;

public class EmberCompliance implements JsonCompliance{

    @Override
    public String formatMediaList(String medias) {
        String formatted = "{\"medias\":" + medias + "}";
        return formatted;
    }

    @Override
    public String formatMedia(String media) {
        String formatted = "{\"media\":" + media + "}";
        return formatted;
    }
}
