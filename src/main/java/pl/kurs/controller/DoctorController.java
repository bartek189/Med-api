package pl.kurs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.mapper.DoctorToDoctorDtoMapper;
import pl.kurs.model.command.CreateDoctorCommand;
import pl.kurs.model.command.UpdateDoctorCommand;
import pl.kurs.model.dto.DoctorDto;
import pl.kurs.model.entity.Doctor;
import pl.kurs.service.DoctorService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorToDoctorDtoMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto create(@RequestBody @Valid CreateDoctorCommand command) {
        Doctor doctor = doctorService.create(command);
        return mapper.map(doctor);
    }

    @GetMapping("/{id}")
    public DoctorDto getById(@PathVariable long id) {
        Doctor doctor = doctorService.getById(id);
        return mapper.map(doctor);
    }

    @GetMapping
    public List<DoctorDto> getAll() {
        List<Doctor> doctors = doctorService.getAll();
        return mapper.mapList(doctors);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public DoctorDto edit(@PathVariable long id, @RequestBody @Valid UpdateDoctorCommand command) {
        Doctor doctor = doctorService.edit(id, command);
        return mapper.map(doctor);
    }
}
