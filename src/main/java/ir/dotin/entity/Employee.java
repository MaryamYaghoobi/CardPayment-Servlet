
package ir.dotin.entity;

import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "Employee")
@Table(name = "Employee")
@SelectBeforeUpdate
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "LONG", nullable = false, unique = true)
    public long id;
    @Column(name = "firstName", columnDefinition = "VARCHAR(255)")
    private String firstName;
    @Column(name = "lastName", columnDefinition = "VARCHAR(255)")
    private String lastName;
    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    private String email;
    @Temporal(TemporalType.DATE)
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Column(name = "username", columnDefinition = "VARCHAR(255)")
    private String username;
    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    private String password;
    @ManyToOne()
    @JoinColumn(name = "manager")
    private Employee manager;
    @OneToMany(mappedBy = "manager")
    private Set<Employee> employees = new HashSet<Employee>();
    @ManyToOne()
    @JoinColumn(name = "role")
    private CategoryElement role;
    @ManyToOne()
    @JoinColumn(name = "employeeStatus")
    private CategoryElement employeeStatus;
    @OneToMany(mappedBy = "employeeId")
    private Set<Leave> leaves = new HashSet<Leave>();
    @ManyToMany(mappedBy = "employeeSenderId")
    private Set<Email> senderEmail = new HashSet<Email>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
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

    public Set<Leave> getLeaves() {
        return leaves;
    }

    public void setLeaves(Set<Leave> leaves) {
        this.leaves = leaves;
    }

    public Set<Email> getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(Set<Email> senderEmail) {
        this.senderEmail = senderEmail;
    }

}