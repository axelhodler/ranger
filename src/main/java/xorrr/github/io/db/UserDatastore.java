package xorrr.github.io.db;

import xorrr.github.io.model.User;

public interface UserDatastore {

    String storeUser(User u);

    User getUserById(String id);

    void deleteUserById(String id);

}
