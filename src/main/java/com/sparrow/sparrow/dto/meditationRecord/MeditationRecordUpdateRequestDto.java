package com.sparrow.sparrow.dto.meditationRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeditationRecordUpdateRequestDto {
    private Long meditationRecordId;
    private Boolean disclosure;
    private String title;
    private String content;
    private List<Long> tagIds;
}
