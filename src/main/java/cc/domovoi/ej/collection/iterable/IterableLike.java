package cc.domovoi.ej.collection.iterable;

import cc.domovoi.ej.collection.tuple.Tuple2;

import java.util.List;
import java.util.Optional;
import java.util.function.*;

public interface IterableLike<A, Repr extends IterableLike> {

    Repr cons(A elem);

    Repr consAll(Repr elems);

    Integer count(Predicate<? super A> p);

    Repr drop(Integer n);

    Repr dropWhile(Predicate<? super A> p);

    Boolean exists(Predicate<? super A> p);

    Repr filter(Predicate<? super A> p);

    Repr filterNot(Predicate<? super A> p);

    Optional<A> find(Predicate<? super A> p);

    <B> B fold(B zero, BiFunction<? super B, ? super A, ? extends A> op);

    Boolean forall(Predicate<? super A> p);

    void foreach(Consumer<? super A> f);

    <B> MapLike<B, Repr> groupBy(Function<? super A, ? extends B> f);

    SeqLike<Repr> grouped(Integer size);

    A head();

    Optional<A> headOption();

    SeqLike<A> init();

//    Boolean isEmpty();

    A last();

    Optional<A> lastOption();

    <B extends Comparable<B>> A maxBy(Function<? super A, ? extends B> f);

    <B extends Comparable<B>> A minBy(Function<? super A, ? extends B> f);

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

    Tuple2<Repr, Repr> partition(Predicate<? super A> p);

    A reduce(BiFunction<? super A, ? super A, ? extends A> op);

    Optional<A> reduceOption(BiFunction<? super A, ? super A, ? extends A> op);

    int size();

    Repr slice(Integer from, Integer until);

    SeqLike<Repr> sliding(Integer size);

    default Tuple2<Repr, Repr> span(Predicate<? super A> p) {
        return new Tuple2<>(takeWhile(p), dropWhile(p));
    }

    Tuple2<Repr, Repr> splitAt(Integer n);

    Repr tail();

    Repr take(Integer n);

    Repr takeWhile(Predicate<? super A> p);

    List<A> toList();

    SeqLike<A> toSeq();

    SetLike<A> toSet();




}
