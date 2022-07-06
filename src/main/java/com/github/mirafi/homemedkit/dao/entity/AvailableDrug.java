package com.github.mirafi.homemedkit.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "available_drug")
public class AvailableDrug {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "chat_id")
    private long chatId;
//    private LocalDate startTime;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "drug_id", nullable = false)
    private Drug drug;
}
