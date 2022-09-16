package com.sparrow.sparrow.domain.meditationRecord;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparrow.sparrow.domain.BaseTimeEntity;
import com.sparrow.sparrow.domain.meditationRecord.meditationRecordTag.MeditationRecordTag;
import com.sparrow.sparrow.domain.music.Music;
import com.sparrow.sparrow.domain.user.User;
import com.sparrow.sparrow.dto.meditationRecord.MeditationRecordRequestDto;
import com.sparrow.sparrow.dto.meditationRecord.MeditationRecordUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeditationRecord extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("meditation_record_id")
    private Long meditationRecordId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @JsonProperty("disclosure")
    private Boolean disclosure;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private Music music;
    @JsonProperty("bird_sound")
    private Integer birdSound;
    @JsonProperty("ocean_sound")
    private Integer oceanSound;
    @JsonProperty("rain_sound")
    private Integer rainSound;
    @JsonProperty("fire_sound")
    private Integer fireSound;
    private Integer duration;
    @OneToMany(mappedBy = "meditationRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    List<MeditationRecordTag> meditationRecordTags;

    public void setMeditationRecordTags(List<MeditationRecordTag> tags) {
        this.meditationRecordTags = tags;
    }

    public void update(MeditationRecordUpdateRequestDto requestDto, User user) {
        this.disclosure = requestDto.getDisclosure();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
    }
}
