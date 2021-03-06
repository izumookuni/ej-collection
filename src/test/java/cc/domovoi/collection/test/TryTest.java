package cc.domovoi.collection.test;

import cc.domovoi.collection.util.Either;
import cc.domovoi.collection.util.Failure;
import cc.domovoi.collection.util.Success;
import cc.domovoi.collection.util.Try;
import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

public class TryTest {

    private Try<Integer> successfulTry = Try.apply(() -> 3 / 2);
    private Try<Integer> failingTry = Try.apply(() -> 3 / 0);

    @Test
    public void testConstructor() {
        Assert.assertTrue(successfulTry.isSuccess());
        Assert.assertTrue(failingTry.isFailure());
    }

    @Test
    public void testFilter() {
        Try<Integer> integerTry1 = successfulTry.filter(integer -> integer == 1);
        Try<Integer> integerTry2 = successfulTry.filter(integer -> integer > 1);
        Try<Integer> integerTry3 = failingTry.filter(integer -> integer == 1);
        Assert.assertTrue(integerTry1.isSuccess() && integerTry1.get() == 1);
        Assert.assertTrue(integerTry2.isFailure() && "Predicate Failure".equals(integerTry2.failed().get().getMessage()));
        Assert.assertTrue(integerTry3.isFailure() && "/ by zero".equals(integerTry3.failed().get().getMessage()));
    }

    @Test
    public void testMapAndFlatMap() {
        Try<Integer> successfulTry2 = Try.apply(() -> 4 / 2);
        Try<Integer> successfulTry3 = Try.apply(() -> 5 / 2);
        Try<Integer> integerTry1 = successfulTry.flatMap(i1 -> successfulTry2.flatMap(i2 -> successfulTry3.map(i3 -> i1 + i2 + i3)));
        Try<Integer> integerTry2 = failingTry.flatMap(i1 -> successfulTry2.flatMap(i2 -> successfulTry3.map(i3 -> i1 + i2 + i3)));
        Assert.assertTrue(integerTry1.isSuccess() && integerTry1.get() == 5);
        Assert.assertTrue(integerTry2.isFailure() && "/ by zero".equals(integerTry2.failed().get().getMessage()));
    }

    @Test
    public void testFlatten() {
        Try<Try<Integer>> integerTry1 = Try.apply(() -> Try.apply(() -> 3 / 2));
        Try<Try<Integer>> integerTry2 = Try.apply(() -> Try.apply(() -> {
            throw new RuntimeException("error");
        }));
        Try<Try<Integer>> integerTry3 = failingTry.map(integer -> Try.apply(() -> integer + 1));
        Try<Integer> integerTry4 = Try.flatten(integerTry1);
        Try<Integer> integerTry5 = Try.flatten(integerTry2);
        Try<Integer> integerTry6 = Try.flatten(integerTry3);
        Assert.assertTrue(integerTry4.isSuccess() && integerTry4.get() == 1);
        Assert.assertTrue(integerTry5.isFailure() && "error".equals(integerTry5.failed().get().getMessage()));
        Assert.assertTrue(integerTry6.isFailure() && "/ by zero".equals(integerTry6.failed().get().getMessage()));
    }

    @Test
    public void testFold() {
        Integer i1 = successfulTry.fold(exception -> Integer.MAX_VALUE, integer -> integer + 2);
        Integer i2 = failingTry.fold(exception -> Integer.MAX_VALUE, integer -> integer + 2);
        Assert.assertTrue(i1 == 3);
        Assert.assertTrue(i2 == Integer.MAX_VALUE);
    }

    @Test
    public void testForeach() {
        successfulTry.foreach(integer -> Assert.assertTrue(integer == 1));
        failingTry.foreach(integer -> {
            throw new RuntimeException("error");
        });
    }

    @Test
    public void testGetOrElse() {
        Integer i1 = successfulTry.getOrElse(() -> Integer.MAX_VALUE);
        Integer i2 = failingTry.getOrElse(() -> Integer.MAX_VALUE);
        Assert.assertTrue(i1 == 1);
        Assert.assertTrue(i2 == Integer.MAX_VALUE);
    }

    @Test
    public void testOrElse() {
        Try<Integer> integerTry1 = successfulTry.orElse(() -> Try.apply(() -> {
            throw new RuntimeException("error");
        }));
        Try<Integer> integerTry2 = failingTry.orElse(() -> Try.apply(() -> 2));
        Try<Integer> integerTry3 = failingTry.orElse(() -> Try.apply(() -> {
            throw new RuntimeException("error");
        }));
        Assert.assertTrue(integerTry1.isSuccess() && integerTry1.get() == 1);
        Assert.assertTrue(integerTry2.isSuccess() && integerTry2.get() == 2);
        Assert.assertTrue(integerTry3.isFailure() && "error".equals(integerTry3.failed().get().getMessage()));
    }

    @Test
    public void testRecover() {
        Try<Integer> integerTry1 = failingTry.recover(exception -> exception.getMessage().length());
        Try<Integer> integerTry2 = failingTry.recover(exception -> {
            throw new RuntimeException("error1");
        });
        Try<Integer> integerTry3 = successfulTry.recover(exception -> {
            throw new RuntimeException("error2");
        });
        Assert.assertTrue(integerTry1.isSuccess() && integerTry1.get() == "/ by zero".length());
        Assert.assertTrue(integerTry2.isFailure() && "error1".equals(integerTry2.failed().get().getMessage()));
        Assert.assertTrue(integerTry3.isSuccess() && integerTry3.get() == 1);
    }

