package src.javaproject.classes;

import src.javaproject.interfaces.SerializeMarker;

import java.io.Serializable;

public final class SerialWriter<T extends String, R extends SerializeMarker> implements Serializable, SerializeMarker {
    private final T one;
    private final R two;

    public SerialWriter(T first, R second) {
        one = first;
        two = second;
    }

    @Override
    public String toString() {
        return STR."User \{one} has modified entry \{two.toString()}";
    }
}
