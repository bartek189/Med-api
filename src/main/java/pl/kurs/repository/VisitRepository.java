package pl.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kurs.model.entity.AcceptVisit;
import pl.kurs.model.entity.Visit;

import java.time.LocalDateTime;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {


    @Query("select case when count(*) > 0 then true else false end from Visit v where v.doctor.id = ?1 and date < ?2 and date > ?3 and v.acceptVisit =?4  ")
    boolean existByDoctorIdDateFromTo(long id, LocalDateTime from, LocalDateTime to, AcceptVisit acceptVisit);

    @Query("select case when count(*) > 0 then true else false end from Visit v where v.patient.id = ?1 and date < ?2 and date > ?3 and v.acceptVisit =?4")
    boolean existByPatientIdDateFromTo(long id, LocalDateTime from, LocalDateTime to, AcceptVisit acceptVisit);

    @Query("select case when count(*) > 0 then true else false end from Visit v where v.date = ?2 and v.acceptVisit = ?1")
    boolean checkWhetherIsAccept(AcceptVisit acceptVisit, LocalDateTime date);
}