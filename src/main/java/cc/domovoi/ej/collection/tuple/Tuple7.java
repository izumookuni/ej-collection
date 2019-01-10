package cc.domovoi.ej.collection.tuple;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A tuple of 7 elements.
 * @param <T1> Element 1 type of this Tuple7
 * @param <T2> Element 2 type of this Tuple7
 * @param <T3> Element 3 type of this Tuple7
 * @param <T4> Element 4 type of this Tuple7
 * @param <T5> Element 5 type of this Tuple7
 * @param <T6> Element 6 type of this Tuple7
 * @param <T7> Element 7 type of this Tuple7
 */
public class Tuple7<T1, T2, T3, T4, T5, T6, T7> extends Product implements Serializable {

    private T1 _1;

    private T2 _2;

    private T3 _3;

    private T4 _4;

    private T5 _5;

    private T6 _6;

    private T7 _7;

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

    public T7 _7() {
        return _7;
    }

    public Tuple7(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
        this._7 = _7;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tuple7<?, ?, ?, ?, ?, ?, ?> tuple7 = (Tuple7<?, ?, ?, ?, ?, ?, ?>) o;
        return Objects.equals(_1, tuple7._1) &&
                Objects.equals(_2, tuple7._2) &&
                Objects.equals(_3, tuple7._3) &&
                Objects.equals(_4, tuple7._4) &&
                Objects.equals(_5, tuple7._5) &&
                Objects.equals(_6, tuple7._6) &&
                Objects.equals(_7, tuple7._7);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2, _3, _4, _5, _6, _7);
    }

    @Override
    public String toString() {
        return String.format("Tuple7(%s,%s,%s,%s,%s,%s,%s)", this._1.toString(), this._2.toString(), this._3.toString(), this._4.toString(), this._5.toString(), this._6.toString(), this._7.toString());
    }

    @Override
    public List<Object> productCollection() {
        return Arrays.asList(this._1, this._2, this._3, this._4, this._5, this._6, this._7);
    }
}
