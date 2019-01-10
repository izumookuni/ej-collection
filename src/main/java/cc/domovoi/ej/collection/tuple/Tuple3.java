package cc.domovoi.ej.collection.tuple;

import cc.domovoi.ej.collection.util.Option;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A tuple of 3 elements.
 * @param <T1> Element 1 type of this Tuple3
 * @param <T2> Element 2 type of this Tuple3
 * @param <T3> Element 3 type of this Tuple3
 */
public final class Tuple3<T1, T2, T3> extends Product implements Serializable {

    private T1 _1;

    private T2 _2;

    private T3 _3;

    public T1 _1() {
        return this._1;
    }

    public T2 _2() {
        return this._2;
    }

    public T3 _3() {
        return this._3;
    }

    public Tuple3(T1 _1, T2 _2, T3 _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    public Tuple3<T1, T2, T3> copy(Option<T1> _1, Option<T2> _2, Option<T3> _3) {
        return new Tuple3<>(_1.getOrElse(() -> this._1), _2.getOrElse(() -> this._2), _3.getOrElse(() -> this._3));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tuple3<?, ?, ?> tuple3 = (Tuple3<?, ?, ?>) o;
        return Objects.equals(_1, tuple3._1) &&
                Objects.equals(_2, tuple3._2) &&
                Objects.equals(_3, tuple3._3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2, _3);
    }

    @Override
    public String toString() {
        return String.format("Tuple3(%s,%s,%s)", this._1, this._2, this._3);
    }

    @Override
    public List<Object> productCollection() {
        return Arrays.asList(this._1, this._2, this._3);
    }
}
