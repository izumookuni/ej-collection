package cc.domovoi.ej.collection.tuple;

import java.util.List;

public interface ProductLike {

    Integer productArity();

    Object productElement(Integer n);

    List<Object> productCollection();
}
