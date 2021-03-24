package com.sd.event;

public interface Listener<T> {
    T onMessage(String message);
}
