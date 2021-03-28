
package ir.dotin.entity;

import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.Entity;
import javax.persistence.*;


@Entity(name = "Leaves")
@Table(name = "t_Leaves")
@SelectBeforeUpdate
public class Leaves extends ir.dotin.entity.Entity {

    @Column(name = "c_leaveToDate", columnDefinition = "VARCHAR(255)")
    private String leaveToDate;
    @Column(name = "c_leaveFromDate", columnDefinition = "VARCHAR(255)")
    private String leaveFromDate;
    @Column(name = "c_leaveToTime", columnDefinition = "VARCHAR(255)")
    private String leaveToTime;
    @Column(name = "c_leaveFromTime", columnDefinition = "VARCHAR(255)")
    private String leaveFromTime;
    @Column(name = "c_reason", columnDefinition = "VARCHAR(255)")
    private String reason;
    @ManyToOne()
    @JoinColumn(name = "c_leaveStatus")
    private CategoryElement leaveStatus;
    @ManyToOne()
    @JoinColumn(name = "c_employeeId")
    private Employee employee;


    public String getLeaveToDate() {
        return leaveToDate;
    }

    public void setLeaveToDate(String leaveToDate) {
        this.leaveToDate = leaveToDate;
    }

    public String getLeaveFromDate() {
        return leaveFromDate;
    }

    public void setLeaveFromDate(String leaveFromDate) {
        this.leaveFromDate = leaveFromDate;
    }

    public String getLeaveToTime() {
        return leaveToTime;
    }

    public void setLeaveToTime(String leaveToTime) {
        this.leaveToTime = leaveToTime;
    }

    public String getLeaveFromTime() {
        return leaveFromTime;
    }

    public void setLeaveFromTime(String leaveFromTime) {
        this.leaveFromTime = leaveFromTime;
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

    public Leaves(String leaveFromDate, String leaveToDate, String leaveFromTime, String leaveToTime, String reason, CategoryElement leaveStatus) {
        this.leaveFromDate = leaveFromDate;
        this.leaveToDate = leaveToDate;
        this.leaveFromTime = leaveFromTime;
        this.leaveToTime = leaveToTime;
        this.reason = reason;
        this.leaveStatus = leaveStatus;

    }
    public Leaves(){}
}
