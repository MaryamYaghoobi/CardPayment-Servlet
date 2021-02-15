
package ir.dotin.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Email")
@Table(name = "Email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "LONG", nullable = false, unique = true)
    public long id;

    @Column(name = "context", columnDefinition = "VARCHAR(255)")
    private String context;

    @Column(name = "filePath", columnDefinition = "VARCHAR(255)")
    private String filePath;

    @OneToMany()
    @JoinTable(name = "EmployeeEmail",
            joinColumns = {@JoinColumn(name = "receiveEmailId")},
            inverseJoinColumns = {@JoinColumn(name = "receiverId")})
    private Set<Employee> receiverEmployees = new HashSet<Employee>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Set<Employee> getReceiverEmployees() {
        return receiverEmployees;
    }

    public void setReceiverEmployees(Set<Employee> receiverEmployees) {
        this.receiverEmployees = receiverEmployees;
    }


}