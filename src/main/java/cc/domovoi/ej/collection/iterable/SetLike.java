package cc.domovoi.ej.collection.iterable;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface SetLike<A> extends IterableLike<A, SetLike<A>>, Set<A> {

    SetLike<A> and(SetLike<A> that);

    SetLike<A> andNot(SetLike<A> that);

    Boolean contains_(A elem);

    SetLike<A> diff(SetLike<A> that);

    <B> SeqLike<B> flatMap(Function<? super A, ? extends SetLike<? extends B>> f);

    SeqLike<Integer> indices();

    <B> SeqLike<B> map(Function<? super A, ? extends SetLike<? extends B>> f);

    <B> SetLike<B> scan(B zero, BiFunction<? super B, ? super A, ? extends B> op);

    @Override
    default SetLike<A> toSet() {
        return this;
    }
}
