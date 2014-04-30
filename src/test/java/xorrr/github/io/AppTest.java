package xorrr.github.io;

import static org.junit.Assert.assertEquals;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class AppTest {

    private MongoClient client;
    private DBCollection users;
    private DBCollection media;

    private BasicDBObject createMediaDbo() {
        return new BasicDBObject(MediaCol.URL, "www.test.org")
        .append(MediaCol.RANGES, new ArrayList<BasicDBObject>());
    }

    private BasicDBObject createRangeDbo() {
        return new BasicDBObject(MediaCol.RANGES,
                new BasicDBObject(MediaCol.USER, "userid").append(
                        MediaCol.RANGE, new BasicDBObject(MediaCol.START_TIME,
                                "27:11").append(MediaCol.END_TIME, "28:22")));
    }

    @SuppressWarnings("unchecked")
    private List<DBObject> getRangesList() {
        return (List<DBObject>) media.findOne(
                new BasicDBObject(MediaCol.URL, "www.test.org")).get(
                        MediaCol.RANGES);
    }

    private DBObject getFirstRange(List<DBObject> dbo) {
        return (DBObject) dbo.get(0).get(MediaCol.RANGE);
    }

    @Before
    public void setUp() throws UnknownHostException {
        client = new MongoClient("localhost");
        users = client.getDB(RangerDB.NAME).getCollection(RangerDB.USER_COL);
        media = client.getDB(RangerDB.NAME).getCollection(RangerDB.MEDIA_COL);
    }

    @Test
    public void canCreateUser() throws Exception {
        DBObject userDbo = new BasicDBObject(UserCol.NAME, "pedro");

        users.insert(userDbo);

        assertEquals(userDbo, users.findOne(userDbo));
    }

    @Test
    public void canCreateMedia() throws Exception {
        DBObject mediaDbo = createMediaDbo();

        media.insert(mediaDbo);

        assertEquals(mediaDbo, media.findOne(mediaDbo));
    }


    @Test
    public void canUpdateMedia() throws Exception {
        DBObject mediaDbo = createMediaDbo();
        DBObject rangeDbo = createRangeDbo();
        DBObject rangeToPush = new BasicDBObject("$push", rangeDbo);

        media.insert(mediaDbo);
        media.update(new BasicDBObject(MediaCol.URL, "www.test.org"), rangeToPush);
        List<DBObject> dbo = getRangesList();
        DBObject foundRangeDbo = getFirstRange(dbo);

        assertEquals("27:11", foundRangeDbo.get(MediaCol.START_TIME));
        assertEquals("28:22", foundRangeDbo.get(MediaCol.END_TIME));
    }

    @After
    public void tearDown() {
        client.getDB(RangerDB.NAME).dropDatabase();
    }
}
