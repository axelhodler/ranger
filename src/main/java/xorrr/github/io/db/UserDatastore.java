package xorrr.github.io.db;

import xorrr.github.io.model.Range;
import xorrr.github.io.model.User;

public interface UserDatastore {

    String storeUser(User u);

    User getUserById(String id);

    void deleteUserById(String id);

    void modifyRanges(String userId, String mediaId, Range r);

}
