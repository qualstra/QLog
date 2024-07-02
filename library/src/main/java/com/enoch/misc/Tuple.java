package com.enoch.misc;

import lombok.Data;

@Data
public class Tuple<T, V> {
	private T value1;
	private V value2;

	public Tuple(T obj, V weight) {
		this.value1 = obj;
		this.value2 = weight;
	}
}
