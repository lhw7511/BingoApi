package com.project.BingoApi.jpa.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "images_restaurant")
@Getter
@NoArgsConstructor
public class ImageRestaurant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "images_restaurant_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


    @Column(name = "image_url")
    private String imageUrl;


    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
        restaurant.getImagesRestaurants().add(this);
    }

}
