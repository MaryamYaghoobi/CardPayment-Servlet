
package ir.dotin.entity;

import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.List;


@Entity(name = "Employee")
@Table(name = "t_Employee")
@SelectBeforeUpdate
public class Employee extends Common {

    @Column(name = "c_firstName", columnDefinition = "VARCHAR(255)")
    private String firstName;
    @Column(name = "c_lastName", columnDefinition = "VARCHAR(255)")
    private String lastName;
    @Column(name = "c_email", columnDefinition = "VARCHAR(255)")
    private String email;
    @Column(name = "c_dateOfBirth", columnDefinition = "VARCHAR(255)")
    private String dateOfBirth;
    @Column(name = "c_username", columnDefinition = "VARCHAR(255)")
    private String username;
    @Column(name = "c_password", columnDefinition = "VARCHAR(255)")
    private String password;
    @ManyToOne()
    @JoinColumn(name = "c_manager")
    private Employee manager;
    @OneToMany(mappedBy = "manager")
    private List<Employee> employees;
    @ManyToOne()
    @JoinColumn(name = "c_role")
    private CategoryElement role;
    @ManyToOne()
    @JoinColumn(name = "c_employeeStatus")
    private CategoryElement employeeStatus;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public CategoryElement getRole() {
        return role;
    }

    public void setRole(CategoryElement role) {
        this.role = role;
    }

    public CategoryElement getStatus() {
        return employeeStatus;
    }

    public void setStatus(CategoryElement employeeStatus) {
        this.employeeStatus = employeeStatus;
    }


}