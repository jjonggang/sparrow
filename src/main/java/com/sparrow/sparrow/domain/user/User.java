package com.sparrow.sparrow.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
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

    public String getRoleKey(){
        return this.role.getKey();
    }

}
