package com.project.BingoApi.jpa.repository;


import com.project.BingoApi.jpa.domain.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishListRepository extends JpaRepository<WishList,Long> {

    @Query("select w from WishList w where w.user.id =:userId and w.restaurant.id =:restaurantId")
    WishList getWishListOne(@Param("userId") Long userId,@Param("restaurantId") Long restaurantId);
}
