package pl.kurs.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kurs.model.entity.Doctor;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select d from Doctor d where d.id = ?1")
    Optional<Doctor> findByIdPessimist(long id);

    @Query("select d from Doctor d")
    List<Doctor> findAllDoctor(Pageable page);
}
