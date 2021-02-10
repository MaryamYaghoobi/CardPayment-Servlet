package ir.dotin.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "LONG", nullable = false, unique = true)
    public long id;
    @Column(name = "permission", columnDefinition = "VARCHAR(255)")
    private String permission;
    @Column(name = "status", columnDefinition = "BOOLEAN")
    private String status;
    @OneToMany()
    @JoinColumn(name = "employeeId")
    private Set<Employee> employees = new HashSet<Employee>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

}
