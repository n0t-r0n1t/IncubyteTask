package rahaman.ronit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DOB_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");
    public List<Customer> parse(InputStream inputStream) throws IOException {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("|D|")) {
                    customers.add(parseLineToCustomer(line));
                }
            }
        }
        return customers;
    }

    private Customer parseLineToCustomer(String line) {
        String[] parts = line.split("\\|");

        String name = parts[2];
        String customerId = parts[3];
        LocalDate openDate = LocalDate.parse(parts[4], DATE_FORMATTER);
        LocalDate lastConsultedDate = LocalDate.parse(parts[5], DATE_FORMATTER);
        String vaccinationId = parts[6];
        String doctorName = parts[7];
        String state = parts[8];
        String country = parts[9];
        LocalDate dob = LocalDate.parse(parts[10], DOB_FORMATTER);
        boolean isActive = parts[11].equals("A");

        return new Customer(name, customerId, openDate, lastConsultedDate, vaccinationId, doctorName, state, country, dob, isActive);
    }
}
