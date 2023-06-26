package pl.kurs.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "visit")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    private LocalDateTime date;
    private int lengthInMinutes;
    @Enumerated(value = EnumType.STRING)
    private AcceptVisit acceptVisit = AcceptVisit.NIEZAAKCEPTOWANA;

    public Visit(Doctor doctor, Patient patient, LocalDateTime date, int lengthInMinutes) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.lengthInMinutes = lengthInMinutes;
    }
}
