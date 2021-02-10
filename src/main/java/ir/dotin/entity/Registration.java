package ir.dotin.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class Registration implements Serializable {
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
    @Column(name = "username", columnDefinition = "VARCHAR(255)")
    private String username;
    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    private String password;
    @Column(name = "address", columnDefinition = "VARCHAR(255)")
    private String address;
    @Column(name = "activityStatus", columnDefinition = "BOOLEAN")
    private String activityStatus;
    @Column(name = "managerStatus", columnDefinition = "BOOLEAN")
    private String managerStatus;
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;
    @Column(name = "roles", columnDefinition = "VARCHAR(255)")
    private String roles;
    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    private Roles role;

    @OneToOne(mappedBy = "registration")
    private Leave leave;

    @ManyToOne
    @JoinColumn(name="employeeLeaveId", nullable=false)
    private EmployeeLeave employeeLeave;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getManagerStatus() {
        return managerStatus;
    }

    public void setManagerStatus(String managerStatus) {
        this.managerStatus = managerStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Leave getLeave() {
        return leave;
    }

    public void setLeave(Leave leave) {
        this.leave = leave;
    }

    public EmployeeLeave getEmployeeLeave() {
        return employeeLeave;
    }

    public void setEmployeeLeave(EmployeeLeave employeeLeave) {
        this.employeeLeave = employeeLeave;
    }


    }