package com.example.JPA.service;

import com.example.JPA.model.Employee;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class SecretSantaService {
    public void assignSecretSantas(String inputFile, String outputFile) throws IOException, CsvValidationException {
        List<Employee> employees = readEmployeesFromCsv(inputFile);

        if (employees.size() < 2) {
            throw new IllegalArgumentException("Participants should be more than 2 for Secret Santa Game!");
        }

        List<Employee> shuffledEmployees = new ArrayList<>(employees);

        Map<Employee, Employee> secretSantaAssignments = new HashMap<>();
        for (int i = 0; i < employees.size(); i++) {
            Employee giver = employees.get(i);
            Employee receiver = shuffledEmployees.get(i);

            if (giver.getEmail().equals(receiver.getEmail())) {
                Collections.shuffle(shuffledEmployees);
                i = -1;
                continue;
            }

            secretSantaAssignments.put(giver, receiver);
        }

        // Write assignments to output CSV
        writeAssignmentsToCsv(secretSantaAssignments, outputFile);
    }

    private List<Employee> readEmployeesFromCsv(String filePath) throws IOException, CsvValidationException {
        List<Employee> employees = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

        if (inputStream == null) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String[] nextLine;
            reader.readNext(); // Skip header

            while ((nextLine = reader.readNext()) != null) {
                employees.add(new Employee(nextLine[0], nextLine[1]));
            }
        }

        return employees;
    }

    private void writeAssignmentsToCsv(Map<Employee, Employee> assignments, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("Employee_Name,Employee_EmailID,Secret_Child_Name,Secret_Child_EmailID\n");

            for (Map.Entry<Employee, Employee> entry : assignments.entrySet()) {
                Employee giver = entry.getKey();
                Employee receiver = entry.getValue();
                writer.write(giver.getName() + "," + giver.getEmail() + "," + receiver.getName() + "," + receiver.getEmail() + "\n");
            }
        }
    }
}
