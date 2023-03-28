package com.project.BingoApi.jpa.domain;

import com.project.BingoApi.jpa.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends  BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String fullName;

    private String password;

    private String email;

    private String address;

    private String profileUrl;

    private String accessToken;

    private String roles;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<WishList> wishLists = new ArrayList<>();

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public void setWishLists(WishList wishList){
        this.wishLists.add(wishList);
        wishList.setUser(this);
    }
    @Builder
    public User(Long userId, String fullName, String password, String email, String address, String profileUrl, String accessToken, String roles) {
        this.id = userId;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.profileUrl = profileUrl;
        this.accessToken = accessToken;
        this.roles = roles;
    }
}
