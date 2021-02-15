
package ir.dotin.entity;

import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity(name = "Leave")
@Table(name = "Leave")
@SelectBeforeUpdate
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "LONG", nullable = false, unique = true)
    public long id;
    @Column(name = "leaveToDate", columnDefinition = "DATE")
    private LocalDate leaveToDate;
    @Column(name = "leaveFromDate", columnDefinition = "DATE")
    private LocalDate leaveFromDate;
    @Column(name = "reason", columnDefinition = "VARCHAR(255)")
    private String reason;
    @ManyToOne()
    @JoinColumn(name = "leaveStatus")
    private CategoryElement leaveStatus;
    @ManyToOne()
    @JoinColumn(name = "employeeId")
    private Employee employee;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getLeaveToDate() {
        return leaveToDate;
    }

    public void setLeaveToDate(LocalDate leaveToDate) {
        this.leaveToDate = leaveToDate;
    }

    public LocalDate getLeaveFromDate() {
        return leaveFromDate;
    }

    public void setLeaveFromDate(LocalDate leaveFromDate) {
        this.leaveFromDate = leaveFromDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public CategoryElement getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(CategoryElement leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


}