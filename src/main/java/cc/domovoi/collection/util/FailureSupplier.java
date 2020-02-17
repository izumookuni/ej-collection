package cc.domovoi.collection.util;

@FunctionalInterface
public interface FailureSupplier<T> {

    T get() throws Exception;
}
