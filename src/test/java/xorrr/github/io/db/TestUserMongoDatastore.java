package xorrr.github.io.db;

import org.junit.Test;

public class TestUserMongoDatastore {

    @Test
    public void canCreateUserDatastore() {
        UserDatastore ds = new UserMongoDatastore();
    }

}
