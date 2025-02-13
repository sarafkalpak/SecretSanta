package com.example.JPA.controller;

import com.example.JPA.service.SecretSantaService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class SecretSantaController {

    @Autowired
    private SecretSantaService secretSantaService;

    @GetMapping
    public String home() {
        return "Welcome to Secret Santa API! Use /api/assign to assign Secret Santas.";
    }

    @PostMapping("/assign")
    public String assignSecretSantas() throws CsvValidationException {
        String inputFile = "employees.csv"; // Use classpath resource
        String outputFile = "src/main/resources/output.csv";

        try {
            secretSantaService.assignSecretSantas(inputFile, outputFile);
            return "Secret Santa assignments completed successfully! Check output file: " + outputFile;
        } catch (IOException e) {
            return "Error processing the CSV files: " + e.getMessage();
        }
    }
}
