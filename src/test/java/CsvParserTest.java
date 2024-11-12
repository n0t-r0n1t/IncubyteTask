import org.junit.jupiter.api.Test;
import rahaman.ronit.CSVParser;
import rahaman.ronit.Customer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CsvParserTest {
    @Test
    public void testParse() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("sample.csv");
        CSVParser parser = new CSVParser();
        List<Customer> customers = parser.parse(is);
        System.out.println(customers.toString());
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
    }
}
