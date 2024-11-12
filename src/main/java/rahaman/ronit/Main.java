package rahaman.ronit;

import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            InputStream is = Main.class.getClassLoader().getResourceAsStream("sample.csv");
            CSVParser parser = new CSVParser();

            Connection conn = DatabaseConnection.getConnection();

            TableCreator tableCreator = new TableCreator(conn);
            tableCreator.createStagingTable();

            DataIngestor dataIngestor = new DataIngestor(conn);
            List<Customer> customers = parser.parse(is);
            dataIngestor.loadToStaging(customers);

            DataTransformer transformer = new DataTransformer(conn);
            transformer.transformAndLoadToCountryTables();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}