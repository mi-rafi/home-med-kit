package com.github.mirafi.homemedkit.dao;

import com.github.mirafi.homemedkit.dao.entity.AvailableDrug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableDrugRepository extends JpaRepository<AvailableDrug, Long> {

}
