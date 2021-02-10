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
public class Employee implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "LONG", nullable = false, unique = true)
    public long id;
    @Column(name = "firstName", columnDefinition = "VARCHAR(255)")
    private String firstName;
    @Column(name = "lastName", columnDefinition = "VARCHAR(255)")
    private String lastName;
    @Column(name = "fatherName", columnDefinition = "VARCHAR(255)")
    private String fatherName;
    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    private String email;
    @Temporal(TemporalType.DATE)
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Column(name = "username", columnDefinition = "VARCHAR(255)")
    private String username;
    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    private String password;
    @Column(name = "roles", columnDefinition = "VARCHAR(255)")
    private String roles;
    @Column(name = "activityStatus", columnDefinition = "BOOLEAN")
    private String activityStatus;
    @ManyToOne()
    @JoinColumn(name = "manager")
    private Employee manager;
    @OneToMany(mappedBy = "manager")
    private Set<Employee> employees = new HashSet<Employee>();
    @OneToMany()
    @JoinColumn(name = "emailSenderId")
    private Set<Email> sentEmails = new HashSet<Email>();

    @OneToMany()
    @JoinColumn(name = "employeeId")
    private Set<EmployeeLeave> leaveList = new HashSet<EmployeeLeave>();

    @OneToMany()
    @JoinColumn(name = "employeeId")
    private Set<Registration> registrationList = new HashSet<Registration>();
    @OneToMany()
    @JoinColumn(name = "employeeId")
    private Set<Permission> permissionsList = new HashSet<Permission>();

    @OneToMany(mappedBy = "Employee")
    private Set<Roles> role;
    @OneToMany(mappedBy = "Employee")
    private Set<Permission> permissions;
    @OneToOne
    @JoinColumn(name="employeeLeaveId")
    private EmployeeLeave employeeLeave;


    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public EmployeeLeave getEmployeeLeave() {
        return employeeLeave;
    }

    public void setEmployeeLeave(EmployeeLeave employeeLeave) {
        this.employeeLeave = employeeLeave;
    }


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

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
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

    public Set<Email> getSentEmails() {
        return sentEmails;
    }

    public void setSentEmails(Set<Email> sentEmails) {
        this.sentEmails = sentEmails;
    }

    public Set<EmployeeLeave> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(Set<EmployeeLeave> leaveList) {
        this.leaveList = leaveList;
    }

    public Set<Registration> getRegistrationList() {
        return registrationList;
    }

    public void setRegistrationList(Set<Registration> registrationList) {
        this.registrationList = registrationList;
    }

    public Set<Permission> getPermissionsList() {
        return permissionsList;
    }

    public void setPermissionsList(Set<Permission> permissionsList) {
        this.permissionsList = permissionsList;
    }

    public Set<Roles> getRole() {
        return role;
    }

    public void setRole(Set<Roles> role) {
        this.role = role;
    }

}
