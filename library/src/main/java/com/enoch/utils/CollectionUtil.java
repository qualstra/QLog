package com.enoch.utils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CollectionUtil {

	public static <T> boolean contains(Collection<T> collect, T toCheck) {
		return collect.contains(toCheck);
	}

	public static <T> boolean contains(Collection<T> collect, Transform<T> toCheck) {
		return collect.contains(CopyUtil.transform(toCheck));
	}

	public static <T,S> Optional<T> find(List<T> srcList, S elem, Compare<T, S> comparator) {
		return srcList.stream().filter(a -> comparator.compare(a, elem)).findAny();
	}
}