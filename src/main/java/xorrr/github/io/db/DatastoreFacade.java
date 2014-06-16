package xorrr.github.io.db;

import java.util.List;

import xorrr.github.io.model.Media;
import xorrr.github.io.model.User;

import com.google.inject.Inject;

public class DatastoreFacade {

    private UserDatastore uds;
    private MediaDatastore mds;

    @Inject
    public DatastoreFacade(UserDatastore ds, MediaDatastore mds) {
        this.uds = ds;
        this.mds = mds;
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
}
