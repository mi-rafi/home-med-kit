package com.github.mirafi.homemedkit.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "drug")
public class Drug {

    @Id
    @GeneratedValue
    private long id;

    private String name;
//    private Duration expirationDate;

    private String description;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "drug_id")
    private List<AvailableDrug> availableDrugs = new ArrayList<>();
}
