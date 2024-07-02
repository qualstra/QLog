package com.enoch.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;

public class CopyUtil {
	public static <T, S> List<S> map(Iterable<T> elems, Mapper<T, S> map) {
		final List<S> list = new ArrayList<S>();
		elems.forEach(elem -> list.add(map.map(elem)));
		return list;
	}

	public static <T, S> List<S> map2(Iterable<T> elems, Function<T, S> map) {
		final List<S> list = new ArrayList<S>();
		elems.forEach(elem -> list.add(map.apply(elem)));
		return list;
	}
	public static <T> T transform(Transform<T> t) {
		T res = t.transform();
		// BeanUtils.copyProperties(t, res);
		return res;
	}

	public static <T> List<T> transform(Iterable<? extends Transform<T>> src) {
		final List<T> listRes = new ArrayList<T>();
		src.forEach(arg -> listRes.add(transform(arg)));
		return listRes;
	}

	public static void writeToFile(InputStream initialStream, File targetFile) throws IOException {
		FileUtils.copyInputStreamToFile(initialStream, targetFile);
	}

	public static void writeToFile(InputStream initialStream, String targetFile) throws IOException {
		writeToFile(initialStream, new File(targetFile));
	}

}
