
package ir.dotin.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Email")
@Table(name = "t_Email")
public class Email extends Common {


    @Column(name = "c_context", columnDefinition = "VARCHAR(255)")
    private String context;

    @ManyToMany()
    @JoinTable(name = "m_EmployeeEmail",
            joinColumns = {@JoinColumn(name = "c_receiveEmailId")},
            inverseJoinColumns = {@JoinColumn(name = "c_receiverId")})
    private List<Employee> receiverEmployees;
    @ManyToMany(mappedBy = "c_employeeSenderId")
    private List<Email> senderEmail;


    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }


    public List<Employee> getReceiverEmployees() {
        return receiverEmployees;
    }

    public void setReceiverEmployees(List<Employee> receiverEmployees) {
        this.receiverEmployees = receiverEmployees;
    }


}