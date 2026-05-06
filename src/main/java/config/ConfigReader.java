package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("config.properties not found");
            }

            PROPERTIES.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null) {
            throw new RuntimeException("Property not found: " + key);
        }

        return value;
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}