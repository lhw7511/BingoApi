package com.project.BingoApi.jpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {

    private Long userId;

    private String fullName;

    private String password;

    private String email;

    private String address;

    private String profileUrl;

    private String accessToken;

    private String roles;

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
