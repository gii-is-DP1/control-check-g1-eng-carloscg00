package org.springframework.samples.petclinic.vacination;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VaccinationRepository extends CrudRepository<Vaccination, Integer> {
    
    @Query("SELECT v FROM Vaccination v")
    List<Vaccination> findAll();

    @Query("SELECT v FROM Vaccine v")
    List<Vaccine> findAllVaccines();

    @Query("SELECT v FROM Vaccine v WHERE v.name = :name")
    Vaccine findVaccineByName(String name);

    Optional<Vaccination> findById(int id);

    Vaccination save(Vaccination p);
}
