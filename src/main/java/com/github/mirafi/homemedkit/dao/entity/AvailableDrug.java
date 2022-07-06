package com.github.mirafi.homemedkit.dao.entity;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class AvailableDrug {

    private long id;
    private long chatId;
//    private LocalDate startTime;
    private LocalDate expirationDate;
}
