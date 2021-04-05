package com.team11.PharmacyProject.myOrder;

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

}
