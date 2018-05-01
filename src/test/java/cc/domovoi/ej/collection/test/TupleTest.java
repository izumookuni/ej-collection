package cc.domovoi.ej.collection.test;

import cc.domovoi.ej.collection.tuple.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class TupleTest {

    @Test
    public void testConstructor() {

        Tuple1<Integer> tuple1 = new Tuple1<>(42);
        Assert.assertTrue(42 == tuple1._1());

        Tuple2<Integer, String> tuple2 = new Tuple2<>(42, "hello");
        Assert.assertTrue(42 == tuple2._1() && "hello".equals(tuple2._2()));

        Tuple3<Integer, String, Long> tuple3 = new Tuple3<>(42, "hello", 1234567890123456L);
        Assert.assertTrue(42 == tuple3._1() && "hello".equals(tuple3._2()) && 1234567890123456L == tuple3._3());

        Tuple4<Integer, String, Long, Boolean> tuple4 = new Tuple4<>(42, "hello", 1234567890123456L, false);
        Assert.assertTrue(42 == tuple4._1() && "hello".equals(tuple4._2()) && 1234567890123456L == tuple4._3() && !tuple4._4());

        Tuple5<Integer, String, Long, Boolean, Double> tuple5 = new Tuple5<>(42, "hello", 1234567890123456L, false, 1.11);
        Assert.assertTrue(42 == tuple5._1() && "hello".equals(tuple5._2()) && 1234567890123456L == tuple5._3() && !tuple5._4() && 1.11 == tuple5._5());

        Tuple6<Integer, String, Long, Boolean, Double, Tuple2<String, Integer>> tuple6 = new Tuple6<>(42, "hello", 1234567890123456L, false, 1.11, new Tuple2<>("world", 24));
        Assert.assertTrue(42 == tuple6._1() && "hello".equals(tuple6._2()) && 1234567890123456L == tuple6._3() && !tuple6._4() && 1.11 == tuple6._5() && Tuples.of("world", 24).equals(tuple6._6()));

        Tuple7<Integer, String, Long, Boolean, Double, Tuple2<String, Integer>, Optional<Boolean>> tuple7 = new Tuple7<>(42, "hello", 1234567890123456L, false, 1.11, new Tuple2<>("world", 24), Optional.of(true));
        Assert.assertTrue(42 == tuple7._1() && "hello".equals(tuple7._2()) && 1234567890123456L == tuple7._3() && !tuple7._4() && 1.11 == tuple7._5() && Tuples.of("world", 24).equals(tuple7._6()) && tuple7._7().orElse(false));
    }

    @Test
    public void testEquals() {
        Tuple3<Integer, String, Tuple2<Boolean, Double>> tuple3A = new Tuple3<>(42, "hello", new Tuple2<>(false, 1.11));
        Tuple3<Integer, String, Tuple2<Boolean, Double>> tuple3B = Tuples.of(42, "hello", Tuples.of(false, 1.11));
        Assert.assertEquals(tuple3A, tuple3B);
        Assert.assertTrue(tuple3A.equals(tuple3B));
        // It can't use '!=' to determine whether tuple3A and tuple3B is equal.
        Assert.assertTrue(tuple3A != tuple3B);
    }

    @Test
    public void testSwap() {
        Tuple2<String, Boolean> tuple2A = new Tuple2<>("hello", false);
        Tuple2<Boolean, String> tuple2B = new Tuple2<>(false, "hello");
        Assert.assertEquals(tuple2A, tuple2B.swap());
    }
}
