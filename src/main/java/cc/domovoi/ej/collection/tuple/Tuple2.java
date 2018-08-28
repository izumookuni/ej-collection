package cc.domovoi.ej.collection.tuple;

import cc.domovoi.ej.collection.util.Option;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class Tuple2<T1, T2> extends Product implements Map.Entry<T1, T2>, Serializable {

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

    public Tuple2<T1, T2> copy(Option<T1> _1, Option<T2> _2) {
        return new Tuple2<>(_1.getOrElse(() -> this._1), _2.getOrElse(() -> this._2));
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
    public int hashCode() {
        return new HashCodeBuilder(111, 115).append(_1).append(_2).toHashCode();
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
