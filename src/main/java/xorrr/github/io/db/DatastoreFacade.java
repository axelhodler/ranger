package xorrr.github.io.db;

import xorrr.github.io.model.User;

public class DatastoreFacade {

    private UserDatastore ds;

    public DatastoreFacade(UserDatastore ds) {
        this.ds = ds;
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
}
