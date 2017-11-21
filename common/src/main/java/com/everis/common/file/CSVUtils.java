package com.everis.common.file;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CSVUtils {

    public static void saveAsCsv(final List<String> rows, final String fileName) throws FileNotFoundException {
        if (rows != null && rows.size() > 0) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(fileName);
                for (final String row : rows) {
                    writer.println(row);
                }
            } finally {
                if(writer != null){
                    writer.close();
                }
            }
        }
    }

}
