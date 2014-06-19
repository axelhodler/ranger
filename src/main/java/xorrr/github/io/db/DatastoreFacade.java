package xorrr.github.io.db;

import java.util.List;

import xorrr.github.io.exceptions.AlreadyStoredException;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.model.User;

import com.google.inject.Inject;

public class DatastoreFacade {

    private UserDatastore uds;
    private MediaDatastore mds;
    private RangeDatastore rds;

    @Inject
    public DatastoreFacade(UserDatastore ds, MediaDatastore mds,
            RangeDatastore rds) {
        this.uds = ds;
        this.mds = mds;
        this.rds = rds;
    }

    public String storeUser(User u) {
        return uds.storeUser(u);
    }

    public User getUserById(String id) {
        return uds.getUserById(id);
    }

    public void deleteUserById(String id) {
        uds.deleteUserById(id);
    }

    public String storeMedia(Media m) {
        return mds.storeMedia(m);
    }

    public Media getMediaById(String id) {
        return mds.getMediaById(id);
    }

    public List<Media> getMedia() {
        return mds.getMedia();
    }

    public boolean urlStored(String url) {
        return mds.urlStored(url);
    }

    public Range getRange(String mediaId, String userId) {
        return rds.getRangeFor(mediaId, userId);
    }

    public Range getAverageRange(String mediaId) {
        return rds.getAverages(mediaId);
    }

    public String storeRange(Range r, String mediaId, String userId)
            throws AlreadyStoredException {
        return rds.storeRange(r, mediaId, userId);
    }

    public String modifyRange(Range r, String mediaId, String userId) {
        return rds.modifyRange(r, mediaId, userId);
    }
}
