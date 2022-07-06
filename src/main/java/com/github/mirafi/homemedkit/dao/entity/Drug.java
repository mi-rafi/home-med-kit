package com.github.mirafi.homemedkit.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "drug")
public class Drug {

    @Id
    private long id;

    private String name;
//    private Duration expirationDate;

    private String description;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "drug_id")
    private List<AvailableDrug> availableDrugs;
}
