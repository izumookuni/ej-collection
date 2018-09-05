package cc.domovoi.ej.collection.util;

import cc.domovoi.ej.collection.tuple.Product;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Either<L, R> extends Product implements Serializable {

    protected L _left;

    protected R _right;

    public abstract Boolean isLeft();

    public abstract Boolean isRight();

    public Boolean contains(R r) {
        return isRight() && r.equals(this._right);
    }

    public Boolean exists(Predicate<? super R> p) {
        return isRight() && p.test(this._right);
    }

    public Either<L, R> filterOrElse(Predicate<? super R> p, Supplier<? extends L> zero) {
        if ((isRight() && p.test(_right)) || isLeft()) {
            return this;
        }
        else {
            return new Left<>(zero.get());
        }
    }

    public <R1> Either<L, R1> flatMap(Function<? super R, ? extends Either<L, R1>> f) {
        if (isRight()) {
            return f.apply(this._right);
        }
        else {
            return new Left<>(this._left);
        }
    }

    public <T> T fold(Function<? super L, ? extends T> fl, Function<? super R, ? extends T> fr) {
        if (isRight()) {
            return fr.apply(this._right);
        }
        else {
            return fl.apply(this._left);
        }
    }

    public Boolean forall(Predicate<? super R> p) {
        return (isRight() && p.test(this._right)) || isLeft();
    }

    public void foreach(Consumer<? super R> f) {
        if (isRight()) {
            f.accept(this._right);
        }
    }

    public R getOrElse(Supplier<? extends R> zero) {
        if (isRight()) {
            return this._right;
        }
        else {
            return zero.get();
        }
    }

    public static <L1, R1> Either<L1, R1> joinLeft(Either<Either<L1, R1>, R1> either) {
        if (either.isLeft() && either._left.isLeft()) {
            return new Left<>(either._left._left);
        }
        else if (either.isLeft() && either._left.isRight()) {
            return new Right<>(either._left._right);
        }
        else {
            return new Right<>(either._right);
        }
    }

    public static <L1, R1> Either<L1, R1> joinRight(Either<L1, Either<L1, R1>> either) {
        if (either.isRight() && either._right.isLeft()) {
            return new Left<>(either._right._left);
        }
        else if (either.isRight() && either._right.isRight()) {
            return new Right<>(either._right._right);
        }
        else {
            return new Left<>(either._left);
        }
    }

    public Left<L, R> left() {
        if (isLeft()) {
            return (Left<L, R>) this;
        }
        else {
            throw new ClassCastException("This object isn't instance of Left");
        }
    }

    public Right<L, R> right() {
        if (isRight()) {
            return (Right<L, R>) this;
        }
        else {
            throw new ClassCastException("This object isn't instance of Right");
        }
    }

    public <T> Either<L, T> map(Function<? super R, ? extends T> f) {
        if (isRight()) {
            return new Right<>(f.apply(this._right));
        }
        else {
            return new Left<>(this._left);
        }
    }

    public Either<R, L> swap() {
        if (isLeft()) {
            return new Right<>(this._left);
        }
        else {
            return new Left<>(this._right);
        }
    }

    public Optional<R> toOptional() {
        if (isRight()) {
            return Optional.of(this._right);
        }
        else {
            return Optional.empty();
        }
    }

    public Option<R> toOption() {
        if (isRight()) {
            return Some.apply(this._right);
        }
        else {
            return None.unit();
        }
    }

    public List<R> toList() {
        if (isRight()) {
            return Collections.singletonList(this._right);
        }
        else {
            return Collections.emptyList();
        }
    }

    public Try<R> toTry() {
        if (isRight()) {
            return new Success<>(this._right);
        }
        else {
            return new Failure<>(new ClassCastException("This object isn't instance of Right"));
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(113, 115).append(_left).append(_right).toHashCode();
    }

    @Override
    public Integer productArity() {
        return 1;
    }

    @Override
    public Object productElement(Integer n) {
        if (n == 0) {
            if (isRight()) {
                return _right;
            }
            else {
                return _left;
            }
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public List<Object> productCollection() {
        return Arrays.asList(this._left, this._right);
    }
}
