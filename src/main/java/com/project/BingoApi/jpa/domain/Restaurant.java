package com.project.BingoApi.jpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Restaurant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    private String lmsKey;

    private String name;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String coordinate;

    @Column(name = "open_times")
    private String openTime;


    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    private List<ImageRestaurant> imagesRestaurants = new ArrayList<>();


    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

}
