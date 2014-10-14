package xorrr.github.io.utils;

public final class EnvVars {
    private EnvVars() {
    }

    public static final int MONGO_PORT = Integer.parseInt(System.getenv("MONGO_PORT"));
    public static final int PORT = Integer.parseInt(System.getenv("PORT"));
}
