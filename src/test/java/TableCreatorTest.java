import org.junit.jupiter.api.Test;
import rahaman.ronit.TableCreator;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TableCreatorTest extends DatabaseBase {

    @Test
    public void testStagingTableCreation() throws SQLException {
        TableCreator creator = new TableCreator(connection);
        creator.createStagingTable();

        ResultSet resultSet = connection.getMetaData().getTables(null, null, "STAGING_CUSTOMERS", null);
        assertTrue(resultSet.next(), "Staging_Customers table should be created");
    }

    @Test
    public void testCountryTableCreation() throws SQLException {
        TableCreator creator = new TableCreator(connection);
        creator.createCountryTable("INDIA");

        ResultSet resultSet = connection.getMetaData().getTables(null, null, "TABLE_INDIA", null);
        assertTrue(resultSet.next(), "Table_INDIA table should be created");
    }
}
