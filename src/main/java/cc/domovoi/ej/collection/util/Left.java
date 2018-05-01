package cc.domovoi.ej.collection.util;

public final class Left<L, R> extends Either<L, R> {

    public Left(L left) {
        super();
        this._left = left;
        this._right = null;
    }

    public L get() {
        return this._left;
    }

    public static <L1, R1> Left<L1, R1> apply(L1 left) {
        return new Left<>(left);
    }

    @Override
    public Boolean isLeft() {
        return true;
    }

    @Override
    public Boolean isRight() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("Left(%s)", this._left.toString());
    }
}
