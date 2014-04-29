package xorrr.github.io;

import static org.junit.Assert.assertEquals;

import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class AppTest {

    private MongoClient client;

    @Before
    public void setUp() throws UnknownHostException {
        client = new MongoClient("localhost");
    }

    @Test
    public void canCreateUser() throws Exception {
        DBObject userDbo = new BasicDBObject(UserCol.NAME, "pedro");

        client.getDB(RangerDB.NAME).getCollection(RangerDB.USER_COL)
                .insert(userDbo);

        assertEquals(userDbo,
                client.getDB(RangerDB.NAME).getCollection(RangerDB.USER_COL)
                        .findOne(userDbo));
    }

    @After
    public void tearDown() {
        client.getDB(RangerDB.NAME).dropDatabase();
    }
}
