package com.team11.PharmacyProject.advertisement;

import com.team11.PharmacyProject.dto.advertisment.AdvertismentDTO;
import com.team11.PharmacyProject.dto.advertisment.AdvertismentDTORequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/sales")
public class AdvertismentController {

    @Autowired
    AdvertismentService advertismentService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AdvertismentDTO>> findAll(@PathVariable("id") Long id) {
        List<AdvertismentDTO> advertisementDTOS = null;
        try {
            advertisementDTOS = advertismentService.findAll(id).stream().map(m -> modelMapper.map(m, AdvertismentDTO.class)).collect(Collectors.toList());
            return new ResponseEntity<>(advertisementDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(advertisementDTOS, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<String> addAdvertisment(@PathVariable("id") Long id, @RequestBody AdvertismentDTORequest dto) {
        boolean flag = advertismentService.addAdvertisment(id, dto);
       if(flag){
           return new ResponseEntity<>("Success", HttpStatus.OK);
       }
        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }


}
