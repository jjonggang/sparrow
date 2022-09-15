package com.sparrow.sparrow.controller.music;

import com.sparrow.sparrow.domain.music.Music;
import com.sparrow.sparrow.domain.tag.Tag;
import com.sparrow.sparrow.dto.music.MusicResponseDto;
import com.sparrow.sparrow.dto.response.ResponseDto;
import com.sparrow.sparrow.dto.tag.TagResponseDto;
import com.sparrow.sparrow.service.music.MusicService;
import com.sparrow.sparrow.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class MusicApiController {
    private final MusicService musicService;
    @GetMapping("/music")
    public ResponseEntity<?> getTags(){
        try{
            List<Music> musics = musicService.getAllMusics();
            List<MusicResponseDto> dto = musics.stream()
                    .map(music -> new MusicResponseDto(music)).collect(Collectors.toList());
            return ResponseEntity.ok().body(dto);
        }catch (Exception e){
            // 사용자 정보는 항상 하나이므로 리스트로 만들어야하는 ResponseDto를 사용하지 않고 그냥 UserDto리턴
            ResponseDto responseDto = ResponseDto.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDto);
        }
    }
}