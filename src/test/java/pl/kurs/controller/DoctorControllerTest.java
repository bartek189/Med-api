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
import pl.kurs.model.command.CreateDoctorCommand;
import pl.kurs.model.command.UpdateDoctorCommand;
import pl.kurs.model.dto.DoctorDto;
import pl.kurs.model.entity.Doctor;
import pl.kurs.repository.DoctorRepository;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DoctorControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void init() {
        doctorRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void shouldSaveDoctor() {
        CreateDoctorCommand command = new CreateDoctorCommand("A", "B", "5555555555");

        String json = objectMapper.writeValueAsString(command);

        String response = mvc.perform(post("/api/v1/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        DoctorDto result = objectMapper.readValue(response, DoctorDto.class);

        assertEquals(1, doctorRepository.findAll().size());
        assertEquals("A", result.getName());
        assertEquals("B", result.getSurname());
        assertEquals("5555555555", result.getNip());

    }

    @SneakyThrows
    @Test
    void shouldGetAll() {
        Doctor doctor = doctorRepository.save(new Doctor("X", "Z", "2987654321"));

        String response = mvc.perform(get("/api/v1/doctor")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<DoctorDto> doctorDtos = objectMapper.readValue(response, new TypeReference<List<DoctorDto>>() {
        });
        assertEquals(1, doctorDtos.size());

        DoctorDto result = doctorDtos.get(0);
        assertEquals("X", result.getName());
        assertEquals("Z", result.getSurname());
        assertEquals("2987654321", result.getNip());

    }

    @SneakyThrows
    @Test
    void shouldGetDoctorBYId() {
        Doctor doctor = doctorRepository.save(new Doctor("V", "R", "1111111111"));

        String response = mvc.perform(get("/api/v1/doctor/" + doctor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DoctorDto result = objectMapper.readValue(response, DoctorDto.class);

        assertEquals("V", result.getName());
        assertEquals("R", result.getSurname());
        assertEquals("1111111111", result.getNip());

    }

    @SneakyThrows
    @Test
    void shouldRemoveById() {
        Doctor doctor = doctorRepository.save(new Doctor("V", "R", "2222222222"));

        mvc.perform(delete("/api/v1/doctor/" + doctor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertEquals(0, doctorRepository.findAll().size());

    }

    @SneakyThrows
    @Test
    void shouldEditDoctor() {
        Doctor doctor = doctorRepository.save(new Doctor("V", "R", "3333333333"));

        UpdateDoctorCommand command = new UpdateDoctorCommand("XXX", "OOO", "3333333333");
        String json = objectMapper.writeValueAsString(command);

        String response = mvc.perform(put("/api/v1/doctor/" + doctor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DoctorDto result = objectMapper.readValue(response, DoctorDto.class);

        assertEquals("XXX", result.getName());
        assertEquals("OOO", result.getSurname());
        assertEquals("3333333333", result.getNip());
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("provideCreateDoctorCommand")
    void shouldFailValidation(CreateDoctorCommand command) {
        String json = objectMapper.writeValueAsString(command);

        mvc.perform(post("/api/v1/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> provideCreateDoctorCommand() {
        return Stream.of(
                Arguments.of(new CreateDoctorCommand(null, "XXX", "9999999999")),
                Arguments.of(new CreateDoctorCommand("ZZZ", null, "0000000000")),
                Arguments.of(new CreateDoctorCommand("ZZZ", "XXX", "1"))
        );
    }
}