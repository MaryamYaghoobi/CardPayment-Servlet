package ir.co.isc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "LONG", nullable = false, unique = true)
    public long id;
    @Version
    private Long version;
}
