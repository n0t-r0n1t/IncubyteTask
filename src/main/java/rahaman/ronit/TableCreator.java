package rahaman.ronit;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {
    private final Connection conn;

    public TableCreator(Connection connection) {
        this.conn = connection;
    }

    public void createStagingTable() throws SQLException {
        String createStagingSQL = """
                CREATE TABLE IF NOT EXISTS Staging_Customers (
                    Name VARCHAR(255) NOT NULL,
                    Cust_Id VARCHAR(18) NOT NULL,
                    Open_Dt DATE NOT NULL,
                    Consul_Dt DATE,
                    VAC_ID CHAR(5),
                    DR_Name VARCHAR(255),
                    State CHAR(5),
                    Country CHAR(5),
                    DOB DATE NOT NULL,
                    Is_Active CHAR(1)
                );
                """;

        Statement stmt = conn.createStatement();
        stmt.execute(createStagingSQL);
        System.out.println("Staging table created successfully.");
    }

    public void createCountryTable(String country) throws SQLException {
        String createCountryTableSQL = String.format("""
                CREATE TABLE IF NOT EXISTS Table_%s (
                    Name VARCHAR(255) NOT NULL,
                    Cust_Id VARCHAR(18) NOT NULL PRIMARY KEY,
                    Open_Dt DATE NOT NULL,
                    Consul_Dt DATE,
                    VAC_ID CHAR(5),
                    DR_Name VARCHAR(255),
                    State CHAR(5),
                    Country CHAR(5),
                    DOB DATE NOT NULL,
                    Is_Active CHAR(1),
                    Age INT,
                    Over_Thirty_Days INT
                );
                """, country);


        Statement stmt = conn.createStatement();
        stmt.execute(createCountryTableSQL);
        System.out.println("Country table for " + country + " created successfully.");
    }
}
