package xorrr.github.io.db;

import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;

public interface MediaDatastore {

    void storeMedia(Media m);

    void addRangeToMedia(String id, Range r);

    Range getAverageRange(String id);

    Media getMediaById(String id);

}
