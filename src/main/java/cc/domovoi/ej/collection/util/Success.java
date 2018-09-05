package cc.domovoi.ej.collection.util;

public final class Success<T> extends Try<T> {

    public Success(T success) {
        super();
        this._exception = null;
        this._value = success;
    }

    @Override
    public Boolean isFailure() {
        return false;
    }

    @Override
    public Boolean isSuccess() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("Success(%s)", this._value);
    }
}
