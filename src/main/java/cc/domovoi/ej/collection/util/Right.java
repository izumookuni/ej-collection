package cc.domovoi.ej.collection.util;

public final class Right<L, R> extends Either<L, R> {

    public Right(R right) {
        super();
        this._left = null;
        this._right = right;
    }

    public R get() {
        return this._right;
    }

    public static <L1, R1> Right<L1, R1> apply(R1 right) {
        return new Right<>(right);
    }

    @Override
    public Boolean isLeft() {
        return false;
    }

    @Override
    public Boolean isRight() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("Right(%s)", this._right);
    }
}
