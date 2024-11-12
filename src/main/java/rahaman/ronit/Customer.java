package rahaman.ronit;

import java.time.LocalDate;

public class Customer {
    private final String name;
    private final String customerId;
    private final LocalDate openDate;
    private final LocalDate lastConsultedDate;
    private final String vaccinationId;
    private final String doctorName;
    private final String state;
    private final String country;
    private final LocalDate dob;
    private final boolean isActive;

    public Customer(String name, String customerId, LocalDate openDate, LocalDate lastConsultedDate,
                    String vaccinationId, String doctorName, String state, String country, LocalDate dob, boolean isActive) {
        this.name = name;
        this.customerId = customerId;
        this.openDate = openDate;
        this.lastConsultedDate = lastConsultedDate;
        this.vaccinationId = vaccinationId;
        this.doctorName = doctorName;
        this.state = state;
        this.country = country;
        this.dob = dob;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", customerId='" + customerId + '\'' +
                ", openDate=" + openDate +
                ", lastConsultedDate=" + lastConsultedDate +
                ", vaccinationId='" + vaccinationId + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", dob=" + dob +
                ", isActive=" + isActive +
                '}';
    }

    public boolean isActive() {
        return isActive;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getVaccinationId() {
        return vaccinationId;
    }

    public LocalDate getLastConsultedDate() {
        return lastConsultedDate;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}