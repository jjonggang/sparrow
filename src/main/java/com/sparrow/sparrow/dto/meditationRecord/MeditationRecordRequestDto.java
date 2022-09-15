package com.sparrow.sparrow.dto.meditationRecord;

import com.sparrow.sparrow.dto.music.MusicResponseDto;
import com.sparrow.sparrow.dto.tag.TagResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeditationRecordRequestDto {
    private Long meditationRecordId;
    private Boolean disclosure;
    private String title;
    private String content;
    private Long musicId;
    private Integer birdSound;
    private Integer oceanSound;
    private Integer rainSound;
    private Integer fireSound;
    private Integer durationSec;
    private List<Long> tagIds;
}
