package com.github.mirafi.homemedkit.dao;

import com.github.mirafi.homemedkit.dao.entity.AvailableDrug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvailableDrugRepository extends JpaRepository<AvailableDrug, Long> {

    List<AvailableDrug> findAllByChatId(Long chatId);
}
