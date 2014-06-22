package xorrr.github.io.utils.logging;

public interface PutRangeRouteLogger {
    public void logNoRangePayloadProvided();

    public void logNoUserIdProvided();

    public void logRangeModified(String rangeId);
}
