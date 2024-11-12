import org.junit.jupiter.api.Test;
import rahaman.ronit.Customer;
import rahaman.ronit.DataIngestor;
import rahaman.ronit.TableCreator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataIngestorTest extends DatabaseBase {

    @Test
    public void testLoadToStaging() throws SQLException {
        TableCreator creator = new TableCreator(connection);
        creator.createStagingTable();

        DataIngestor ingestor = new DataIngestor(connection);
        Customer sampleCustomer = new Customer("Alex", "123457", LocalDate.of(2010, 10, 12), LocalDate.of(2012, 10, 13),
                "MVD", "Paul", "SA", "USA", LocalDate.of(1987, 3, 6), true);

        ingestor.loadToStaging(List.of(sampleCustomer));

        // Verify data in Staging_Customers table
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Staging_Customers WHERE Cust_Id = ?");
        pstmt.setString(1, "123457");
        ResultSet rs = pstmt.executeQuery();

        assertTrue(rs.next(), "Record should exist in Staging_Customers");
        assertEquals("Alex", rs.getString("Name"));
        assertEquals("USA", rs.getString("Country"));
        assertEquals("A", rs.getString("Is_Active"));
    }
}
