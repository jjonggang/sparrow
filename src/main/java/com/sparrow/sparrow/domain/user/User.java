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
    private String email;
    private String gender;
    private String birthday;
    @Enumerated(EnumType.STRING)
    private Role role;

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

}
