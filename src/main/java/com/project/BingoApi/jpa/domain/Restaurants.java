package com.project.BingoApi.jpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Restaurants {

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

    @OneToMany(mappedBy = "restaurants",cascade = CascadeType.ALL)
    private List<ImagesRestaurant> imagesRestaurantsList = new ArrayList<>();

}
