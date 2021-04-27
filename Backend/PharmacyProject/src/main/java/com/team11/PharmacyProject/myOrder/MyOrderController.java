package com.team11.PharmacyProject.myOrder;

import com.team11.PharmacyProject.appointment.AppointmentService;
import com.team11.PharmacyProject.dto.appointment.AppointmentDTO;
import com.team11.PharmacyProject.dto.order.MyOrderAddingDTO;
import com.team11.PharmacyProject.dto.order.MyOrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<MyOrderDTO>> getOrdersByPharmacyId(@PathVariable("id") Long id, @RequestParam(value = "filter", required = false) String filterValue) {
        List<MyOrderDTO> myOrderDTOS = orderService.getOrdersByPharmacyId(id, filterValue).stream().map(m -> modelMapper.map(m, MyOrderDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(myOrderDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MyOrderDTO>> getAvailableOrders() {
        List<MyOrderDTO> myOrderDTOS = orderService.getAvailableOrders();
        return new ResponseEntity<>(myOrderDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MyOrderDTO> getOrder(@PathVariable("id") long id) {
        MyOrderDTO myOrderDTO = orderService.getOrder(id);
        if (myOrderDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(myOrderDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/addorder", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addOrder(@RequestBody MyOrderAddingDTO data) {
        boolean flag = orderService.addOrder(data);
        if(flag)
          return new ResponseEntity<>("Succesfully added", HttpStatus.OK);
        else
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);

    }

}
