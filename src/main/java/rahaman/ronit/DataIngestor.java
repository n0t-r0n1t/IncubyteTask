package rahaman.ronit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DataIngestor {
    private final Connection conn;

    public DataIngestor(Connection connection) {
        this.conn = connection;
    }

    public void loadToStaging(List<Customer> customers) throws SQLException {
        String insertSQL = """
                INSERT INTO Staging_Customers 
                (Name, Cust_Id, Open_Dt, Consul_Dt, VAC_ID, DR_Name, State, Country, DOB, Is_Active)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;


        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        for (Customer customer : customers) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getCustomerId());
            pstmt.setDate(3, java.sql.Date.valueOf(customer.getOpenDate()));
            pstmt.setDate(4, customer.getLastConsultedDate() == null ? null : java.sql.Date.valueOf(customer.getLastConsultedDate()));
            pstmt.setString(5, customer.getVaccinationId());
            pstmt.setString(6, customer.getDoctorName());
            pstmt.setString(7, customer.getState());
            pstmt.setString(8, customer.getCountry());
            pstmt.setDate(9, java.sql.Date.valueOf(customer.getDob()));
            pstmt.setString(10, customer.isActive() ? "A" : "I");
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        System.out.println("Data loaded to staging table successfully.");

    }
}

