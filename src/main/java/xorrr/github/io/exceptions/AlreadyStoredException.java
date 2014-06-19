package xorrr.github.io.exceptions;

public class AlreadyStoredException extends Exception {

    private static final long serialVersionUID = -1291042716764690790L;

    public AlreadyStoredException(String mediaId, String userId) {
        super(String.format(
                "A range for mediaId: %s and userId: %s was already set, "
                        + " use modifyRange instead", mediaId, userId));
    }
}
