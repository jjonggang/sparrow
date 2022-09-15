package com.sparrow.sparrow.dto.meditationRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparrow.sparrow.domain.meditationRecord.MeditationRecord;
import com.sparrow.sparrow.domain.meditationRecord.meditationRecordTag.MeditationRecordTag;
import com.sparrow.sparrow.domain.music.Music;
import com.sparrow.sparrow.domain.user.User;
import com.sparrow.sparrow.dto.music.MusicResponseDto;
import com.sparrow.sparrow.dto.tag.TagResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeditationRecordResponseDto {
    private Long meditationRecordId;
    private Boolean disclosure;
    private String title;
    private String content;
    private MusicResponseDto music;
    private Integer birdSound;
    private Integer oceanSound;
    private Integer rainSound;
    private Integer fireSound;
    private Integer durationSec;
    private List<TagResponseDto> tags;

    public MeditationRecordResponseDto(MeditationRecord entity){
        this.meditationRecordId = entity.getMeditationRecordId();
        this.disclosure = entity.getDisclosure();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.music = new MusicResponseDto(entity.getMusic());
        this.birdSound = entity.getBirdSound();
        this.oceanSound = entity.getOceanSound();
        this.rainSound = entity.getRainSound();
        this.fireSound = entity.getFireSound();
        this.durationSec = entity.getDurationSec();
        this.tags = entity.getMeditationRecordTags().stream().map(tag -> new TagResponseDto(tag.getTag())).collect(Collectors.toList());
    }
}
