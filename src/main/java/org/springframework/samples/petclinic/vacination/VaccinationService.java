package org.springframework.samples.petclinic.vacination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.PetType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VaccinationService {

    @Autowired
    private VaccinationRepository vaccinationRepository;

    public List<Vaccination> getAll(){
        List<Vaccination> vaccinations = vaccinationRepository.findAll();
        return vaccinations;
    }

    public List<Vaccine> getAllVaccines(){
        List<Vaccine> vaccines = vaccinationRepository.findAllVaccines();
        return vaccines;
    }

    public Vaccine getVaccine(String typeName) {
        Vaccine vaccine = vaccinationRepository.findVaccineByName(typeName);
        return vaccine;
    }

    @Transactional(rollbackFor = UnfeasibleVaccinationException.class)
    public Vaccination save(Vaccination p) throws UnfeasibleVaccinationException {
        PetType vaccinationPetType = p.getVaccinatedPet().getType();
        PetType vaccinePetType = p.getVaccine().getPetType();
        if (vaccinationPetType!=vaccinePetType) {
            throw new UnfeasibleVaccinationException();
        } else {
            vaccinationRepository.save(p);
            return p;
        }
    }

    
}
