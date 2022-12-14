package com.sparrow.sparrow.dto.user;

import com.sparrow.sparrow.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBasicResponseDto {
    private String profileImage;
    private String name;
    private Integer duration;
    public UserBasicResponseDto(User entity) {
        if(entity.getProfileImage() == null){
            this.profileImage = "https://nutrition-helper-bucket.s3.ap-northeast-2.amazonaws.com/profile/cat_profile.jpg";
        }else{
            this.profileImage = entity.getProfileImage();
        }
        this.name = entity.getName();
        this.duration = entity.getDuration();
    }
}
