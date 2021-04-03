package com.team11.PharmacyProject.users.patient;

import com.team11.PharmacyProject.dto.MedicineDTO;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private ModelMapper modelMapper;

    private PatientDTO convertToDto(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }

    private Patient convertToEntity(PatientDTO pharmacyDto) {
        return modelMapper.map(pharmacyDto, Patient.class);
    }

    @GetMapping(value="/all", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientDTO>> getAllPatients(){
        List<Patient> patientsResult = patientService.getAll();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient p:
                patientsResult) {
            patientDTOS.add(convertToDto(p));
        }
        return new ResponseEntity<>(patientDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/allergies/all/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicineDTO>> getAllAllergiesOfPatient(@PathVariable("id") Long id){

        Patient patient = patientService.findOne(id);
        if(patient == null) return null;

        List<MedicineDTO> allergiesDTOs = new ArrayList<>();
        for (Medicine m: patient.getAllergies()) {
            allergiesDTOs.add(modelMapper.map(m, MedicineDTO.class));
        }

        return new ResponseEntity<>(allergiesDTOs, HttpStatus.OK);
    }

    @GetMapping(value="/search", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientDTO>> searchPatientsByFirstAndLastName
            (@RequestParam(value = "firstName", required = false) String firstName,
             @RequestParam(value = "lastName", required = false) String lastName){
        List<Patient> patientsResult;
//        if (firstName == null && lastName == null){
//            // TODO da vrati sve pacijente
//        }
        if (firstName == null){
            firstName = "";
        }
        if (lastName == null){
            lastName = "";
        }
        patientsResult = patientService.searchPatientsByFirstAndLastName(firstName, lastName);
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient p:
                patientsResult) {
            patientDTOS.add(convertToDto(p));
        }
        return new ResponseEntity<>(patientDTOS, HttpStatus.OK);
    }

    @DeleteMapping(value = "/allergies/{id}/{allergy_id}")
    public ResponseEntity<String> deleteAllergy(@PathVariable("id") long id, @PathVariable("allergy_id") long allergy_id) {
        if (patientService.deleteAllergy(id, allergy_id)) {
            return new ResponseEntity<>("Allergy deleted successfully", HttpStatus.OK);
        } else {
            return null;
        }
    }

    @PostMapping(value = "/allergies/{id}/{allergy_id}")
    public ResponseEntity<String> addAllergy(@PathVariable("id") long id, @PathVariable("allergy_id") long allergy_id) {

        Medicine allergy = medicineService.findOne(allergy_id);
        if(allergy == null) return null;

        if (patientService.addAllergy(id, allergy)) {
            return new ResponseEntity<>("Allergy added successfully", HttpStatus.OK);
        } else {
            return null;
        }
    }
}
