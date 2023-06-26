package pl.kurs.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "doctor")
@SQLDelete(sql = "UPDATE doctor SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Doctor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private long id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String nip;

    @OneToMany(mappedBy = "doctor")
    private List<Visit> visits = new ArrayList<>();
    private Boolean deleted = Boolean.FALSE;

    public Doctor(String name, String surname, String nip) {
        this.name = name;
        this.surname = surname;
        this.nip = nip;
    }
}