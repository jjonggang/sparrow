package com.sparrow.sparrow.dto.music;

import com.sparrow.sparrow.domain.music.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MusicResponseDto {
    private Long musicId;
    private String musicName;
    private String musicPath;
    private String musicImagePath;
    private String musicDurationSec;
    private String composer;
    public MusicResponseDto(Music entity){
        this.musicId = entity.getMusicId();
        this.musicName = entity.getMusicName();
        this.musicPath = entity.getMusicPath();
        this.musicImagePath = entity.getMusicImagePath();
        this.musicDurationSec = entity.getMusicDurationSec();
        this.composer = entity.getComposer();
    }
}

