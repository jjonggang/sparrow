package com.sparrow.sparrow.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparrow.sparrow.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("user_id")
    private Long userId;
    private String name;
    private Integer duration;
    @JsonProperty("profile_image")
    private String profileImage;
    private String email;
    private String gender;
    private String birthday;
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonProperty("refresh_token")
    private String refreshToken;

    private String level;
    @Builder
    public User(String name, String email, Role role){
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public User update(String name){
        this.name = name;

        return this;
    }
    public String getRoleKey(){
        return this.role.getKey();
    }

    public void setRefreshToken(String encodedToken) {
        this.refreshToken = encodedToken;
    }

    public void updateMeditationTime(Integer duration) {
        this.duration += duration;
    }
}
