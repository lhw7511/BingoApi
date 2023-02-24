package com.project.BingoApi.jpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Restaurant extends  BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_key")
    private Category category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "region_Id")
    private Region region;

    private String name;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String latitude;

    private String longitude;

    @Column(name = "open_time")
    private String openTime;

    private String parkingYn;


    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    private List<ImageRestaurant> imagesRestaurants = new ArrayList<>();


    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    private List<Food> foods = new ArrayList<>();


    public void setFoods(Food food){
        this.foods.add(food);
        food.setRestaurant(this);
    }

    public void setImagesRestaurants(ImageRestaurant imageRestaurant) {
        this.imagesRestaurants.add(imageRestaurant);
        imageRestaurant.setRestaurant(this);
    }

    public void setReviews(Review review){
        this.reviews.add(review);
        review.setRestaurant(this);
    }

    public void setCategory(Category category){
        this.category = category;
        category.getRestaurants().add(this);
    }

    public void setRegion(Region region){
        this.region = region;
        region.getRestaurants().add(this);
    }
}
