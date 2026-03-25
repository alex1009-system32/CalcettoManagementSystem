package org.example.calcettomanagmentsystem.core.connection;

import org.example.calcettomanagmentsystem.shared.navigation.SQLSchemaNavigator;

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
 * @author Senior Developer
 */
public class SQLReader {
    /**
     * Reads a resource-based SQL file and returns its content as a string.
     *
     * @param sqlSchemaNavigator The navigator identifying the specific SQL script to load.
     * @return The complete SQL script as a single {@link String}.
     * @throws IOException If the file cannot be found or read.
     */
    public static String readFile(SQLSchemaNavigator sqlSchemaNavigator) throws IOException {
        try (InputStream is = SQLReader.class.getResourceAsStream(sqlSchemaNavigator.getPath())) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return br.lines().collect(Collectors.joining("\n"));
        }
    }
}
