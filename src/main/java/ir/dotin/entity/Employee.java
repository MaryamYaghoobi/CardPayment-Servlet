package ir.dotin.entity;

import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;


@Entity(name = "Employee")
@Table(name = "t_Employee")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SelectBeforeUpdate
public class Employee extends ir.dotin.entity.Entity {

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
    @Column(name = "c_fatherName", columnDefinition = "VARCHAR(255)")
    private String fatherName;
    @ManyToOne()
    @JoinColumn(name = "c_manager")
    private Employee manager;
    @OneToMany(mappedBy = "manager")
    private List<Employee> employees;
    @ManyToOne()
    @JoinColumn(name = "c_role")
    private CategoryElement role;
    @ManyToOne()
    @JoinColumn(name = "c_gender")
    private CategoryElement gender;
    @OneToMany()
    @JoinColumn(name = "c_employeeId")
    private List<Leaves> leaveList;
    @OneToMany()
    @JoinColumn(name = "c_emailSenderId")
    private List<Email> sentEmails;

    public List<Email> getSentEmails() {
        return sentEmails;
    }

    public void setSentEmails(List<Email> sentEmails) {
        this.sentEmails = sentEmails;
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

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public CategoryElement getGender() {
        return gender;
    }

    public void setGender(CategoryElement gender) {
        this.gender = gender;
    }


    public List<Leaves> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(List<Leaves> leaveList) {
        this.leaveList = leaveList;
    }

    public Employee() {
    }

    public Employee(long id, String firstName, String lastName, String fatherName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.email = email;


    }

    public Employee(String firstName, String lastName, String fatherName, String email, String dateOfBirth, CategoryElement gender, CategoryElement role, String username, String password, Employee manager) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.role = role;
        //   this.employeeStatus = employeeStatus;
        this.username = username;
        this.password = password;
        this.manager = manager;

    }


    public Employee(String firstName, String lastName, String username) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public Employee(long id, String firstName, String lastName, String email, String fatherName, CategoryElement employeeStatus, Employee manager) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.fatherName = fatherName;
        //  this.employeeStatus = employeeStatus;
        this.manager = manager;
    }


}