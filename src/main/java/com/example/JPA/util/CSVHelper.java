package com.example.JPA.util;

import com.example.JPA.model.Employee;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static List<Employee> readCSV(String filePath) throws IOException, CsvValidationException {
        List<Employee> employees = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                employees.add(new Employee(line[0], line[1]));
            }
        }
        return employees;
    }

    public static void writeCSV(String filePath, List<Employee[]> assignments) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[]{"Employee_Name", "Employee_EmailID", "Secret_Child_Name", "Secret_Child_EmailID"});
            for (Employee[] pair : assignments) {
                writer.writeNext(new String[]{pair[0].getName(), pair[0].getEmail(), pair[1].getName(), pair[1].getEmail()});
            }
        }
    }
}
