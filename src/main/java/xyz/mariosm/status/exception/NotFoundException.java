package xyz.mariosm.status.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String type, String key) {
        super(String.format("No %s of type %s was found!", key, type));
    }
}
