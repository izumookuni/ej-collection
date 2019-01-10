package cc.domovoi.ej.collection.tuple;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * A tuple of 1 elements.
 * @param <T1> Element 1 type of this Tuple1
 */
public class Tuple1<T1> extends Product implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tuple1<?> tuple1 = (Tuple1<?>) o;
        return Objects.equals(_1, tuple1._1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1);
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
