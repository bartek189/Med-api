package pl.kurs.controller;

import com.fasterxml.jackson.core.type.TypeReference;
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
import pl.kurs.model.command.CreatePatientCommand;
import pl.kurs.model.command.UpdatePatientCommand;
import pl.kurs.model.dto.PatientDto;
import pl.kurs.model.entity.Patient;
import pl.kurs.repository.PatientRepository;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    public void init() {
        patientRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    public void shouldSaveNewPatient() {
        CreatePatientCommand patientCommand = new CreatePatientCommand("A", "B", "11111111111", "abcdf@abcd.com");
        String json = mapper.writeValueAsString(patientCommand);

        String response = mvc.perform(post("/api/v1/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        PatientDto patientDto = mapper.readValue(response, PatientDto.class);
        assertEquals(1, patientRepository.findAll().size());
        assertEquals("A", patientDto.getName());
        assertEquals("B", patientDto.getSurname());
        assertEquals("11111111111", patientDto.getPesel());
        assertEquals("abcdf@abcd.com", patientDto.getEmail());

    }

    @SneakyThrows
    @Test
    public void shouldGetAll() {
        Patient patient = patientRepository.save(new Patient("XXX", "ZZZ", "12121212121", "XXXX@XXX.com"));


        String response = mvc.perform(get("/api/v1/patient")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<PatientDto> patientDtos = mapper.readValue(response, new TypeReference<List<PatientDto>>() {
        });
        PatientDto result = patientDtos.get(0);

        assertEquals(1, patientDtos.size());
        assertEquals("XXX", result.getName());
        assertEquals("ZZZ", result.getSurname());
        assertEquals("12121212121", result.getPesel());
        assertEquals("XXXX@XXX.com", result.getEmail());
    }

    @SneakyThrows
    @Test
    void shouldGetPatientById() {
        Patient patient = patientRepository.save(new Patient("GGG", "L", "09876543211", "ACDE@AAAA.com"));

        String response = mvc.perform(get("/api/v1/patient/" + patient.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PatientDto result = mapper.readValue(response, PatientDto.class);

        assertEquals("GGG", result.getName());
        assertEquals("L", result.getSurname());
        assertEquals("09876543211", result.getPesel());
        assertEquals("ACDE@AAAA.com", result.getEmail());

    }

    @SneakyThrows
    @Test
    void shouldRemovePatientById() {
        Patient patient = patientRepository.save(new Patient("GGG", "L", "09876643211", "ACDE@BAAA.com"));


        mvc.perform(delete("/api/v1/patient/" + patient.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertEquals(0, patientRepository.findAll().size());
    }

    @SneakyThrows
    @Test
    void shouldEditPatient() {
        Patient patient = patientRepository.save(new Patient("GGG", "L", "09876943211", "ACDE@BALA.com"));

        UpdatePatientCommand command = new UpdatePatientCommand("XXX", "ZZZ", "09876943211", "ACDE@BALA.com");
        String json = mapper.writeValueAsString(command);
        String response = mvc.perform(put("/api/v1/patient/" + patient.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PatientDto result = mapper.readValue(response, PatientDto.class);

        assertEquals("XXX", result.getName());
        assertEquals("ZZZ", result.getSurname());
        assertEquals("09876943211", result.getPesel());
        assertEquals("ACDE@BALA.com", result.getEmail());

    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("provideCreatePatientCommand")
    void shouldFailValidationCreatePatient(CreatePatientCommand command) {
        String json = mapper.writeValueAsString(command);

        mvc.perform(post("/api/v1/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> provideCreatePatientCommand() {
        return Stream.of(
                Arguments.of(new CreatePatientCommand(null, "XXX", "12345678901", "abcd@abcd.com")),
                Arguments.of(new CreatePatientCommand("AAA", null, "12345678901", "abcd@abcd.com")),
                Arguments.of(new CreatePatientCommand("AAA", "XXX", "1", "abcd@abcd.com")),
                Arguments.of(new CreatePatientCommand("AAA", "XXX", "12345678901", "x")));
    }


}