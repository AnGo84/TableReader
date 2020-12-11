package com.tablereader.properties;

import java.io.*;
import java.util.Properties;

public class PropertiesHandler {

    public static Properties getPropertiesFromFile(File file) throws IOException {
        if (file == null) {
            throw new FileNotFoundException("Properties' file '" + file.getPath() + "'  not found!");
        }
        Properties properties = new Properties();
        if (!file.exists()) {
            file.createNewFile();
        } else {
            try (InputStream inputStream = new FileInputStream(file)) {
                properties.load(inputStream);
            }
        }
        return properties;
    }

    private static void checkProperties(Properties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Properties cannot be null!!!");
        }
    }

    public static String getPropertyString(Properties properties, String propertyName) {
        return properties.getProperty(propertyName, "");
    }

    public static int getPropertyInt(Properties properties, String propertyName, int defaultValue) {
        int value = defaultValue;
        if (PropertiesHandler.getPropertyString(properties, propertyName) != null && PropertiesHandler.getPropertyString(properties, propertyName).length() > 0) {
            value = Integer.parseInt(PropertiesHandler.getPropertyString(properties, propertyName));
        }
        return value;
    }

    public static String toString(Properties properties) {
        final StringBuilder sb = new StringBuilder("Properties{").append("\n");
        for (final String name : properties.stringPropertyNames()) {
            sb.append(name).append(" : ").append(properties.getProperty(name)).append("\n");
        }
        sb.append('}');
        return sb.toString();
    }
}
