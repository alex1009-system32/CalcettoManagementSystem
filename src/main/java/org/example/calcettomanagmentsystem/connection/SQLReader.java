package org.example.calcettomanagmentsystem.connection;

import org.example.calcettomanagmentsystem.navigation.SQLScheamNavigation;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SQLReader {
    public static String readFile(SQLScheamNavigation sqlScheamNavigation) throws IOException {
        InputStream is = SQLReader.class.getResourceAsStream(sqlScheamNavigation.getPath());
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        return br.readAllAsString();
    }
}
