package xorrr.github.io.utils;

import javax.ws.rs.core.Response;

public class TestHelpers {

    public static Object getSameOriginHeader(Response resp) {
        return resp.getMetadata().get(HttpHeaderKeys.ACAOrigin).get(0);
    }
}
