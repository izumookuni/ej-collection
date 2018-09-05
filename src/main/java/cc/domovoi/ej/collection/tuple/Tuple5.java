package cc.domovoi.ej.collection.tuple;

import cc.domovoi.ej.collection.util.Option;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Tuple5<T1, T2, T3, T4, T5> extends Product implements Serializable {

    private T1 _1;

    private T2 _2;

    private T3 _3;

    private T4 _4;

    private T5 _5;

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

    public Tuple5(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
    }

    public Tuple5<T1, T2, T3, T4, T5> copy(Option<T1> _1, Option<T2> _2, Option<T3> _3, Option<T4> _4, Option<T5> _5) {
        return new Tuple5<>(_1.getOrElse(() -> this._1), _2.getOrElse(() -> this._2), _3.getOrElse(() -> this._3), _4.getOrElse(() -> this._4), _5.getOrElse(() -> this._5));
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(111, 121).append(this._1).append(this._2).append(this._3).append(this._4).append(this._5).toHashCode();
    }

    @Override
    public String toString() {
        return String.format("Tuple5(%s,%s,%s,%s,%s)", this._1, this._2, this._3, this._4, this._5);
    }

    @Override
    public List<Object> productCollection() {
        return Arrays.asList(this._1, this._2, this._3, this._4, this._5);
    }
}
