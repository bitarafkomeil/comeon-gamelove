package com.comeongroup.assignment.data.repository;

import com.comeongroup.assignment.data.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Boolean existsByName(String name);
}