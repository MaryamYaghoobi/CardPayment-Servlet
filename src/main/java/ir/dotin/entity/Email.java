package ir.dotin.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Email")
@Table(name = "Email")
public class Email implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "LONG", nullable = false, unique = true)
    public long id;
    @Column(name = "content", columnDefinition = "VARCHAR(255)")
    private String content;

    @Column(name = "filePath", columnDefinition = "VARCHAR(255)")
    private String filePath;

    @OneToMany()
    @JoinTable(name = "Employee_Email",
            joinColumns = {@JoinColumn(name = "receivedEmailId")},
            inverseJoinColumns = {@JoinColumn(name = "emailReceiverId")})
    private Set<Employee> receiverEmployees = new HashSet<Employee>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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



