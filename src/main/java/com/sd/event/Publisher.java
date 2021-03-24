package com.sd.event;

public interface Publisher<T> {
    T publish(T t);
}
