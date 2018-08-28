package cc.domovoi.ej.collection.tuple;

import cc.domovoi.ej.collection.util.Option;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public final class Tuple1<T1> extends Product implements Serializable {

    private T1 _1;

    public T1 _1() {
        return this._1;
    }

    public Tuple1(T1 _1) {
        this._1 = _1;
    }

    public <T> Tuple1<T> map(Function<? super T1, ? extends T> f) {
        return new Tuple1<>(f.apply(_1));
    }

    public <T> Tuple1<T> flatMap(Function<? super T1, ? extends Tuple1<T>> f) {
        return f.apply(_1);
    }

    public Tuple1<T1> copy(Option<T1> _1) {
        return new Tuple1<>(_1.getOrElse(() -> this._1));
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(111, 113).append(_1).toHashCode();
    }

    @Override
    public String toString() {
        return String.format("Tuple1(%s)", this._1.toString());
    }

    @Override
    public List<Object> productCollection() {
        return Collections.singletonList(this._1);
    }
}
