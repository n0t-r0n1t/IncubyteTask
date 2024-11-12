import org.junit.jupiter.api.Test;
import rahaman.ronit.Customer;
import rahaman.ronit.DataIngestor;
import rahaman.ronit.DataTransformer;
import rahaman.ronit.TableCreator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataTransformerTest extends DatabaseBase {

    @Test
    public void testTransformAndLoadToCountryTable() throws SQLException {
        TableCreator creator = new TableCreator(connection);
        creator.createStagingTable();
        creator.createCountryTable("USA");

        DataIngestor ingestor = new DataIngestor(connection);
        Customer customer = new Customer("Alex", "123457", LocalDate.of(2010, 10, 12), LocalDate.of(2012, 10, 13),
                "MVD", "Paul", "SA", "USA", LocalDate.of(1987, 3, 6), true);
        ingestor.loadToStaging(List.of(customer));

        DataTransformer transformer = new DataTransformer(connection);
        transformer.transformAndLoadToCountryTables();

        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Table_USA WHERE Cust_Id = ?");
        pstmt.setString(1, "123457");
        ResultSet rs = pstmt.executeQuery();

        assertTrue(rs.next(), "Record should exist in Table_USA");
        assertEquals("Alex", rs.getString("Name"));
        assertEquals(37, rs.getInt("Age"));  // Adjust expected age based on current date
        assertTrue(rs.getBoolean("Over_Thirty_Days"), "Days since last consulted should be > 30");
    }
}
