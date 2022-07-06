package com.github.mirafi.homemedkit.dao.entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "drug")
@Builder
public class Drug {

    @Id
    private long id;

    private String name;
//    private Duration expirationDate;

    private String description;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "drug_id")
    private List<AvailableDrug> availableDrugs;

    public Drug(String name, String description, List<AvailableDrug> availableDrugs) {
        this.name = name;
        this.description = description;
        this.availableDrugs = availableDrugs;
    }

    public Drug(String name) {
        this(name, "");
    }

    public Drug(String name, String description) {
        this(name, description, Collections.emptyList());
    }
}
