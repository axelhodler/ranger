package xorrr.github.io.db;

import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.model.User;

public class DatastoreFacade {

    private UserDatastore ds;
    private MediaDatastore mds;

    public DatastoreFacade(UserDatastore ds, MediaDatastore mds) {
        this.ds = ds;
        this.mds = mds;
    }

    public void storeUser(User u) {
        ds.storeUser(u);
    }

    public User getUserById(String id) {
        return ds.getUserById(id);
    }

    public void deleteUserById(String id) {
        ds.deleteUserById(id);
    }

    public String storeMedia(Media m) {
        return mds.storeMedia(m);
    }

    public boolean applyRangeToMedia(String id, Range r) {
        return mds.applyRangeToMedia(id, r);
    }

    public Media getMediaById(String id) {
        return mds.getMediaById(id);
    }

    public void modifyRanges(String userId, String mediaId, Range r) {
        ds.modifyRanges(userId, mediaId, r);
    }
}
