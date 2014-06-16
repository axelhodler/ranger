package xorrr.github.io.db;

import java.util.List;

import xorrr.github.io.model.Media;

public interface MediaDatastore {

    String storeMedia(Media m);

    Media getMediaById(String id);

    List<Media> getMedia();

    boolean urlStored(String url);

}
