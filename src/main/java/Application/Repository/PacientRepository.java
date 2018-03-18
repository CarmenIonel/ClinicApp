package Application.Repository;

import Application.Model.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ionel Carmen on 5/2/2017.
 */
public interface PacientRepository extends JpaRepository<Pacient, Integer>
{
    public Pacient findByName(String name);
    public Pacient findByCnp(String cnp);
}
