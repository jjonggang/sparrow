package com.sparrow.sparrow.domain.music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("music_id")
    private Long musicId;
    @JsonProperty("music_name")
    private String musicName;
    @JsonProperty("music_path")
    private String musicPath;
    @JsonProperty("music_image_path")
    private String musicImagePath;
    @JsonProperty("music_duration_sec")
    private String musicDurationSec;
    private String composer;
}
