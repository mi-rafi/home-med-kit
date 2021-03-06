package com.github.mirafi.homemedkit.dao;

import com.github.mirafi.homemedkit.dao.entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedKitRepository extends JpaRepository<Drug, Long> {
}
