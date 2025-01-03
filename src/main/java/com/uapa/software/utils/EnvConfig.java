package com.uapa.software.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
	private static Dotenv dotenv;

	static {
		String env = System.getProperty("env", "default");
		if ("test".equals(env)) {
			dotenv = Dotenv.configure().filename(".env.test").load();
		} else {
			dotenv = Dotenv.configure().load(); // Carga el archivo .env por defecto
		}
	}

	public static String get(String key) {
		return dotenv.get(key);
	}
}
