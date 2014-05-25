package xorrr.github.io.db;

import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;

public interface MediaDatastore {

    String storeMedia(Media m);

    boolean applyRangeToMedia(String id, Range r);

    Media getMediaById(String id);

}
