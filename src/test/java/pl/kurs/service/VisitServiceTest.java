package pl.kurs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import pl.kurs.model.command.AcceptVisitCommand;
import pl.kurs.model.command.CreateVisitCommand;
import pl.kurs.model.entity.AcceptVisit;
import pl.kurs.model.entity.Doctor;
import pl.kurs.model.entity.Patient;
import pl.kurs.model.entity.Visit;
import pl.kurs.repository.DoctorRepository;
import pl.kurs.repository.PatientRepository;
import pl.kurs.repository.VisitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VisitServiceTest {

    private VisitService service;
    private VisitRepository visitRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void init() {
        visitRepository = Mockito.mock(VisitRepository.class);
        patientRepository = Mockito.mock(PatientRepository.class);
        doctorRepository = Mockito.mock(DoctorRepository.class);
        service = new VisitService(visitRepository, doctorRepository, patientRepository);
    }

    @Test
    void shouldSaveVisit() {
        Patient patient = new Patient("XXX", "YYY", "000000000", "aaa@aa.com");
        Doctor doctor = new Doctor("ZZZ", "III", "1111111111");

        Mockito.when(patientRepository.findByIdPessimist(0L)).thenReturn(Optional.of(patient));
        Mockito.when(doctorRepository.findByIdPessimist(0L)).thenReturn(Optional.of(doctor));

        CreateVisitCommand command = new CreateVisitCommand(doctor.getId(), patient.getId(), LocalDateTime.of(2023, 11, 11, 1, 1), 90);

        service.save(command);

        ArgumentCaptor<Visit> argumentCaptor = ArgumentCaptor.forClass(Visit.class);
        Mockito.verify(visitRepository).save(argumentCaptor.capture());
        Visit result = argumentCaptor.getValue();

        assertEquals(LocalDateTime.of(2023, 11, 11, 1, 1), result.getDate());
        assertEquals(90, result.getLengthInMinutes());

        assertEquals("XXX", result.getPatient().getName());
        assertEquals("YYY", result.getPatient().getSurname());
        assertEquals("000000000", result.getPatient().getPesel());
        assertEquals("aaa@aa.com", result.getPatient().getEmail());

        assertEquals("ZZZ", result.getDoctor().getName());
        assertEquals("III", result.getDoctor().getSurname());
        assertEquals("1111111111", result.getDoctor().getNip());

    }

    @Test
    void shouldThrowIllegalStateException() {

        IllegalStateException thrown = Assertions.assertThrows(IllegalStateException.class, () -> {


            Patient patient = new Patient("A", "B", "12345678900", "a@a.com");
            Doctor doctor = new Doctor("D", "P", "9999999999");
            Mockito.when(patientRepository.findByIdPessimist(0L)).thenReturn(Optional.of(patient));
            Mockito.when(doctorRepository.findByIdPessimist(0L)).thenReturn(Optional.of(doctor));


            Visit visit = new Visit(doctor, patient, LocalDateTime.of(2023, 2, 2, 13, 2), 40);
            visit.setAcceptVisit(AcceptVisit.ZAAKCEPTOWANA);
            List<Visit> visits = List.of(visit);
            Mockito.when(visitRepository.checkWhetherIsAccept(AcceptVisit.ZAAKCEPTOWANA, visit.getDate())).thenReturn(true);

            Visit createVisitCommand = new Visit(doctor, patient, LocalDateTime.of(2023, 2, 2, 13, 2), 90);

            AcceptVisitCommand command = new AcceptVisitCommand(AcceptVisit.ZAAKCEPTOWANA);
            Mockito.when(visitRepository.findById(1L)).thenReturn(Optional.of(createVisitCommand));
            service.acceptVisit(1L, command);
        });
        assertEquals("wizyta na ta date juz istnieje ", thrown.getMessage());
    }

    @Test
    void shouldEditAcceptVisit() {
        Patient patient = new Patient("XXX", "YYY", "11111111111", "aaa@aa.com");
        Doctor doctor = new Doctor("ZZZ", "III", "2222222222");

        Visit visit = new Visit(doctor, patient, LocalDateTime.of(2023, 4, 1, 1, 1), 80);
        Mockito.when(visitRepository.findById(1L)).thenReturn(Optional.of(visit));
        AcceptVisitCommand command = new AcceptVisitCommand(AcceptVisit.ZAAKCEPTOWANA);
        service.acceptVisit(1L, command);

        ArgumentCaptor<Visit> argumentCaptor = ArgumentCaptor.forClass(Visit.class);
        Mockito.verify(visitRepository).save(argumentCaptor.capture());
        Visit result = argumentCaptor.getValue();

        assertEquals(AcceptVisit.ZAAKCEPTOWANA, result.getAcceptVisit());
        assertEquals(80, result.getLengthInMinutes());
        assertEquals(LocalDateTime.of(2023, 4, 1, 1, 1), result.getDate());

        assertEquals("XXX", result.getPatient().getName());
        assertEquals("YYY", result.getPatient().getSurname());
        assertEquals("11111111111", result.getPatient().getPesel());
        assertEquals("aaa@aa.com", result.getPatient().getEmail());

        assertEquals("ZZZ", result.getDoctor().getName());
        assertEquals("III", result.getDoctor().getSurname());
        assertEquals("2222222222", result.getDoctor().getNip());
    }


}