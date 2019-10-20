package io.soffa.core.commons;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class Reflection {

    private Reflection() {
    }

    public static Optional<Class<?>> fndGenericSuperClass(Object instance, Integer position) {

        Type generic = instance.getClass().getGenericSuperclass();
        while (!(generic instanceof ParameterizedType)) {
            generic = ((Class) generic).getGenericSuperclass();
            if (generic == null) {
                return Optional.empty();
            }
        }

        Type type = ((ParameterizedType) generic).getActualTypeArguments()[position];
        return Optional.of((Class<?>) type);
    }

}
