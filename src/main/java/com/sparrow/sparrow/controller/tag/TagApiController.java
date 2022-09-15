package com.sparrow.sparrow.controller.tag;

import com.sparrow.sparrow.domain.meditationRecord.MeditationRecord;
import com.sparrow.sparrow.domain.tag.Tag;
import com.sparrow.sparrow.dto.meditationRecord.MeditationRecordResponseDto;
import com.sparrow.sparrow.dto.response.ResponseDto;
import com.sparrow.sparrow.dto.response.ResponsePageDto;
import com.sparrow.sparrow.dto.tag.TagResponseDto;
import com.sparrow.sparrow.service.tag.TagService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class TagApiController {

    private final TagService tagService;

    @GetMapping("/tag")
    public ResponseEntity<?> getTags(){
        try{
            List<Tag> tags = tagService.getAllTags();
            List<TagResponseDto> dto = tags.stream()
                    .map(tag -> new TagResponseDto(tag)).collect(Collectors.toList());
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
