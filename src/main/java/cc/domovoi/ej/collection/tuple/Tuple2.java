package cc.domovoi.ej.collection.tuple;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A tuple of 2 elements.
 * @param <T1> Element 1 type of this Tuple2
 * @param <T2> Element 2 type of this Tuple2
 */
public class Tuple2<T1, T2> extends Product implements Map.Entry<T1, T2>, Serializable {

    private T1 _1;

    private T2 _2;

    public T1 _1() {
        return this._1;
    }

    public T2 _2() {
        return this._2;
    }

    public Tuple2(T1 _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public Tuple2<T2, T1> swap() {
        return new Tuple2<>(this._2, this._1);
    }

    @Override
    public T1 getKey() {
        return this._1;
    }

    @Override
    public T2 getValue() {
        return this._2;
    }

    @Override
    public T2 setValue(T2 value) {
        throw new RuntimeException("Tuple2 is immutable");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;
        return Objects.equals(_1, tuple2._1) &&
                Objects.equals(_2, tuple2._2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2);
    }

    @Override
    public String toString() {
        return String.format("Tuple2(%s,%s)", this._1.toString(), this._2.toString());
    }

    @Override
    public List<Object> productCollection() {
        return Arrays.asList(this._1, this._2);
    }
}
