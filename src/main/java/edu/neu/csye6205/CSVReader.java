package edu.neu.csye6205;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private final String filePath;

    public CSVReader(String filePath) {
        this.filePath = filePath;
    }

    // Reading from CSV file and splitting string
    public List<String[]> readCSV() throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                data.add(parts);
            }
        }

        return data;
    }
}




