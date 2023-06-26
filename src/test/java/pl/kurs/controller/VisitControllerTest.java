package pl.kurs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.kurs.model.command.AcceptVisitCommand;
import pl.kurs.model.command.CreateVisitCommand;
import pl.kurs.model.dto.VisitDto;
import pl.kurs.model.entity.AcceptVisit;
import pl.kurs.model.entity.Doctor;
import pl.kurs.model.entity.Patient;
import pl.kurs.model.entity.Visit;
import pl.kurs.repository.DoctorRepository;
import pl.kurs.repository.PatientRepository;
import pl.kurs.repository.VisitRepository;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VisitControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void init() {
        visitRepository.deleteAll();
        patientRepository.deleteAll();
        doctorRepository.deleteAll();
    }


    @SneakyThrows
    @Test
    void shouldSaveVisit() {
        Patient patient = patientRepository.save(new Patient("A", "B", "12345678901", "xxxxx@xxxx.com"));
        Doctor doctor = doctorRepository.save(new Doctor("X", "Z", "1234567891"));

        CreateVisitCommand command = new CreateVisitCommand(doctor.getId(), patient.getId(), LocalDateTime.of(2023, 11, 1, 1, 1), 90);
        String json = objectMapper.writeValueAsString(command);

        String response = mvc.perform(post("/api/v1/visit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        VisitDto result = objectMapper.readValue(response, VisitDto.class);
        assertEquals(1, visitRepository.findAll().size());

        assertEquals(LocalDateTime.of(2023, 11, 1, 1, 1), result.getDate());
        assertEquals(90, result.getLengthInMinutes());
    }

    @SneakyThrows
    @Test
    void shouldAcceptVisit() {
        Doctor doctor = doctorRepository.save(new Doctor("A", "B", "9999999999"));
        Patient patient = patientRepository.save(new Patient("B", "V", "00000000000", "aaaa@cccc,com"));
        Visit visit = visitRepository.save(new Visit(doctor, patient, LocalDateTime.of(2023, 12, 1, 1, 1), 90));

        AcceptVisitCommand command = new AcceptVisitCommand(AcceptVisit.ZAAKCEPTOWANA);
        String json = objectMapper.writeValueAsString(command);
        String response = mvc.perform(put("/api/v1/visit/" + visit.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        VisitDto result = objectMapper.readValue(response, VisitDto.class);

        assertEquals(AcceptVisit.ZAAKCEPTOWANA, result.getAcceptVisit());
        assertEquals(LocalDateTime.of(2023, 12, 1, 1, 1), result.getDate());
        assertEquals(90, result.getLengthInMinutes());

    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("provideVisitCommand")
    void shouldFailValidation(CreateVisitCommand visitCommand) {
        String json = objectMapper.writeValueAsString(visitCommand);

        mvc.perform(post("/api/v1/visit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

    }

    private static Stream<Arguments> provideVisitCommand() {
        return Stream.of(
                Arguments.of(new CreateVisitCommand(0, 0, null, 4)),
                Arguments.of(new CreateVisitCommand(0, 0, LocalDateTime.of(2021, 11, 1, 1, 1), 4)),
                Arguments.of(new CreateVisitCommand(0, 0, LocalDateTime.of(2023, 2, 2, 2, 2), 0)));
    }

}