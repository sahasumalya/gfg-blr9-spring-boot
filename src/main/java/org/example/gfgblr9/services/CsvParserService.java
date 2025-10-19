package org.example.gfgblr9.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.example.gfgblr9.models.EmployeeCSV;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvParserService {

    public List<EmployeeCSV> parseCsvFile(String filePath) throws Exception {
        List<EmployeeCSV> employees = new ArrayList<>();

        try (Reader reader = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("Id", "Name", "Department") // CSV file headers
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord record : records) {
                EmployeeCSV employee = new EmployeeCSV();
                employee.setId(record.get("Id"));
                employee.setName(record.get("Name"));
                employee.setDepartment(record.get("Department"));
                employees.add(employee);
            }
        }

        return employees;
    }
}