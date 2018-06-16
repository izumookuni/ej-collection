package cc.domovoi.ej.collection.iterable;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface SetLike<A> extends IterableLike<A, SetLike<A>>, Set<A> {

    Boolean contains_(A elem);

    <B> SeqLike<B> flatMap(Function<? super A, ? extends SetLike<? extends B>> f);

    <B> SeqLike<B> map(Function<? super A, ? extends SetLike<? extends B>> f);

    <B> SetLike<B> scan(B zero, BiFunction<? super B, ? super A, ? extends B> op);
}
