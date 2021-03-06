package ir.dotin.entity;

import javax.persistence.*;
import java.util.List;


@Entity(name = "CategoryElement")
@Table(name = "t_CategoryElement")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class CategoryElement extends entity {

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
