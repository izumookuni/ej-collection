package cc.domovoi.ej.collection.tuple;

import cc.domovoi.ej.collection.util.Option;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

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
    public int hashCode() {
        return new HashCodeBuilder(111, 117).append(_1).append(_2).append(_3).toHashCode();
    }

    @Override
    public String toString() {
        return String.format("Tuple3(%s,%s,%s)", this._1.toString(), this._2.toString(), this._3.toString());
    }

    @Override
    public List<Object> productCollection() {
        return Arrays.asList(this._1, this._2, this._3);
    }
}
