package com.project.BingoApi.jpa.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "image_restaurant")
@Getter
@NoArgsConstructor
public class ImageRestaurant extends  BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_restaurant_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    @Setter
    private Restaurant restaurant;


    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_key")
    private String imageKey;



}
