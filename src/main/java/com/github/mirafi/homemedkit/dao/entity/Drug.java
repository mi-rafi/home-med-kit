package com.github.mirafi.homemedkit.dao.entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Drug {

    private long id;
    private String name;
//    private Duration expirationDate;
    private String description;
    @OneToMany(mappedBy = "drug_id", fetch = FetchType.EAGER)
    private List<AvailableDrug> availableDrugs;
}
