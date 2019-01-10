package cc.domovoi.ej.collection.tuple;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A tuple of 4 elements.
 * @param <T1> Element 1 type of this Tuple4
 * @param <T2> Element 2 type of this Tuple4
 * @param <T3> Element 3 type of this Tuple4
 * @param <T4> Element 4 type of this Tuple4
 */
public class Tuple4<T1, T2, T3, T4> extends Product implements Serializable {

    private T1 _1;

    private T2 _2;

    private T3 _3;

    private T4 _4;

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

    public Tuple4(T1 _1, T2 _2, T3 _3, T4 _4) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tuple4<?, ?, ?, ?> tuple4 = (Tuple4<?, ?, ?, ?>) o;
        return Objects.equals(_1, tuple4._1) &&
                Objects.equals(_2, tuple4._2) &&
                Objects.equals(_3, tuple4._3) &&
                Objects.equals(_4, tuple4._4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2, _3, _4);
    }

    @Override
    public String toString() {
        return String.format("Tuple4(%s,%s,%s,%s)", this._1.toString(), this._2.toString(), this._3.toString(), this._4.toString());
    }

    @Override
    public List<Object> productCollection() {
        return Arrays.asList(this._1, this._2, this._3, this._4);
    }
}
