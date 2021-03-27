package com.team11.PharmacyProject.medicineFeatures.medicine;

import com.team11.PharmacyProject.dto.MedicineDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/medicine")
public class MedicineController {

    @Autowired
    private MedicineServiceImpl medicineService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicineDTO>> getMedicines() {
        List<MedicineDTO> medicineDTOs = medicineService.getAllMedicines().stream().map(m -> mapper.map(m, MedicineDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(medicineDTOs, HttpStatus.OK);
    }
}
