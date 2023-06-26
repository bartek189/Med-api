package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.kurs.model.command.CreatePatientCommand;
import pl.kurs.model.command.UpdatePatientCommand;
import pl.kurs.model.entity.Patient;
import pl.kurs.repository.PatientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;


    public Patient save(CreatePatientCommand command) {
        return patientRepository.save(new Patient(command.getName(), command.getSurname(), command.getPesel(), command.getEmail()));
    }

    public void remove(long id) {
        patientRepository.deleteById(id);
    }

    public List<Patient> getAll() {
        return patientRepository.findAllPatients(PageRequest.of(0, 10));
    }

    public Patient getById(long id) {
        return patientRepository.findById(id).get();
    }

    public Patient edit(long id, UpdatePatientCommand command) {
        Patient edit = patientRepository.findById(id).get();
        edit.setName(command.getName());
        edit.setSurname(command.getSurname());
        edit.setPesel(command.getPesel());
        edit.setEmail(command.getEmail());


        return patientRepository.save(edit);
    }
}
