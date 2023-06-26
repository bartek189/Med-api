package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.kurs.model.command.CreateDoctorCommand;
import pl.kurs.model.command.UpdateDoctorCommand;
import pl.kurs.model.entity.Doctor;
import pl.kurs.repository.DoctorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository repository;

    public Doctor create(CreateDoctorCommand command) {
        return repository.save(new Doctor(command.getName(), command.getSurname(), command.getNip()));
    }

    public Doctor getById(long id) {
        return repository.findById(id).get();
    }

    public List<Doctor> getAll() {
        return repository.findAllDoctor(PageRequest.of(0, 10));
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public Doctor edit(long id, UpdateDoctorCommand command) {
        Doctor doctor = repository.findById(id).get();
        doctor.setName(command.getName());
        doctor.setSurname(command.getSurname());
        doctor.setNip(command.getNip());

        return repository.save(doctor);
    }
}
