package ir.dotin.entity;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Entity(name = "CategoryElement")
@Table(name = "t_CategoryElement")
public class CategoryElement extends ir.dotin.entity.Entity {

    @Column(name = "c_name", columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "c_code", columnDefinition = "VARCHAR(255)")
    private String code;

    @OneToMany()
    @JoinColumn(name = "id")
    private List<Category> category;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
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
