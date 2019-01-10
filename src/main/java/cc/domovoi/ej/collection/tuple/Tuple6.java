package cc.domovoi.ej.collection.tuple;

import cc.domovoi.ej.collection.util.Option;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A tuple of 6 elements.
 * @param <T1> Element 1 type of this Tuple6
 * @param <T2> Element 2 type of this Tuple6
 * @param <T3> Element 3 type of this Tuple6
 * @param <T4> Element 4 type of this Tuple6
 * @param <T5> Element 5 type of this Tuple6
 * @param <T6> Element 6 type of this Tuple6
 */
public class Tuple6<T1, T2, T3, T4, T5, T6> extends Product implements Serializable {

    private T1 _1;

    private T2 _2;

    private T3 _3;

    private T4 _4;

    private T5 _5;

    private T6 _6;

    public T1 _1() {
        return _1;
    }

    public T2 _2() {
        return _2;
    }

    public T3 _3() {
        return _3;
    }

    public T4 _4() {
        return _4;
    }

    public T5 _5() {
        return _5;
    }

    public T6 _6() {
        return _6;
    }

    public Tuple6(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
    }

    public Tuple6<T1, T2, T3, T4, T5, T6> copy(Option<T1> _1, Option<T2> _2, Option<T3> _3, Option<T4> _4, Option<T5> _5, Option<T6> _6) {
        return new Tuple6<>(_1.getOrElse(() -> this._1), _2.getOrElse(() -> this._2), _3.getOrElse(() -> this._3), _4.getOrElse(() -> this._4), _5.getOrElse(() -> this._5), _6.getOrElse(() -> this._6));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tuple6<?, ?, ?, ?, ?, ?> tuple6 = (Tuple6<?, ?, ?, ?, ?, ?>) o;
        return Objects.equals(_1, tuple6._1) &&
                Objects.equals(_2, tuple6._2) &&
                Objects.equals(_3, tuple6._3) &&
                Objects.equals(_4, tuple6._4) &&
                Objects.equals(_5, tuple6._5) &&
                Objects.equals(_6, tuple6._6);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2, _3, _4, _5, _6);
    }

    @Override
    public String toString() {
        return String.format("Tuple6(%s,%s,%s,%s,%s,%s)", this._1, this._2, this._3, this._4, this._5, this._6);
    }

    @Override
    public List<Object> productCollection() {
        return Arrays.asList(this._1, this._2, this._3, this._4, this._5, this._6);
    }
}
