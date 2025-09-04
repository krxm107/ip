package brobot;

public final class FileIOStatus {

    private final String message;
    private final boolean isSuccess;
    private FileIOStatus(final String message, final boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public static FileIOStatus makeSuccessStatus(final String message) {
        return new FileIOStatus(message, true);
    }

    public static FileIOStatus makeFailureStatus(final String message) {
        return new FileIOStatus(message, false);
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
