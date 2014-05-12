package xorrr.github.io.db;

import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;

public interface MediaDatastore {

    void storeMedia(Media m);

    void applyRangeToMedia(String id, Range r);

    Media getMediaById(String id);

}
