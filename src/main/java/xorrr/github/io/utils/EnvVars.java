package xorrr.github.io.utils;

public final class EnvVars {
    private EnvVars() {
    }

    public static final int MONGO_PORT = Integer.valueOf(System
            .getenv("MONGO_PORT"));
}
