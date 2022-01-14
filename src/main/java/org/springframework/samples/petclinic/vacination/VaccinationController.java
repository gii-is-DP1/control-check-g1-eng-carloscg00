package org.springframework.samples.petclinic.vacination;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vaccination")
public class VaccinationController {
    
    private static final String VIEWS_VACCINATION_CREATE_OR_UPDATE_FORM = "vaccination/createOrUpdateVaccinationForm";

    @Autowired
    private VaccinationService vaccinationService;

    @Autowired
    private PetService petService;

    @GetMapping("/create")
    public String initCreationVaccinationForm(ModelMap modelMap) {
        Vaccination vaccination = new Vaccination();
        List<Vaccine> vaccines = vaccinationService.getAllVaccines();
        Collection<Pet> pets = petService.findAllPets();
        modelMap.put("vaccination", vaccination);
        modelMap.put("vaccines", vaccines);
        modelMap.put("pets", pets);
        return VIEWS_VACCINATION_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/create")
	public String processCreationForm(@Valid Vaccination vaccination, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("vaccination", vaccination);
			return VIEWS_VACCINATION_CREATE_OR_UPDATE_FORM;
		}
		else {
                    try{
                    	this.vaccinationService.save(vaccination);;
                    }catch(UnfeasibleVaccinationException ex){
                        result.reject("La mascota seleccionada no puede recibir la vacuna especificada.");
                        return VIEWS_VACCINATION_CREATE_OR_UPDATE_FORM;
                    }
            return "redirect:/welcome";
		}
	}
}
