package rahaman.ronit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVParser {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DOB_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");
    private final Map<String, Integer> headerIndexMap = new HashMap<>();

    public List<Customer> parse(InputStream inputStream) throws IOException {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            // First, read the headers and map them to indices
            if ((line = reader.readLine()) != null) {
                parseHeaders(line);
            }

            // Parse the data lines
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("|D|")) {
                    customers.add(parseLineToCustomer(line));
                }
            }
        }
        return customers;
    }

    private void parseHeaders(String headerLine) {
        String[] headers = headerLine.split("\\|");
        for (int i = 0; i < headers.length; i++) {
            headerIndexMap.put(headers[i].trim().replaceAll(" ", "_").toLowerCase(), i);
        }
    }

    private Customer parseLineToCustomer(String line) {
        String[] parts = line.split("\\|");

        // Parse fields based on header presence
        String name = getField(parts, "customer_name");
        String customerId = getField(parts, "customer_id");
        LocalDate openDate = parseDate(getField(parts, "open_date"), DATE_FORMATTER);
        LocalDate lastConsultedDate = parseDate(getField(parts, "last_consulted_date"), DATE_FORMATTER);
        String vaccinationType = getField(parts, "vaccination_id");
        String doctorConsulted = getField(parts, "dr_name");
        String state = getField(parts, "state");
        String country = getField(parts, "country");
        LocalDate dob = parseDate(getField(parts, "dob"), DOB_FORMATTER);
        boolean isActive = "A".equals(getField(parts, "is_active"));

        return new Customer(name, customerId, openDate, lastConsultedDate, vaccinationType, doctorConsulted, state, country, dob, isActive);
    }

    private String getField(String[] parts, String fieldName) {
        Integer index = headerIndexMap.get(fieldName);
        return (index != null && index < parts.length) ? parts[index].trim() : null;
    }

    private LocalDate parseDate(String dateString, DateTimeFormatter formatter) {
        return (dateString != null && !dateString.isEmpty()) ? LocalDate.parse(dateString, formatter) : null;
    }
}
