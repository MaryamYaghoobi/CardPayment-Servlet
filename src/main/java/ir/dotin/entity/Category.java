package ir.dotin.entity;

import javax.persistence.*;
import java.util.List;


@Entity(name = "Category")
@Table(name = "t_Category")
public class Category extends Common {

    @Column(name = "c_name", columnDefinition = "VARCHAR(255)")
    private String name;
    @ManyToOne
    @JoinColumn(name = "c_categoryElementList")
    private List<CategoryElement> categoryElementList;

    public List<CategoryElement> getCategoryElementList() {
        return categoryElementList;
    }

    public void setCategoryElementList(List<CategoryElement> categoryElementList) {
        this.categoryElementList = categoryElementList;
    }

    public String getInstance() {
        return name;
    }

    public void setInstance(String instance) {
        this.name = name;
    }

    public Category() {
    }

}
