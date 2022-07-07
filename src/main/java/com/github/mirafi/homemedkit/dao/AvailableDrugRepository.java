package com.github.mirafi.homemedkit.dao;

import com.github.mirafi.homemedkit.dao.entity.AvailableDrug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvailableDrugRepository extends JpaRepository<AvailableDrug, Long> {

    @Modifying
    @Query(value = "delete from available_drug\n" +
            "where available_drug.chat_id = :chat_id\n" +
            "  and id in (select ad.id from drug\n" +
            "      join available_drug ad\n" +
            "          on drug.id = ad.drug_id\n" +
            "      where upper(drug.name) like upper(:name));", nativeQuery = true)
    void deleteAvailableDrugByChatIdAndName(@Param("chat_id") Long userChatId,
                                            @Param("name") String nameDrug);


    List<AvailableDrug> findAllByChatId(Long chatId);
}
