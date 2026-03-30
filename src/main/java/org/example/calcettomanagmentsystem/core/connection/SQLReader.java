package org.example.calcettomanagmentsystem.core.connection;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Utility class for reading SQL script files from resources.
 * <p>
 * This class handles loading SQL files identified by {@link SQLSchemaNavigator}
 * and converting them into string format for execution.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public class SQLReader {
    /**
     * Reads a resource-based SQL file and returns its content as a string.
     * <p>
     * This method assumes UTF-8 encoding for the SQL file contents.
     * </p>
     *
     * @param sqlSchemaNavigator The navigator identifying the specific SQL script to load from resources.
     * @return The complete SQL script as a single {@link String}, with lines joined by newlines.
     * @throws IOException If the resource cannot be found or an error occurs during reading.
     */
    public static String readFile(SQLSchemaNavigator sqlSchemaNavigator) throws IOException {
        try (InputStream is = SQLReader.class.getResourceAsStream(sqlSchemaNavigator.getPath())) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return br.lines().collect(Collectors.joining("\n"));
        }
    }
}
