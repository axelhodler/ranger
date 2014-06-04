package xorrr.github.io.rest.transformation;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import xorrr.github.io.frontend.JsonCompliance;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.model.User;

import com.google.inject.Inject;

public class Transformator {

    private ObjectMapper mapper;
    private JsonCompliance compliance;

    @Inject
    public Transformator(JsonCompliance c) {
        mapper = new ObjectMapper();
        this.compliance = c;
    }

    public String toUserJson(User u) {
        String userJson = serializeUserToJson(u);
        return userJson;
    }

    public User toUserPojo(String jsonUser) {
        User user = deserializeJsonToUser(jsonUser);
        return user;
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

    public Media toMediaPojo(String jsonMedia) {
        Media media = deserializeJsonToMedia(jsonMedia);
        return media;
    }

    public Range toRangePojo(String jsonRange) {
        Range range = deserializeRange(jsonRange);
        return range;
    }

    public String toRangeJson(Range r) {
        String rangeJson = null;
        rangeJson = serializeRangeToJson(r);
        return rangeJson;
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

    private Range deserializeRange(String jsonRange) {
        Range range = null;
        try {
            range = mapper.readValue(jsonRange, Range.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return range;
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

    private User deserializeJsonToUser(String jsonUser) {
        User user = null;
        try {
            user = mapper.readValue(jsonUser, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
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

    private Media deserializeJsonToMedia(String jsonMedia) {
        Media media = null;
        try {
            media = mapper.readValue(jsonMedia, Media.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return media;
    }


}
