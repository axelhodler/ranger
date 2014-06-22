package xorrr.github.io.rest.routes;

public final class MappedRoutes {
    private MappedRoutes() {}
    private static String ID = "/:id";
    public static final String MEDIA = "/media";
    public static final String MEDIA_BY_ID = MEDIA + ID;
    public static final String USERS = "/users";
    public static final String RANGE = "/ranges";
    public static final String RANGE_FOR_MEDIAID = RANGE + ID;
}
