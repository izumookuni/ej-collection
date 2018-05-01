package cc.domovoi.ej.collection.tuple;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder(111, 123).append(_1).append(_2).append(_3).append(_4).append(_5).append(_6).toHashCode();
    }

    @Override
    public String toString() {
        return String.format("Tuple6(%s,%s,%s,%s,%s,%s)", this._1.toString(), this._2.toString(), this._3.toString(), this._4.toString(), this._5.toString(), this._6.toString());
    }

    @Override
    public List<Object> productCollection() {
        return Arrays.asList(this._1, this._2, this._3, this._4, this._5, this._6);
    }
}
