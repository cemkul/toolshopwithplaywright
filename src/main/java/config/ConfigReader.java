package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }

            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    private ConfigReader() {}

    public static String get(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.isBlank()) {
            return systemProperty;
        }

        String envKey = key.toUpperCase().replace(".", "_");
        String environment = System.getenv(envKey);
        if (environment != null && !environment.isBlank()) {
            return environment;
        }

        String property = PROPERTIES.getProperty(key);
        if (property != null && !property.isBlank()) {
            return property;
        }

        throw new RuntimeException("Property not found: " + key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}