package xorrr.github.io.db;

import xorrr.github.io.model.Range;

public interface RangeDatastore {

    void addRange(Range r, String mediaId, String userId);

    Range getAverages(String mediaId);

    Range getRangeFor(String mediaId, String userId);
}
