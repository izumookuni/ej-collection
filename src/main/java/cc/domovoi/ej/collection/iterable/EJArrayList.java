package cc.domovoi.ej.collection.iterable;

import cc.domovoi.ej.collection.tuple.Tuple2;
import cc.domovoi.ej.collection.tuple.Tuple3;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EJArrayList<A> extends ArrayList<A> implements SeqLike<A> {

    public static <B> Collector<B, EJArrayList<B>, EJArrayList<B>> collectEJArrayList() {
        return new Collector<B, EJArrayList<B>, EJArrayList<B>>() {
            @Override
            public Supplier<EJArrayList<B>> supplier() {
                return EJArrayList::new;
            }

            @Override
            public BiConsumer<EJArrayList<B>, B> accumulator() {
                return EJArrayList::add;
            }

            @Override
            public BinaryOperator<EJArrayList<B>> combiner() {
                return (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                };
            }

            @Override
            public Function<EJArrayList<B>, EJArrayList<B>> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
            }
        };
    }

    public static <B> EJArrayList<B> unit(B elem) {
        EJArrayList<B> list = new EJArrayList<>();
        list.add(elem);
        return list;
    }

    public static <B> EJArrayList<B> empty() {
        return new EJArrayList<>();
    }

    @SafeVarargs
    public static <B> EJArrayList<B> of(B ...elems) {
        EJArrayList<B> list = new EJArrayList<>();
        Collections.addAll(list, elems);
        return list;
    }

    public static <B> EJArrayList<B> of(Collection<? extends B> elems) {
        EJArrayList<B> list = new EJArrayList<>();
        list.addAll(elems);
        return list;
    }

    public EJArrayList<EJArrayList<Integer>> combinationIndices(int n) {
        if (n < 1 || n > this.size()) {
            return EJArrayList.empty();
        }
        else if (n == 1) {
            return this.indices().stream().map(EJArrayList::unit).collect(EJArrayList.collectEJArrayList());
        }
        else if (n == this.size()) {
            return EJArrayList.unit(this.indices());
        }
        else {
            EJArrayList<Integer> listIndices = IntStream.range(0, this.size() - n + 1).boxed().collect(EJArrayList.collectEJArrayList());
            return innerCombinationIndices(listIndices.stream().map(EJArrayList::unit).collect(EJArrayList.collectEJArrayList()), n, this.size());
        }
    }

    private EJArrayList<EJArrayList<Integer>> innerCombinationIndices(EJArrayList<EJArrayList<Integer>> buffer, final int n, final int size) {
        int step = 2;
        while (step <= n) {
            EJArrayList<EJArrayList<Integer>> temp = new EJArrayList<>();
            for (final EJArrayList<Integer> list : buffer) {
                int last = list.get(list.size());
                int until = size - (n - step);
                for (int i = last + 1; i < until; i += 1) {
                    EJArrayList<Integer> temp2 = new EJArrayList<>();
                    temp2.addAll(list);
                    temp2.add(i);
                    temp.add(temp2);
                }
            }
            buffer = temp;
            step += 1;
        }
        return buffer;
    }

    @Override
    public EJArrayList<EJArrayList<A>> combinations(int n) {
        return this.combinationIndices(n).stream().map(list -> list.stream().map(this::get).collect(EJArrayList.collectEJArrayList())).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public Boolean contains_(A elem) {
        return this.contains(elem);
    }

    @Override
    public <B> EJArrayList<B> flatMap(Function<? super A, ? extends SeqLike<? extends B>> f) {
        return this.stream().flatMap(elem -> f.apply(elem).stream()).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public EJArrayList<Integer> indices() {
        return IntStream.range(0, this.size()).boxed().collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public <B> EJArrayList<B> map(Function<? super A, ? extends B> f) {
        return this.stream().map(f).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public EJArrayList<A> padTo(int len, A elem) {
        int size = this.size();
        EJArrayList<A> list = new EJArrayList<>();
        list.addAll(this);
        if (len > size) {
            list.addAll(Collections.nCopies(len - size, elem));
        }
        return list;
    }

    @Override
    public EJArrayList<A> patch(int from, SeqLike<? extends A> that, int replaced) {
        EJArrayList<A> list = new EJArrayList<>();
        list.addAll(this);
        IntStream.range(from, from + replaced).forEach(i -> list.set(i, that.get(i - from)));
        return list;
    }

    @Override
    public EJArrayList<EJArrayList<A>> permutations() {
        // TODO: ???
        return null;
    }

    @Override
    public EJArrayList<A> reverse() {
        EJArrayList<A> list = new EJArrayList<>();
        list.addAll(this);
        Collections.reverse(list);
        return list;
    }

    @Override
    public <B> EJArrayList<B> scan(B zero, BiFunction<? super B, ? super A, ? extends B> op) {
        EJArrayList<B> list = new EJArrayList<>();
        list.add(zero);
        this.stream().reduce(zero, (l, r) -> {
            B temp = op.apply(l, r);
            list.add(temp);
            return temp;
        }, (l, r) -> null);
        return list;
    }

    @Override
    public <B extends Comparable<B>> EJArrayList<A> sortBy(Function<? super A, ? extends B> f) {
        return this.stream().sorted(Comparator.comparing(f)).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public EJArrayList<A> sortWith(Comparator<A> f) {
        return this.stream().sorted(f).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public <B> EJArrayList<EJArrayList<B>> transpose(Function<? super A, ? extends SeqLike<B>> f) {
        int rowSize = this.size();
        EJArrayList<EJArrayList<B>> list = new EJArrayList<>();
        IntStream.range(0, rowSize).forEach(rowIndex -> {
            SeqLike<B> rowList = f.apply(this.get(rowIndex));
            IntStream.range(0, rowList.size()).forEach(colSize -> {
                if (rowIndex != 0) {
                    list.get(colSize).add(rowList.get(colSize));
                }
                else {
                    list.add(EJArrayList.unit(rowList.get(colSize)));
                }
            });
        });
        return list;
    }

    @Override
    public EJArrayList<A> updated(int index, A elem) {
        EJArrayList<A> list = new EJArrayList<>();
        list.addAll(this);
        list.set(index, elem);
        return list;
    }

    @Override
    public <B, C> Tuple2<EJArrayList<B>, EJArrayList<C>> unzip(Function<? super A, ? extends Tuple2<B, C>> f) {
        EJArrayList<B> listB = new EJArrayList<>();
        EJArrayList<C> listC = new EJArrayList<>();
        this.stream().map(f).forEach(t2 -> {
            listB.add(t2._1());
            listC.add(t2._2());
        });
        return new Tuple2<>(listB, listC);
    }

    @Override
    public <B, C, D> Tuple3<EJArrayList<B>, EJArrayList<C>, EJArrayList<D>> upzip3(Function<? super A, ? extends Tuple3<B, C, D>> f) {
        EJArrayList<B> listB = new EJArrayList<>();
        EJArrayList<C> listC = new EJArrayList<>();
        EJArrayList<D> listD = new EJArrayList<>();
        this.stream().map(f).forEach(t3 -> {
            listB.add(t3._1());
            listC.add(t3._2());
            listD.add(t3._3());
        });
        return new Tuple3<>(listB, listC, listD);
    }

    @Override
    public <B> EJArrayList<Tuple2<A, B>> zip(SeqLike<B> that) {
        int size = this.size();
        int thatSize = that.size();
        EJArrayList<Tuple2<A, B>> list = new EJArrayList<>();
        IntStream.range(0, size <= thatSize ? size : thatSize).forEach(i -> list.add(new Tuple2<>(this.get(i), that.get(i))));
        return list;
    }

    @Override
    public <B> EJArrayList<Tuple2<A, B>> zipAll(SeqLike<B> that, A thisElem, B thatElem) {
        int size = this.size();
        int thatSize = that.size();
        EJArrayList<Tuple2<A, B>> list = new EJArrayList<>();
        IntStream.range(0, size <= thatSize ? thatSize : size).forEach(i -> {
            if (i < size -1 && i < thatSize - 1) {
                list.add(new Tuple2<>(this.get(i), that.get(i)));
            }
            else if (i < size - 1) {
                list.add(new Tuple2<>(this.get(i), thatElem));
            }
            else {
                list.add(new Tuple2<>(thisElem, that.get(i)));
            }
        });
        return list;
    }

    @Override
    public EJArrayList<Tuple2<A, Integer>> zipWithIndex() {
        return zip(IntStream.range(0, this.size()).boxed().collect(EJArrayList.collectEJArrayList()));
    }

    @Override
    public EJArrayList<A> cons(A elem) {
        EJArrayList<A> list = new EJArrayList<>();
        list.addAll(this);
        list.add(elem);
        return list;
    }

    @Override
    public EJArrayList<A> consAll(SeqLike<A> elems) {
        EJArrayList<A> list = new EJArrayList<>();
        list.addAll(this);
        list.addAll(elems);
        return list;
    }

    @Override
    public int count(Predicate<? super A> p) {
        int size = this.size();
        int index = 0;
        int count = 0;
        while (index < size) {
            if (p.test(this.get(index))) {
                count += 1;
            }
            index += 1;
        }
        return count;
    }

    @Override
    public EJArrayList<A> drop(int n) {
        return this.stream().skip(n).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public EJArrayList<A> dropWhile(Predicate<? super A> p) {
        int size = this.size();
        int index = 0;
        while (index < size) {
            if (!p.test(this.get(index))) {
                break;
            }
            index += 1;
        }
        return IntStream.range(index, size).mapToObj(this::get).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public Boolean exists(Predicate<? super A> p) {
        return this.stream().anyMatch(p);
    }

    @Override
    public EJArrayList<A> filter(Predicate<? super A> p) {
        return this.stream().filter(p).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public EJArrayList<A> filterNot(Predicate<? super A> p) {
        return this.stream().filter(p.negate()).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public Optional<A> find(Predicate<? super A> p) {
        return this.stream().filter(p).findFirst();
    }

    @Override
    public <B> B fold(B zero, BiFunction<? super B, ? super A, ? extends B> op) {
        return this.stream().reduce(zero, op::apply, null);
    }

    @Override
    public Boolean forall(Predicate<? super A> p) {
        return this.stream().allMatch(p);
    }

    @Override
    public void foreach(Consumer<? super A> f) {
        this.forEach(f);
    }

    @Override
    public <B> MapLike<B, EJArrayList<A>> groupBy(Function<? super A, ? extends B> f) {
        // TODO: groupBy
        return null;
    }

    @Override
    public EJArrayList<EJArrayList<A>> grouped(int size) {
        final int thisSize = this.size();
        final int groupedSize = thisSize / size;
        int index = 0;
        int groupIndex = -1;
        EJArrayList<EJArrayList<A>> list = new EJArrayList<>();
        while (index < thisSize) {
            if (index % size != 0) {
                list.get(groupIndex).add(this.get(index));
            }
            else {
                groupIndex += 1;
                list.add(EJArrayList.unit(this.get(index)));
            }
            index += 1;
        }
        return list;
    }

    @Override
    public A head() {
        return this.get(0);
    }

    @Override
    public Optional<A> headOption() {
        return !this.isEmpty() ? Optional.of(this.get(0)) : Optional.empty();
    }

    @Override
    public EJArrayList<A> init() {
        return this.stream().limit(this.size() - 1).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public A last() {
        return this.get(this.size() - 1);
    }

    @Override
    public Optional<A> lastOption() {
        return !this.isEmpty() ? Optional.of(this.get(this.size() - 1)) : Optional.empty();
    }

    @Override
    public <B extends Comparable<B>> Optional<A> maxBy(Function<? super A, ? extends B> f) {
        return this.stream().max(Comparator.comparing(f));
    }

    @Override
    public <B extends Comparable<B>> Optional<A> minBy(Function<? super A, ? extends B> f) {
        return this.stream().min(Comparator.comparing(f));
    }

    @Override
    public String mkString(String start, String sep, String end) {
        return this.stream().map(A::toString).collect(Collectors.joining(sep, start, end));
    }

    @Override
    public Tuple2<EJArrayList<A>, EJArrayList<A>> partition(Predicate<? super A> p) {
        int size = this.size();
        int index = 0;
        while (index < size) {
            if (!p.test(this.get(index))) {
                break;
            }
            index += 1;
        }
        return new Tuple2<>(IntStream.range(0, index).mapToObj(this::get).collect(EJArrayList.collectEJArrayList()), IntStream.range(index, size).mapToObj(this::get).collect(EJArrayList.collectEJArrayList()));
    }

    @Override
    public A reduce(BiFunction<? super A, ? super A, ? extends A> op) {
        return this.stream().reduce(op::apply).get();
    }

    @Override
    public Optional<A> reduceOption(BiFunction<? super A, ? super A, ? extends A> op) {
        return this.stream().reduce(op::apply);
    }

    @Override
    public EJArrayList<A> slice(int from, int until) {
        return IntStream.range(from, until).mapToObj(this::get).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public EJArrayList<EJArrayList<A>> sliding(int size, int step) {
        int thisSize = this.size();
        int groupSize = 0;
        EJArrayList<EJArrayList<A>> list = new EJArrayList<>();
        while (groupSize * step + size <= thisSize) {
            int innerIndex = 0;
            EJArrayList<A> innerList = new EJArrayList<>();
            while (innerIndex < size) {
                innerList.add(this.get(groupSize * step + innerIndex));
                innerIndex += 1;
            }
            list.add(innerList);
        }
        return list;
    }

    @Override
    public Tuple2<EJArrayList<A>, EJArrayList<A>> splitAt(int n) {
        return new Tuple2<>(IntStream.range(0, n).mapToObj(this::get).collect(EJArrayList.collectEJArrayList()), IntStream.range(n, this.size()).mapToObj(this::get).collect(EJArrayList.collectEJArrayList()));
    }

    @Override
    public EJArrayList<A> tail() {
        return this.stream().skip(1).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public EJArrayList<A> take(int n) {
        return this.stream().limit(n).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public EJArrayList<A> takeWhile(Predicate<? super A> p) {
        int size = this.size();
        int index = 0;
        while (index < size) {
            if (!p.test(this.get(index))) {
                break;
            }
            index += 1;
        }
        return IntStream.range(0, index).mapToObj(this::get).collect(EJArrayList.collectEJArrayList());
    }

    @Override
    public List<A> toList() {
        return this;
    }

    @Override
    public SetLike<A> toSet() {
        // TODO: toSet
        return null;
    }
    

}
