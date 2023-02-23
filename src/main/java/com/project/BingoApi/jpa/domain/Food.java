package com.project.BingoApi.jpa.domain;

import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Food {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    private String name;

    private Long price;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    @Setter
    private Restaurant restaurant;





}