    @Test
    public void testRecoverWith() {
        Try<Integer> integerTry1 = failingTry.recoverWith(exception -> Try.apply(() -> exception.getMessage().length()));
        Try<Integer> integerTry2 = failingTry.recoverWith(exception -> Try.apply(() -> {
            throw new RuntimeException("error1");
        }));
        Try<Integer> integerTry3 = successfulTry.recoverWith(exception -> Try.apply(() -> {
            throw new RuntimeException("error2");
        }));
        Assert.assertTrue(integerTry1.isSuccess() && integerTry1.get() == "/ by zero".length());
        Assert.assertTrue(integerTry2.isFailure() && "error1".equals(integerTry2.failed().get().getMessage()));
        Assert.assertTrue(integerTry3.isSuccess() && integerTry3.get() == 1);
    }

    @Test
    public void testToEither() {
        Either<Exception, Integer> either1 = successfulTry.toEither();
        Either<Exception, Integer> either2 = failingTry.toEither();
        Assert.assertTrue(either1.isRight() && either1.right().get() == 1);
        Assert.assertTrue(either2.isLeft() && "/ by zero".equals(either2.left().get().getMessage()));
    }

    @Test
    public void testToOption() {
        Optional<Integer> optionalInteger1 = successfulTry.toOptional();
        Optional<Integer> optionalInteger2 = failingTry.toOptional();
        Assert.assertTrue(optionalInteger1.isPresent() && optionalInteger1.get() == 1);
        Assert.assertFalse(optionalInteger2.isPresent());
    }

    @Test
    public void testTransform() {
        Try<Long> longTry1 = successfulTry.transform(integer -> Try.apply(() -> integer + 1000L), exception -> Try.apply(() -> exception.getMessage().length() + 1000L));
        Try<Long> longTry2 = failingTry.transform(integer -> Try.apply(() -> integer + 1000L), exception -> Try.apply(() -> exception.getMessage().length() + 1000L));
        Try<Long> longTry3 = successfulTry.transform(integer -> Try.apply(() -> {
            throw new RuntimeException("error1");
        }), exception -> Try.apply(() -> exception.getMessage().length() + 1000L));
        Try<Long> longTry4 = failingTry.transform(integer -> Try.apply(() -> integer + 1000L), exception -> Try.apply(() -> {
            throw new RuntimeException("error2");
        }));
        Assert.assertTrue(longTry1.isSuccess() && longTry1.get() == 1001L);
        Assert.assertTrue(longTry2.isSuccess() && longTry2.get() == "/ by zero".length() + 1000L);
        Assert.assertTrue(longTry3.isFailure() && "error1".equals(longTry3.failed().get().getMessage()));
        Assert.assertTrue(longTry4.isFailure() && "error2".equals(longTry4.failed().get().getMessage()));
    }

    @Test
    public void testAsRuntimeFailure() {
        Try<Integer> integerTry1 = new Failure<>(new Exception("An Exception"));
        Try<Integer> integerTry2 = new Failure<>(new RuntimeException("An RuntimeException"));
        Try<Integer> integerTry3 = new Failure<>(new NoSuchElementException("An NoSuchElementException"));
        Try<Integer> integerTry4 = new Success<>(1);
        Assert.assertTrue(integerTry1.asRuntimeFailure().isFailure());
        Assert.assertTrue(integerTry2.asRuntimeFailure().isSuccess());
        try {
            throw integerTry2.asRuntimeFailure().get();
        } catch (Exception e) {
            Assert.assertEquals("An RuntimeException", e.getMessage());
        }
        try {
            throw integerTry3.asRuntimeFailure().get();
        } catch (Exception e) {
            Assert.assertEquals("An NoSuchElementException", e.getMessage());
        }
        Assert.assertTrue(integerTry3.asRuntimeFailure().isSuccess());
        Assert.assertTrue(integerTry4.asRuntimeFailure().isFailure());
    }

    @Test
    public void testAdept() {
        Try<Integer> integerTry1 = new Failure<>(new RuntimeException("An RuntimeException in Integer type"));
        Try<Integer> integerTry2 = new Success<>(42);
        Try<String> stringTry1 = new Failure<>(new RuntimeException("An RuntimeException in String type"));
        Try<String> stringTry2 = new Success<>("Hello");
        Try<Double> doubleTry1 = integerTry1.adapt();
        Try<Double> doubleTry2 = integerTry2.adapt();
        Try<Double> doubleTry3 = stringTry1.adapt();
        Try<Double> doubleTry4 = stringTry2.adapt();
        Assert.assertTrue(doubleTry1.isFailure());
        Assert.assertEquals("An RuntimeException in Integer type", doubleTry1.asRuntimeFailure().get().getMessage());
        Assert.assertTrue(doubleTry2.isFailure());
        Assert.assertEquals("This object isn't instance of Failure", doubleTry2.asRuntimeFailure().get().getMessage());
        Assert.assertTrue(doubleTry3.isFailure());
        Assert.assertEquals("An RuntimeException in String type", doubleTry3.asRuntimeFailure().get().getMessage());
        Assert.assertTrue(doubleTry4.isFailure());
        Assert.assertEquals("This object isn't instance of Failure", doubleTry4.asRuntimeFailure().get().getMessage());
    }

    @Test
    public void testTryApply() {
        Try<Integer> integerTry = Try.apply(() -> {
            throw new Exception("An Exception");
        });
        Assert.assertTrue(integerTry.isFailure());
    }

}
