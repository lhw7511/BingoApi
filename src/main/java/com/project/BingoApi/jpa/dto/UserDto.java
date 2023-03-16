package com.project.BingoApi.jpa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.BingoApi.jpa.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public class UserDto {

    private Long userId;

    private String fullName;

    private String password;

    private String email;

    private String address;

    private String profileUrl;

    private String accessToken;

    private List<ReviewDto> reviewDtos;

    private String roles;

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }


    public UserDto(User user) {
        this.userId = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.profileUrl = user.getProfileUrl();
        this.roles = user.getRoles();

    }
}
