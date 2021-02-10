package ir.dotin.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EmployeeLeave implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "LONG", nullable = false, unique = true)
    public long id;
    @Column(name = "details", columnDefinition = "VARCHAR(255)")
    private String details;
    @Column(name = "requestStatus", columnDefinition = "BOOLEAN")
    private String requestStatus;
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;
    @Column(name = "response", columnDefinition = "VARCHAR(255)")
    private String response;
    @OneToOne(mappedBy="employeeLeave")
    private Employee employee;
    @OneToMany(mappedBy="employeeLeave")
    private Set<Registration> registrations;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }


}
