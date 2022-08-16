package com.comeongroup.assignment.data.model;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * A Game.
 */
@Entity
@Table
@Data
public class Game implements IdentifiableEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}