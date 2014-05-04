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

    public void getUserById(String id) {
        ds.getUserById(id);
    }

    public void deleteUserById(String id) {
        ds.deleteUserById(id);
    }

    public void storeMedia(Media m) {
        mds.storeMedia(m);
    }

    public void addRangeToMedia(String id, Range r) {
        mds.addRangeToMedia(id, r);
    }

    public void getAverageRangeFor(String id) {
        mds.getAverageRange(id);
    }
}
