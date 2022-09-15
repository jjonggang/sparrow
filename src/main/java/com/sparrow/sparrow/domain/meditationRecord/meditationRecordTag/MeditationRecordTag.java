package com.sparrow.sparrow.domain.meditationRecord.meditationRecordTag;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparrow.sparrow.domain.meditationRecord.MeditationRecord;
import com.sparrow.sparrow.domain.music.Music;
import com.sparrow.sparrow.domain.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeditationRecordTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("meditation_record_tag_id")
    private Long meditationRecordTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meditation_record_id")
    private MeditationRecord meditationRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
