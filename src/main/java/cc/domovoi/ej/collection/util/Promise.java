package cc.domovoi.ej.collection.util;

import java.util.concurrent.CompletableFuture;

public class Promise<T> {

    private CompletableFuture<T> _future;

    private Boolean _completedFlag;

    public Promise() {
        this._future = new CompletableFuture<>();
        this._completedFlag = false;
    }

    public Promise<T> complete(Try<T> result) {
        if (tryComplete(result)) {
            return this;
        }
        else {
            throw new IllegalStateException("Promise already completed.");
        }
    }

    public Promise<T> completeWith(CompletableFuture<T> other) {
        return tryCompleteWith(other);
    }

    public Promise<T> failure(Throwable cause) {
        return complete(new Failure<>(cause));
    }

    public CompletableFuture<T> future() {
        return _future;
    }

    public Boolean isCompleted() {
        return _completedFlag;
    }

    public Promise<T> success(T value) {
        return complete(new Success<>(value));
    }

    public Boolean tryComplete(Try<T> result) {
        if (_completedFlag) {
            return false;
        }
        else {
            if (result.isSuccess()) {
                _future.complete(result.get());
            }
            else {
                _future.completeExceptionally(result.failed().get());
            }
            _completedFlag = true;
            return true;
        }
    }

    public Promise<T> tryCompleteWith(CompletableFuture<T> other) {
        if (!_completedFlag) {
            other.whenComplete((result, cause) -> {
               if (cause == null) {
                   _future.complete(result);
               }
               else {
                   _future.completeExceptionally(cause);
               }
               _completedFlag = true;
            });
        }
        return this;
    }

    public Boolean tryFailure(Throwable cause) {
        return tryComplete(new Failure<>(cause));
    }

    public Boolean trySuccess(T value) {
        return tryComplete(new Success<>(value));
    }

    public static <T1> Promise<T1> apply() {
        return new Promise<>();
    }

    public static <T1> Promise<T1> failed(Throwable throwable) {
        return new Promise<T1>().failure(throwable);
    }

    public static <T1> Promise<T1> fromTry(Try<T1> result) {
        return new Promise<T1>().complete(result);
    }

    public static <T1> Promise<T1> successful(T1 result) {
        return new Promise<T1>().success(result);
    }

}
