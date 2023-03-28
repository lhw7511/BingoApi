package com.project.BingoApi.jpa.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "wish_list")
@NoArgsConstructor
public class WishList extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_list_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;


    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
        restaurant.getWishLists().add(this);
    }

    public static WishList createWishList(User user,Restaurant restaurant){
        WishList wishList = new WishList();
        wishList.setRestaurant(restaurant);
        wishList.setUser(user);
        return wishList;
    }




}
