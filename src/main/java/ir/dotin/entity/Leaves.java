
package ir.dotin.entity;

import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;


@Entity(name = "Leaves")
@Table(name = "t_Leaves")
@SelectBeforeUpdate
public class Leaves extends Common {

    @Column(name = "c_leaveToDate", columnDefinition = "VARCHAR(255)")
    private String leaveToDate;
    @Column(name = "c_leaveFromDate", columnDefinition = "VARCHAR(255)")
    private String leaveFromDate;
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