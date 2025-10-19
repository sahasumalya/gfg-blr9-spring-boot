package org.example.gfgblr9.services;

import org.example.gfgblr9.models.EmployeeCSV;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CsvParserServiceTest {

    @Test
    public void parseCsvFileTest() throws Exception {
        CsvParserService csvParserService = new CsvParserService();
        List<EmployeeCSV> employees = csvParserService.parseCsvFile("src/main/resources/employees.csv");
        Assertions.assertEquals(2, employees.size());
    }
}
