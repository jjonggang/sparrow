package com.sparrow.sparrow.service.music;

import com.sparrow.sparrow.domain.music.Music;
import com.sparrow.sparrow.domain.music.MusicRepository;
import com.sparrow.sparrow.domain.tag.Tag;
import com.sparrow.sparrow.domain.tag.TagRepository;
import com.sparrow.sparrow.dto.music.MusicResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;

    public List<Music> getAllMusics(){
        return musicRepository.findAll();
    }
}
