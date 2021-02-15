package ir.dotin.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "CategoryElement")
@Table(name = "CategoryElement")
public class CategoryElement {
    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "code", columnDefinition = "VARCHAR(255)")
    private String code;

    @OneToMany(mappedBy = "CategoryElement")
    private Set<Category> Category;
    @OneToMany(mappedBy = "CategoryElement")
    private Set<Employee> role;
    @OneToMany(mappedBy = "CategoryElement")
    private Set<Employee> employeeStatus;
    @OneToMany(mappedBy = "CategoryElement")
    private Set<Employee> leaveStatus;
    public Set<Employee> getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(Set<Employee> employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
    public Set<Employee> getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(Set<Employee> leaveStatus) {
        this.leaveStatus = leaveStatus;
    }


    public Set<Category> getCategory() {
        return Category;
    }

    public void setCategory(Set<Category> category) {
        Category = category;
    }

    public Set<Employee> getRole() {
        return role;
    }

    public void setRole(Set<Employee> role) {
        this.role = role;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CategoryElement() {
    }

}
