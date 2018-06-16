package cc.domovoi.ej.collection.iterable;

import cc.domovoi.ej.collection.tuple.Tuple2;
import cc.domovoi.ej.collection.tuple.Tuple3;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public interface SeqLike<A> extends IterableLike<A, SeqLike<A>>, List<A> {

    Boolean contains_(A elem);

    default Boolean endWith(SeqLike<? super A> seq) {
        return this.size() >= seq.size() && IntStream.range(0, seq.size()).allMatch(i -> seq.get(i) != null ? seq.get(i).equals(this.get(i)) : this.get(i) == null);
    }

    <B> SeqLike<B> flatMap(Function<? super A, ? extends SeqLike<? extends B>> f);

    default Integer indexOf_(A elem) {
        return indexOf(elem, 0);
    }

    default Integer indexOf(A elem, Integer from) {
        int size = this.size();
        if (from < size) {
            int index = from;
            while (index < size) {
                A elem2 = this.get(index);
                if (elem2 != null && elem2.equals(elem) || elem == null) {
                    break;
                }
                else {
                    index = index + 1;
                }
            }
            return index != size ? index : -1;
        }
        else {
            throw new IndexOutOfBoundsException(String.format("%d is out of range %d", from, size));
        }
    }

    default Integer indexOfSlice(SeqLike<? super A> that) {
        return indexOfSlice(that, 0);
    }

    default Integer indexOfSlice(SeqLike<? super A> that, Integer from) {
        int size = this.size();
        int thatSize = that.size();
        if (from + thatSize - 1 < size && thatSize != 0) {
            int index = from;
            while (index + thatSize - 1 < size) {
                int index2 = 0;
                boolean flag = true;
                while (index2 < thatSize) {
                    A thisElem = this.get(index + index2);
                    if (!(thisElem != null ? thisElem.equals(that.get(index2)) : that.get(index2) == null)) {
                        flag = false;
                        break;
                    }
                    else {
                        index2 = index2 + 1;
                    }
                }
                if (flag) {
                    break;
                }
                else {
                    index = index + 1;
                }
            }
            return index != size ? index : -1;
        }
        else if (thatSize == 0) {
            return -1;
        }
        else {
            throw new IndexOutOfBoundsException(String.format("that size is %d, and from is %d, out of range %d", thatSize, from, size));
        }
    }

    default Integer indexWhere(Predicate<? super A> p) {
        return indexWhere(p, 0);
    }

    default Integer indexWhere(Predicate<? super A> p, Integer from) {
        int size = this.size();
        if (from < size) {
            int index = from;
            while (index < size) {
                if (p.test(this.get(index))) {
                    break;
                }
                else {
                    index = index + 1;
                }
            }
            return index != size ? index : -1;
        }
        else {
            throw new IndexOutOfBoundsException(String.format("%d is out of range %d", from, size));
        }
    }

    SeqLike<Integer> indices();

    default Integer lastIndexOf_(A elem) {
        return lastIndexOf(elem, this.size() - 1);
    }

    default Integer lastIndexOf(A elem, Integer end) {
        int size = this.size();
        if (end < size) {
            int index = end;
            while (index > -1) {
                A elem2 = this.get(index);
                if (elem2 != null && elem2.equals(elem) || elem == null) {
                    break;
                }
                else {
                    index = index -1;
                }
            }
            return index;
        }
        else {
            throw new IndexOutOfBoundsException(String.format("%d is out of range %d", end, size));
        }
    }

    default Integer lastIndexOfSlice(SeqLike<? super A> that) {
        return lastIndexOfSlice(that, this.size() - 1);
    }

    default Integer lastIndexOfSlice(SeqLike<? super A> that, Integer end) {
        int size = this.size();
        int thatSize = that.size();
        if (thatSize - end < 2 && thatSize != 0) {
            int index = end - thatSize + 1; // (size - thatSize) - (size - end - 1)
            while (thatSize - index < 2) {
                int index2 = 0;
                boolean flag = true;
                while (index2 < thatSize) {
                    A thisElem = this.get(index + index2);
                    if (!(thisElem != null ? thisElem.equals(that.get(index2)) :that.get(index2) == null)) {
                        flag = false;
                        break;
                    }
                    else {
                        index2 = index2 + 1;
                    }
                }
                if (flag) {
                    break;
                }
                else {
                    index = index - 1;
                }
            }
            return index;
        }
        else if (thatSize == 0) {
            return -1;
        }
        else {
            throw new IndexOutOfBoundsException(String.format("that size is %d, and end is %d, out of range %d", thatSize, end, size));
        }
    }

    default Integer lastIndexWhere(Predicate<? super A> p) {
        return lastIndexWhere(p, this.size() - 1);
    }

    default Integer lastIndexWhere(Predicate<? super A> p, Integer end) {
        int size = this.size();
        if (end < size) {
            int index = end;
            while (index > -1) {
                A elem2 = this.get(index);
                if (p.test(elem2)) {
                    break;
                }
                else {
                    index = index -1;
                }
            }
            return index;
        }
        else {
            throw new IndexOutOfBoundsException(String.format("%d is out of range %d", end, size));
        }
    }

    <B> SeqLike<B> map(Function<? super A, ? extends B> f);

    SeqLike<A> padTo(Integer len, A elem);

    SeqLike<A> patch(Integer from, SeqLike<? extends A> that, Integer replaced);

    default Integer prefixLength(Predicate<? super A> p) {
        int size = this.size();
        int index = 0;
        while (index < size) {
            if (p.test(this.get(index))) {
                index += 1;
            }
            else {
                break;
            }
        }
        return index;
    }

    SeqLike<A> reverse();

    default <B> SeqLike<B> reverseMap(Function<? super A, ? extends B> f) {
        return this.reverse().map(f);
    }

    <B> SeqLike<B> scan(B zero, BiFunction<? super B, ? super A, ? extends B> op);

    <B extends Comparable<B>> SeqLike<A> sortBy(Function<? super A, ? super B> f);

    SeqLike<A> sortWith(Comparator<A> f);

    Boolean startWith(SeqLike<? super A> seq);

    @Override
    default SeqLike<A> toSeq() {
        return this;
    }

    static <B> SeqLike<SeqLike<B>> transpose(SeqLike<SeqLike<B>> seq) {
        return null;
    }

    static <B, C> Tuple2<SeqLike<B>, SeqLike<C>> unzip(SeqLike<Tuple2<B, C>> seq) {
        return null;
    }

    static <B, C, D>Tuple3<SeqLike<B>, SeqLike<C>, SeqLike<D>> upzip3(SeqLike<Tuple3<B, C, D>> seq) {
        return null;
    }

    <B> SeqLike<Tuple2<A, B>> zip(SeqLike<B> that);

    <B> SeqLike<Tuple2<A, B>> zipAll(SeqLike<B> that, A thisElem, B thatElem);

    SeqLike<Tuple2<A, Integer>> zipWithIndex();


}
