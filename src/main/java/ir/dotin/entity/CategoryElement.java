package ir.dotin.entity;

import javax.persistence.*;


@Entity(name = "CategoryElement")
@Table(name = "t_CategoryElement")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class CategoryElement extends Common {

    @Column(name = "c_name", columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "c_code", columnDefinition = "VARCHAR(255)")
    private String code;

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
