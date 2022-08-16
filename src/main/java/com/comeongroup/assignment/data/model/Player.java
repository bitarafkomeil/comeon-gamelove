package com.comeongroup.assignment.data.model;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * A Player.
 */
@Entity
@Table
@Data
public class Player implements IdentifiableEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}