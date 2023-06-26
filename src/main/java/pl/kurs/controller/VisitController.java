package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.kurs.mapper.VisitToVisitDtoMapper;
import pl.kurs.model.command.AcceptVisitCommand;
import pl.kurs.model.command.CreateVisitCommand;
import pl.kurs.model.dto.VisitDto;
import pl.kurs.model.entity.Visit;
import pl.kurs.service.VisitService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/visit")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;
    private final VisitToVisitDtoMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitDto save(@RequestBody @Valid CreateVisitCommand command) {
        Visit visit = visitService.save(command);
        return mapper.map(visit);
    }

    @PutMapping("/{id}")
    public VisitDto edit(@PathVariable long id, @RequestBody @Valid AcceptVisitCommand command) {
        Visit visit = visitService.acceptVisit(id, command);
        return mapper.map(visit);
    }

}
