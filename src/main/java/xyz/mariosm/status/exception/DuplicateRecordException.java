package xyz.mariosm.status.exception;

public class DuplicateRecordException extends RuntimeException {
    public DuplicateRecordException(String key, String val) {
        super(String.format("A record with the same %s, %s, was found in the database!", key, val));

    }
}
