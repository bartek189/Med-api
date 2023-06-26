package pl.kurs.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kurs.model.entity.Patient;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select p from Patient p where p.id = ?1")
    Optional<Patient> findByIdPessimist(long id);

    @Query("select p from Patient p")
    List<Patient> findAllPatients(Pageable page);
}
