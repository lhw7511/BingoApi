package com.project.BingoApi.jpa.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "refresh_token")
@Getter
@NoArgsConstructor
public class RefreshToken extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    private Long userId;

    private String tokenName;

    private Date expireDt;

    @Builder
    public RefreshToken(Long userId, String tokenName, Date expireDt) {
        this.userId = userId;
        this.tokenName = tokenName;
        this.expireDt = expireDt;
    }


    public void updateTokenName(String tokenName,Date expireDt){
        this.tokenName = tokenName;
        this.expireDt = expireDt;
    }
}
