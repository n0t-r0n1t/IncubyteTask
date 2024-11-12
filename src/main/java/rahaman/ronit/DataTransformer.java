package rahaman.ronit;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class DataTransformer {
    private final Connection conn;

    public DataTransformer(Connection connection) {
        this.conn = connection;
    }

    public void transformAndLoadToCountryTables() throws SQLException {
        Map<String, String> countryTables = new HashMap<>();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Staging_Customers");

        while (rs.next()) {
            String country = rs.getString("Country");
            String custId = rs.getString("Cust_Id");

            LocalDate dob = rs.getDate("DOB").toLocalDate();
            int age = Period.between(dob, LocalDate.now()).getYears();

            Date consulDateSql = rs.getDate("Consul_Dt");
            LocalDate consulDate = consulDateSql != null ? consulDateSql.toLocalDate() : null;
            assert consulDate != null;
            boolean overThirtyDays =  ChronoUnit.DAYS.between(consulDate, LocalDate.now()) > 30;

            if (!countryTables.containsKey(country)) {
                new TableCreator(conn).createCountryTable(country);
                countryTables.put(country, "Table_" + country);
            }

            loadToCountryTable(countryTables.get(country), rs, age, overThirtyDays);
        }
        System.out.println("Data loaded to country tables successfully.");

    }

    private void loadToCountryTable(String tableName, ResultSet rs, int age, boolean overThirtyDays) throws SQLException {
        String insertSQL = String.format("""
                INSERT INTO %s 
                (Name, Cust_Id, Open_Dt, Consul_Dt, VAC_ID, DR_Name, State, Country, DOB, Is_Active, Age, Over_Thirty_Days)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, tableName);


        PreparedStatement pstmt = conn.prepareStatement(insertSQL);

        pstmt.setString(1, rs.getString("Name"));
        pstmt.setString(2, rs.getString("Cust_Id"));
        pstmt.setDate(3, rs.getDate("Open_Dt"));
        pstmt.setDate(4, rs.getDate("Consul_Dt"));
        pstmt.setString(5, rs.getString("VAC_ID"));
        pstmt.setString(6, rs.getString("DR_Name"));
        pstmt.setString(7, rs.getString("State"));
        pstmt.setString(8, rs.getString("Country"));
        pstmt.setDate(9, rs.getDate("DOB"));
        pstmt.setString(10, rs.getString("Is_Active"));
        pstmt.setInt(11, age);
        pstmt.setBoolean(12, overThirtyDays);
        pstmt.executeUpdate();

    }
}

