package pl.kurs.model.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "patient")
@SQLDelete(sql = "UPDATE patient SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private long id;
    private String name;
    private String surname;

    @Column(unique = true)
    private String pesel;
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "patient")
    private List<Visit> visits = new ArrayList<>();

    private Boolean deleted = Boolean.FALSE;

    public Patient(String name, String surname, String pesel, String email) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.email = email;
    }
}
