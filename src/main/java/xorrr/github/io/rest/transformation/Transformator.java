package xorrr.github.io.rest.transformation;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import xorrr.github.io.model.Media;
import xorrr.github.io.model.User;

public class Transformator {

    private ObjectMapper mapper;

    public Transformator() {
        mapper = new ObjectMapper();
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
        return mediaJson;
    }

    public Media toMediaPojo(String jsonMedia) {
        Media media = null;
        media = deserializeJsonToMedia(jsonMedia, media);
        return media;
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

    private Media deserializeJsonToMedia(String jsonMedia, Media media) {
        try {
            media = mapper.readValue(jsonMedia, Media.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return media;
    }
}
