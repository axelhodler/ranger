package xorrr.github.io.rest;


public interface RestHelperFacade {
    public void setPort(int port);

    public void stopRequest(int statusCode, String message);
}
