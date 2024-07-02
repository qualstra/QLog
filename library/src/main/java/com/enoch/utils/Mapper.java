package com.enoch.utils;

@FunctionalInterface
public interface Mapper<T, S> {

	S map(T elem);

}
