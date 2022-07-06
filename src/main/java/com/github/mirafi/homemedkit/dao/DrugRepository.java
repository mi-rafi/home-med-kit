package com.github.mirafi.homemedkit.dao;

import com.github.mirafi.homemedkit.dao.entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {

    Optional<Drug> findDrugByNameLikeIgnoreCase(String name);

    List<Drug> findAllByDescriptionIgnoreCase(String description);

}
