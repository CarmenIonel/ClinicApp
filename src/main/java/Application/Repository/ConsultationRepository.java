package Application.Repository;

import Application.Model.Consultation;
import Application.Model.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Ionel Carmen on 5/2/2017.
 */
public interface ConsultationRepository extends JpaRepository<Consultation, Integer>
{
    public Consultation findByPacientAndDateAndOraI(Pacient pacient,Date date,Date oraI);
    public Consultation findByPacientAndDate(Pacient pacient,Date date);
    public List<Consultation> findAll();
    public List<Consultation> findByPacient(Pacient pacient);
}
