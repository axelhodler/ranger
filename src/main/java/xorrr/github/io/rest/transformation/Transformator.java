package xorrr.github.io.rest.transformation;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import xorrr.github.io.frontend.JsonFormatter;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.model.User;

import com.google.inject.Inject;

public class Transformator {

    private ObjectMapper mapper;
    private JsonFormatter compliance;

    @Inject
    public Transformator(JsonFormatter c) {
        mapper = new ObjectMapper();
        this.compliance = c;
    }

    public String toUserJson(User u) {
        String userJson = serializeUserToJson(u);
        return userJson;
    }

    public String toMediaJson(Media m) {
        String mediaJson = serializeMediaToJson(m);
        String compliantMediaJson = compliance.formatMedia(mediaJson);
        return compliantMediaJson;
    }

    public String toMediaListJson(List<Media> medias) {
        String mediaJson = serializeMediaToJson(medias);
        String compliantMediaJson = compliance.formatMediaList(mediaJson);
        return compliantMediaJson;
    }

    public String toRangeJson(Range r) {
        String jsonRange = serializeRangeToJson(r);
        return compliance.formatRange(jsonRange);
    }

    private String serializeRangeToJson(Range r) {
        String rangeJson = null;
        try {
            rangeJson = mapper.writeValueAsString(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rangeJson;
    }

    private String serializeUserToJson(User u) {
        String userJson = null;
        try {
            userJson = mapper.writeValueAsString(u);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userJson;
    }

    private String serializeMediaToJson(Media m) {
        String mediaJson = null;
        try {
            mediaJson = mapper.writeValueAsString(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaJson;
    }

    private String serializeMediaToJson(List<Media> medias) {
        String mediaJson = null;
        try {
            mediaJson = mapper.writeValueAsString(medias);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaJson;
    }

}
