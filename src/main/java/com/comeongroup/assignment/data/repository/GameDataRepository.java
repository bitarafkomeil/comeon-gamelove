package com.comeongroup.assignment.data.repository;

import com.comeongroup.assignment.data.dto.gamedata.MostLovedDto;
import com.comeongroup.assignment.data.model.GameData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameDataRepository extends JpaRepository<GameData, Long> {
    Boolean existsByGameId(Long gameId);

    Boolean existsByPlayerId(Long playerId);

    Optional<GameData> findByGameIdAndPlayerId(Long gameId, Long playerId);

    @Query(value = "SELECT game_id as gameId, COUNT(*) as count " +
            "FROM game_data " +
            "GROUP BY game_id ORDER BY COUNT(*) desc limit :count ", nativeQuery = true)
    List<MostLovedDto> getMostTop(@Param("count") Integer count);

    Page<GameData> findAllByPlayerId(Pageable pageable, Long playerId);

    default Page<GameData> getAllByPlayerIdAndPageable(Pageable pageable, Optional<Long> playerId) {
        if (playerId.isEmpty())
            return findAll(pageable);
        else
            return findAllByPlayerId(pageable, playerId.get());
    }
}