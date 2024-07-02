package com.enoch.utils;


@FunctionalInterface
public interface Compare<S, T> {
	public boolean compare(S ths, T tht);
}