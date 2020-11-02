package bts.KCamps.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String id, Class clazz) {
        super(String.format("Not found %s by id %s", clazz.getName(), id));
    }
}
