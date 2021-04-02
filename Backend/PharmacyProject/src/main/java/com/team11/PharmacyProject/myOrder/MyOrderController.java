package com.team11.PharmacyProject.myOrder;

import com.team11.PharmacyProject.appointment.AppointmentService;
import com.team11.PharmacyProject.dto.AppointmentDTO;
import com.team11.PharmacyProject.dto.order.MyOrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/orders")
public class MyOrderController {

    @Autowired
    MyOrderService orderService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/bypharmacyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MyOrderDTO>> getOrdersByPharmacyId(@PathVariable("id") Long id){
        List<MyOrderDTO> myOrderDTOS = orderService.getOrdersByPharmacyId(id).stream().map(m -> modelMapper.map(m, MyOrderDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(myOrderDTOS, HttpStatus.OK);
    }

}
