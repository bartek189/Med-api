package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.model.command.AcceptVisitCommand;
import pl.kurs.model.command.CreateVisitCommand;
import pl.kurs.model.entity.AcceptVisit;
import pl.kurs.model.entity.Doctor;
import pl.kurs.model.entity.Patient;
import pl.kurs.model.entity.Visit;
import pl.kurs.repository.DoctorRepository;
import pl.kurs.repository.PatientRepository;
import pl.kurs.repository.VisitRepository;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Visit save(CreateVisitCommand command) {
        Doctor doctor = doctorRepository.findByIdPessimist(command.getDoctorId()).get();
        Patient patient = patientRepository.findByIdPessimist(command.getPatientId()).get();
        conditionCreateVisit(command);
        return visitRepository.save(new Visit(doctor, patient, command.getDate(), command.getLengthInMinutes()));


    }

    public Visit acceptVisit(long id, AcceptVisitCommand visitCommand) {
        Visit visit = visitRepository.findById(id).get();
        checkWhetherIsAccept(visit);
        visit.setAcceptVisit(visitCommand.getAcceptVisit());

        return visitRepository.save(visit);
    }


    private void checkWhetherIsAccept(Visit visit) {
        if (visitRepository.checkWhetherIsAccept(AcceptVisit.ZAAKCEPTOWANA, visit.getDate())) {
            throw new IllegalStateException("wizyta na ta date juz istnieje ");
        }
    }

    private void conditionCreateVisit(CreateVisitCommand command) {
        if (visitRepository.existByDoctorIdDateFromTo(command.getDoctorId(), command.getDate().plusMinutes(command.getLengthInMinutes()), command.getDate().minusMinutes(command.getLengthInMinutes()), AcceptVisit.ZAAKCEPTOWANA)) {
            throw new IllegalStateException("wizyta nie moze zostac stworzona");
        }
        if (visitRepository.existByPatientIdDateFromTo(command.getPatientId(), command.getDate(), command.getDate().plusMinutes(command.getLengthInMinutes()), AcceptVisit.ZAAKCEPTOWANA)) {
            System.out.println("wizyta nie moze zostac stworzona");
        }

    }

}
