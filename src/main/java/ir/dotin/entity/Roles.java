package ir.dotin.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Roles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "LONG", nullable = false, unique = true)
    public long id;
    @OneToMany(mappedBy="Roles")
    private Set<Roles> roles;
    @Column(name = "status", columnDefinition = "BOOLEAN")
    private String status;

    @OneToMany()
    @JoinColumn(name = "roleId")
    private Set<Employee> employees = new HashSet<Employee>();

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

