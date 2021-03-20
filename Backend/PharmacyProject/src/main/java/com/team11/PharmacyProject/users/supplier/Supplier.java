package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.offer.Offer;
import com.team11.PharmacyProject.supplierItem.SupplierItem;
import com.team11.PharmacyProject.users.user.User;

import java.util.List;

public class Supplier extends User {
   private List<SupplierItem> supplierItems;
   private List<Offer> offers;
}