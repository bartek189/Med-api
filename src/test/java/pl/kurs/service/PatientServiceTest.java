package pl.kurs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import pl.kurs.model.command.CreatePatientCommand;
import pl.kurs.model.command.UpdatePatientCommand;
import pl.kurs.model.entity.Patient;
import pl.kurs.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PatientServiceTest {

    private PatientService service;
    private PatientRepository repository;

    @BeforeEach
    public void init() {
        repository = Mockito.mock(PatientRepository.class);
        service = new PatientService(repository);
    }

    @Test
    void shouldSavePatient() {
        CreatePatientCommand command = new CreatePatientCommand("A", "B", "12345678900", "xxx@xxx.com");

        service.save(command);

        ArgumentCaptor<Patient> argumentCaptor = ArgumentCaptor.forClass(Patient.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
        Patient result = argumentCaptor.getValue();

        assertEquals("A", result.getName());
        assertEquals("B", result.getSurname());
        assertEquals("12345678900", result.getPesel());
        assertEquals("xxx@xxx.com", result.getEmail());
    }

    @Test
    void shouldReturnOnePatient() {
        List<Patient> list = List.of(new Patient("M", "N", "0987654321", "aaaaa@aaaa.com"));
        Mockito.when(repository.findAllPatients(PageRequest.of(0, 10))).thenReturn(list);

        List<Patient> all = service.getAll();

        assertEquals(1, all.size());
        assertEquals("M", all.get(0).getName());
        assertEquals("N", all.get(0).getSurname());
        assertEquals("0987654321", all.get(0).getPesel());
        assertEquals("aaaaa@aaaa.com", all.get(0).getEmail());

    }

    @Test
    void shouldReturnPatientById() {
        Patient patient = new Patient("P", "A", "11111111111", "aa@aa.com");
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(patient));

        Patient result = service.getById(1L);

        assertEquals("P", result.getName());
        assertEquals("A", result.getSurname());
        assertEquals("11111111111", result.getPesel());
        assertEquals("aa@aa.com", result.getEmail());

    }

    @Test
    void shouldDeletePatient() {
        service.remove(1L);

        Mockito.verify(repository).deleteById(1L);
    }

    @Test
    void shouldEditPatient() {
        Patient patient = new Patient("O", "I", "00000000000", "xxx@xxx.com");
        UpdatePatientCommand command = new UpdatePatientCommand("XXX", "YYY", "00000000000", "xxx@xxx.com");
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(patient));

        service.edit(1L, command);

        ArgumentCaptor<Patient> argumentCaptor = ArgumentCaptor.forClass(Patient.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
        Patient result = argumentCaptor.getValue();

        assertEquals("XXX", result.getName());
        assertEquals("YYY", result.getSurname());
        assertEquals("00000000000", result.getPesel());
        assertEquals("xxx@xxx.com", result.getEmail());

    }

}