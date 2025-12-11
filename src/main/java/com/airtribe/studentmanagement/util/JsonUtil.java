package com.airtribe.studentmanagement.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {
	private static final JsonUtil INSTANCE = new JsonUtil();

	// Register LocalDate adapter here
	private final Gson gson = new GsonBuilder().registerTypeAdapter(java.time.LocalDate.class, new LocalDateAdapter())
			.setPrettyPrinting().create();

	private JsonUtil() {
	}

	public static JsonUtil getInstance() {
		return INSTANCE;
	}

	public <T> List<T> readList(String path, Class<T> clazz) {
		try {
			if (!Files.exists(Paths.get(path)))
				return List.of();
			Reader r = new FileReader(path);
			Type type = TypeToken.getParameterized(List.class, clazz).getType();
			List<T> list = gson.fromJson(r, type);
			r.close();
			return list == null ? List.of() : list;
		} catch (IOException e) {
			throw new RuntimeException("Failed to read " + path, e);
		}
	}

	public <T> void writeList(String path, List<T> list) {
		try (Writer w = new FileWriter(path)) {
			gson.toJson(list, w);
		} catch (IOException e) {
			throw new RuntimeException("Failed to write " + path, e);
		}
	}
}
