package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.model.command.CreatePatientCommand;
import pl.kurs.model.command.UpdatePatientCommand;
import pl.kurs.model.dto.PatientDto;
import pl.kurs.model.entity.Patient;
import pl.kurs.mapper.PatientToPatientDtoMapper;
import pl.kurs.service.PatientService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientToPatientDtoMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto save(@RequestBody @Valid CreatePatientCommand command) {
        Patient patient = patientService.save(command);
        return mapper.map(patient);
    }

    @GetMapping("/{id}")
    public PatientDto getById(@PathVariable long id) {
        Patient patient = patientService.getById(id);
        return mapper.map(patient);
    }

    @GetMapping
    public List<PatientDto> getAll() {
        List<Patient> patients = patientService.getAll();
        return mapper.mapList(patients);
    }

    @PutMapping("/{id}")
    public PatientDto edit(@PathVariable long id, @RequestBody @Valid UpdatePatientCommand command) {
        Patient patient = patientService.edit(id, command);
        return mapper.map(patient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remove(@PathVariable long id) {
        patientService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
