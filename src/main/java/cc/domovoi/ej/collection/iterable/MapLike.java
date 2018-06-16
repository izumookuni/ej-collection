package cc.domovoi.ej.collection.iterable;

import cc.domovoi.ej.collection.tuple.Tuple2;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface MapLike<K, V> extends IterableLike<Tuple2<K, V>, MapLike<K, V>>, Map<K, V> {

    Boolean contains(V elem);

    <K2, V2> MapLike<K2, V2> flatMap(Function<? super Tuple2<K, V>, ? extends MapLike<? extends K2, ? extends V2>> f);

    <K2, V2> MapLike<K2, V2> map(Function<? super Tuple2<K, V>, ? extends Tuple2<? extends K2, ? extends V2>> f);

    <V2> MapLike<K, V2> mapValues(Function<? super V, ? extends V2> f);

    <K2, V2> MapLike<K2, V2> scan(Tuple2<K2, V2> zero, BiFunction<? super Tuple2<K2, V2>, ? super Tuple2<K, V>, ? extends Tuple2<K2, V2>> op);

}
