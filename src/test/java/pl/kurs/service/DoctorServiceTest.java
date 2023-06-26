package pl.kurs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import pl.kurs.model.command.CreateDoctorCommand;
import pl.kurs.model.command.UpdateDoctorCommand;
import pl.kurs.model.entity.Doctor;
import pl.kurs.repository.DoctorRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DoctorServiceTest {

    private DoctorService doctorService;
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void init() {
        doctorRepository = Mockito.mock(DoctorRepository.class);
        doctorService = new DoctorService(doctorRepository);
    }

    @Test
    void shouldReturnOneDoctor() {
        List<Doctor> doctors = List.of(new Doctor("A", "B", "1234567890"));
        Mockito.when(doctorRepository.findAllDoctor(PageRequest.of(0, 10))).thenReturn(doctors);

        List<Doctor> all = doctorService.getAll();

        assertEquals(1, all.size());
        assertEquals("A", all.get(0).getName());
        assertEquals("B", all.get(0).getSurname());
        assertEquals("1234567890", all.get(0).getNip());

    }

    @Test
    void shouldReturnDoctorById() {
        Doctor doctor = new Doctor("X", "G", "1111111111");
        Mockito.when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        Doctor result = doctorService.getById(1L);

        assertEquals("X", result.getName());
        assertEquals("G", result.getSurname());
        assertEquals("1111111111", result.getNip());
    }

    @Test
    void shouldAddPatient() {
        CreateDoctorCommand doctorCommand = new CreateDoctorCommand("AAA", "BB", "0987654321");

        doctorService.create(doctorCommand);

        ArgumentCaptor<Doctor> argumentCaptor = ArgumentCaptor.forClass(Doctor.class);
        Mockito.verify(doctorRepository).save(argumentCaptor.capture());
        Doctor result = argumentCaptor.getValue();

        assertEquals("AAA", result.getName());
        assertEquals("BB", result.getSurname());
        assertEquals("0987654321", result.getNip());

    }

    @Test
    void shouldDeleteDoctor() {
        doctorService.delete(1L);

        Mockito.verify(doctorRepository).deleteById(1L);
    }

    @Test
    void shouldEditDoctor() {
        Doctor doctor = new Doctor("Z", "L", "2222222222");
        UpdateDoctorCommand doctorCommand = new UpdateDoctorCommand("P", "L", "2222222222");
        Mockito.when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        doctorService.edit(1L, doctorCommand);

        ArgumentCaptor<Doctor> argumentCaptor = ArgumentCaptor.forClass(Doctor.class);
        Mockito.verify(doctorRepository).save(argumentCaptor.capture());
        Doctor result = argumentCaptor.getValue();

        assertEquals("P", result.getName());
        assertEquals("L", result.getSurname());
        assertEquals("2222222222", result.getNip());


    }
}