package com.sparrow.sparrow.dto.tag;

import com.sparrow.sparrow.domain.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TagResponseDto {
    private Long tagId;
    private String tagNameKor;
    private String tagNameEng;
    public TagResponseDto(Tag entity){
        this.tagId = entity.getTagId();
        this.tagNameKor = entity.getTagNameKor();
        this.tagNameEng = entity.getTagNameEng();
    }
}
