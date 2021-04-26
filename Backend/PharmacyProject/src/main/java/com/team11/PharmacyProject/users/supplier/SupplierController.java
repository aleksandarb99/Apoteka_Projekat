package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.dto.supplier.SupplierStockItemDTO;
import com.team11.PharmacyProject.supplierItem.SupplierItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping(value="/stock/{id}")
    public ResponseEntity<List<SupplierStockItemDTO>> getStock(@PathVariable("id") long supplierId) {
        List<SupplierItem> supplierStock = supplierService.getStockForId(supplierId);
        List<SupplierStockItemDTO> supplierStockItemDTOS = supplierStock.stream()
                .map(si -> new SupplierStockItemDTO(si.getMedicine().getId(), si.getMedicine().getName(), si.getAmount()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(supplierStockItemDTOS, HttpStatus.OK);
    }
}
