package xorrr.github.io.db;

import org.junit.Test;

import xorrr.github.io.model.User;

public class TestUserMongoDatastore {

    @Test
    public void canCreateUserDatastore() {
        UserDatastore ds = new UserMongoDatastore();
    }

    @Test
    public void canCreateUser() {
        User u = new User();
        u.setName("xorrr");
    }

}
