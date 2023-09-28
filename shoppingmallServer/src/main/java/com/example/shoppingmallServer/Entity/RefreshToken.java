package com.example.shoppingmallServer.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String refreshToken;
    @NotBlank
    private String memberId;

    public RefreshToken(String token, String id) {
        this.refreshToken = token;
        this.memberId = id;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}
