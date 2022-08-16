package com.comeongroup.assignment.data.model;

import lombok.Data;
import org.hibernate.annotations.LazyGroup;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * A GameData.
 */
@Entity
@Table
@Data
public class GameData implements IdentifiableEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    @LazyToOne(LazyToOneOption.PROXY)
    @LazyGroup("player")
    private Player player;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    @LazyToOne(LazyToOneOption.PROXY)
    @LazyGroup("game")
    private Game game;
}