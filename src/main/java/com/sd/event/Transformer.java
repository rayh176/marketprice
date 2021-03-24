package com.sd.event;

import java.util.Optional;

@FunctionalInterface
public interface Transformer<T> {
    Optional<T> onTransform(String data);
}
