package com.team11.PharmacyProject.users.pharmacist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacistServiceImpl implements  PharmacistService{
    @Autowired
    PharmacistRepository pharmacistRepository;


}
