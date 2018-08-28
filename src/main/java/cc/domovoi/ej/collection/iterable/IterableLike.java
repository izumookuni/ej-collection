package cc.domovoi.ej.collection.iterable;

import cc.domovoi.ej.collection.tuple.Tuple2;

import java.util.List;
import java.util.Optional;
import java.util.function.*;

public interface IterableLike<A, Repr extends IterableLike> extends Iterable<A> {

    Repr cons(A elem);

    Repr consAll(Repr elems);

    int count(Predicate<? super A> p);

    Repr drop(int n);

    Repr dropWhile(Predicate<? super A> p);

    Boolean exists(Predicate<? super A> p);

    Repr filter(Predicate<? super A> p);

    Repr filterNot(Predicate<? super A> p);

    Optional<A> find(Predicate<? super A> p);

    <B> B fold(B zero, BiFunction<? super B, ? super A, ? extends B> op);

    Boolean forall(Predicate<? super A> p);

    void foreach(Consumer<? super A> f);

    <B> MapLike<B, ? extends Repr> groupBy(Function<? super A, ? extends B> f);

    SeqLike<? extends Repr> grouped(int size);

    A head();

    Optional<A> headOption();

    Repr init();

//    Boolean isEmpty();

    A last();

    Optional<A> lastOption();

    <B extends Comparable<B>> Optional<A> maxBy(Function<? super A, ? extends B> f);

    <B extends Comparable<B>> Optional<A> minBy(Function<? super A, ? extends B> f);

    default String mkString() {
        return mkString("");
    }

    default String mkString(String sep) {
        return mkString("", sep, "");
    }

    String mkString(String start, String sep, String end);

//    default Boolean nonEmpty() {
//        return !isEmpty();
//    }

    Tuple2<? extends Repr, ? extends Repr> partition(Predicate<? super A> p);

    A reduce(BiFunction<? super A, ? super A, ? extends A> op);

    Optional<A> reduceOption(BiFunction<? super A, ? super A, ? extends A> op);

    int size();

    Repr slice(int from, int until);

    default SeqLike<? extends Repr> sliding(int size) {
        return sliding(size, 1);
    }

    SeqLike<? extends Repr> sliding(int size, int step);

    default Tuple2<? extends Repr, ? extends Repr> span(Predicate<? super A> p) {
        return new Tuple2<>(takeWhile(p), dropWhile(p));
    }

    Tuple2<? extends Repr, ? extends Repr> splitAt(int n);

    Repr tail();

    Repr take(int n);

    Repr takeWhile(Predicate<? super A> p);

    List<A> toList();

    SeqLike<A> toSeq();

    SetLike<A> toSet();




}
