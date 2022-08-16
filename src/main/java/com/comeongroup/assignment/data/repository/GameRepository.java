package com.comeongroup.assignment.data.repository;

import com.comeongroup.assignment.data.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Boolean existsByName(String name);
}