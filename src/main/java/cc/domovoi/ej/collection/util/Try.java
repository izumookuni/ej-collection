package cc.domovoi.ej.collection.util;

import cc.domovoi.ej.collection.tuple.Product;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Try<T> extends Product implements Serializable {

    protected T _value;

    protected Throwable _exception;

    public abstract Boolean isFailure();

    public abstract Boolean isSuccess();

    public static <T1> Try<T1> apply(Supplier<T1> supplier) {
        try {
            return new Success<>(supplier.get());
        } catch (Throwable failure) {
            return new Failure<>(failure);
        }
    }

    public Try<Throwable> failed() {
        if (isFailure()) {
            return new Success<>(this._exception);
        }
        else {
            return new Failure<>(new NoSuchElementException("This object isn't instance of Failure"));
        }
    }

    public Try<T> filter(Predicate<T> p) {
        if (isSuccess() && !p.test(this._value)) {
            return new Failure<>(new AssertionError("Predicate Failure"));
        }
        else {
            return this;
        }
    }

    public Try<T> filterNot(Predicate<T> p) {
        return filter(p.negate());
    }

    public <U> Try<U> flatMap(Function<T, Try<U>> f) {
        if (isSuccess()) {
            return f.apply(this._value);
        }
        else {
            return new Failure<>(this._exception);
        }
    }

    public static <U> Try<U> flatten(Try<Try<U>> t) {
        return t.flatMap(Function.identity());
//        if (t.isSuccess()) {
//            return t._value;
//        }
//        else {
//            return new Failure<>(t._exception);
//        }
    }

    public <U> U fold(Function<Throwable, U> fa, Function<T, U> fb) {
        if (isSuccess()) {
            return fb.apply(this._value);
        }
        else {
            return fa.apply(this._exception);
        }
    }

    public void foreach(Consumer<T> f) {
        if (isSuccess()) {
            f.accept(this._value);
        }
    }

    public T get() {
        if (isSuccess()) {
            return this._value;
        }
        else {
            throw new NoSuchElementException("This object isn't instance of Success");
        }
    }

    public T getOrElse(Supplier<T> zero) {
        if (isSuccess()) {
            return this._value;
        }
        else {
            return zero.get();
        }
    }

    public <U> Try<U> map(Function<T, U> f) {
        if (isSuccess()) {
            return Try.apply(() -> f.apply(this._value));
//            return new Success<>(f.apply(this._value));
        }
        else {
            return new Failure<>(this._exception);
        }
    }

    public Try<T> orElse(Supplier<Try<T>> zero) {
        if (isSuccess()) {
            return this;
        }
        else {
            return zero.get();
        }
    }

    public Try<T> recover(Function<Throwable, T> f) {
        if (isFailure()) {
            return Try.apply(() -> f.apply(this._exception));
        }
        else {
            return this;
        }
    }

    public Try<T> recoverWith(Function<Throwable, Try<T>> f) {
        if (isFailure()) {
            return f.apply(this._exception);
        }
        else {
            return this;
        }
    }

    public Either<Throwable, T> toEither() {
        if (isSuccess()) {
            return new Right<>(this._value);
        }
        else {
            return new Left<>(this._exception);
        }
    }

    public Optional<T> toOption() {
        if (isSuccess()) {
            return Optional.of(this._value);
        }
        else {
            return Optional.empty();
        }
    }

    public <U> Try<U> transform(Function<T, Try<U>> s, Function<Throwable, Try<U>> f) {
        if (isSuccess()) {
            return s.apply(this._value);
        }
        else {
            return f.apply(this._exception);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(113, 117).append(_value).append(_exception).toHashCode();
    }

    @Override
    public Integer productArity() {
        if (isFailure()) {
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public Object productElement(Integer n) {
        if (isSuccess() && n == 0) {
            return this._value;
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public List<Object> productCollection() {
        if(isSuccess()) {
            return Collections.singletonList(this._value);
        }
        else {
            return Collections.emptyList();
        }
    }
}
