package cc.domovoi.collection.util;

/**
 * @param <T> Element type of this Try
 */
public final class Failure<T> extends Try<T> {

    public Failure(Exception exception) {
        super();
        this._exception = exception;
        this._value = null;
    }

    public Failure(String message) {
        super();
        this._exception = new RuntimeException(message);
        this._value = null;
    }

    @Override
    public Boolean isFailure() {
        return true;
    }

    @Override
    public Boolean isSuccess() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("Failure(%s)", this._exception.toString());
    }
}
