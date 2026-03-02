package org.example.calcettomanagmentsystem.connection;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SQLReader {
    public static String readFile(String fileName) throws IOException {
        InputStream is = SQLReader.class.getResourceAsStream("/org/example/calcettomanagmentsystem/schemas/"+fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        return br.readAllAsString();
    }
}
