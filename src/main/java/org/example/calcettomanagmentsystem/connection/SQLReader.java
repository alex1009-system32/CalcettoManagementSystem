package org.example.calcettomanagmentsystem.connection;

import org.example.calcettomanagmentsystem.navigation.SQLSchemaNavigator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class SQLReader {
    public static String readFile(SQLSchemaNavigator sqlSchemaNavigator) throws IOException {
        try (InputStream is = SQLReader.class.getResourceAsStream(sqlSchemaNavigator.getPath())) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return br.lines().collect(Collectors.joining("\n"));
        }
    }
}
