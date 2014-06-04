package xorrr.github.io.rest.transformation;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import xorrr.github.io.frontend.JsonCompliance;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.model.User;

public class Transformator {

    private ObjectMapper mapper;
    private JsonCompliance compliance;

    public Transformator(JsonCompliance c) {
        mapper = new ObjectMapper();
        this.compliance = c;
    }

    public String toUserJson(User u) {
        String userJson = null;
        userJson = serializeUserToJson(u, userJson);
        return userJson;
    }

    public User toUserPojo(String jsonUser) {
        User user = null;
        user = deserializeJsonToUser(jsonUser, user);
        return user;
    }

    public String toMediaJson(Media m) {
        String mediaJson = null;
        mediaJson = serializeMediaToJson(m, mediaJson);
        String compliantMediaJson = compliance.formatMedia(mediaJson);
        return compliantMediaJson;
    }

    public String toMediaJson(List<Media> medias) {
        String mediaJson = null;
        mediaJson = serializeMediaToJson(medias, mediaJson);
        String compliantMediaJson = compliance.formatMediaList(mediaJson);
        return compliantMediaJson;
    }

    public Media toMediaPojo(String jsonMedia) {
        Media media = null;
        media = deserializeJsonToMedia(jsonMedia, media);
        return media;
    }

    public Range toRangePojo(String jsonRange) {
        Range range = null;
        range = deserializeRange(jsonRange, range);
        return range;
    }

    public String toRangeJson(Range r) {
        String rangeJson = null;
        rangeJson = serializeRangeToJson(r, rangeJson);
        return rangeJson;
    }

    private String serializeRangeToJson(Range r, String rangeJson) {
        try {
            rangeJson = mapper.writeValueAsString(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rangeJson;
    }

    private Range deserializeRange(String jsonRange, Range range) {
        try {
            range = mapper.readValue(jsonRange, Range.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return range;
    }

    private String serializeUserToJson(User u, String userJson) {
        try {
            userJson = mapper.writeValueAsString(u);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userJson;
    }

    private User deserializeJsonToUser(String jsonUser, User user) {
        try {
            user = mapper.readValue(jsonUser, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    private String serializeMediaToJson(Media m, String mediaJson) {
        try {
            mediaJson = mapper.writeValueAsString(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaJson;
    }

    private String serializeMediaToJson(List<Media> medias, String mediaJson) {
        try {
            mediaJson = mapper.writeValueAsString(medias);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaJson;
    }

    private Media deserializeJsonToMedia(String jsonMedia, Media media) {
        try {
            media = mapper.readValue(jsonMedia, Media.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return media;
    }


}
