package brobot;

public final class FileIoStatus {

    private final String message;
    private final boolean isSuccess;
    private FileIoStatus(final String message, final boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public static FileIoStatus makeSuccessStatus(final String message) {
        return new FileIoStatus(message, true);
    }

    public static FileIoStatus makeFailureStatus(final String message) {
        return new FileIoStatus(message, false);
    }

    public boolean checkIfSuccess() {
        return isSuccess;
    }

    public boolean checkIfFailure() {
        return !checkIfSuccess();
    }

    @Override
    public String toString() {
        return message;
    }
}
