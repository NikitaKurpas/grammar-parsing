package expressiontree.vm;

import expressiontree.ir.Type;

/**
 * Created by nikit on 27-Apr-16
 */
public class Value<T> {
    private Type type;
    private T value;

    public Value(Type type, T value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public T getValue() {
        return value;
    }
}
