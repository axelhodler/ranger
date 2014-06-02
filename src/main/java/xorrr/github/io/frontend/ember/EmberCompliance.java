package xorrr.github.io.frontend.ember;

public class EmberCompliance {

    public static String formatMediaList(String medias) {
        String formatted = "{\"medias\":" + medias + "}";
        return formatted;
    }

    public static String formatMedia(String media) {
        String formatted = "{\"media\":" + media + "}";
        return formatted;
    }
}
