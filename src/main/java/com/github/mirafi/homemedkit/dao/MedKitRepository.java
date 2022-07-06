package com.github.mirafi.homemedkit.dao;

import com.github.mirafi.homemedkit.dao.entity.Drug;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedKitRepository extends JpaRepository<Drug, Long> {

    Optional<Drug> findDrugByNameLikeIgnoreCase(String name);

    List<Drug> findAllByDescriptionIgnoreCase(String description);

}
