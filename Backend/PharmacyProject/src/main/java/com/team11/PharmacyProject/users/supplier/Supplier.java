package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.offer.Offer;
import com.team11.PharmacyProject.supplierItem.SupplierItem;
import com.team11.PharmacyProject.users.user.MyUser;
import com.team11.PharmacyProject.users.user.Role;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Supplier extends MyUser {

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SupplierItem> supplierItems;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Offer> offers;

    public Supplier() {

    }

    public Supplier(Long id, String password, String firstName, String lastName, String email, String telephone, Role userType, Address address, List<SupplierItem> supplierItems, List<Offer> offers, boolean isPasswordChanged) {
        super(id, password, firstName, lastName, email, telephone, userType, address, isPasswordChanged);
        this.supplierItems = supplierItems;
        this.offers = offers;
    }

    public List<SupplierItem> getSupplierItems() {
        return supplierItems;
    }

    public void setSupplierItems(List<SupplierItem> supplierItems) {
        this.supplierItems = supplierItems;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}