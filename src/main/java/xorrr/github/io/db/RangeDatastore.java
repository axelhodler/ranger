package xorrr.github.io.db;

import xorrr.github.io.exceptions.AlreadyStoredException;
import xorrr.github.io.model.Range;

public interface RangeDatastore {

    String storeRange(Range r, String mediaId, String userId) throws AlreadyStoredException;

    String modifyRange(Range r, String mediaId, String userId);

    Range getAverages(String mediaId);

    Range getRangeFor(String mediaId, String userId);
}
