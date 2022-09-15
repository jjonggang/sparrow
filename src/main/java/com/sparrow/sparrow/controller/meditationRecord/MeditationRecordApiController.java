package com.sparrow.sparrow.controller.meditationRecord;

import com.sparrow.sparrow.domain.meditationRecord.MeditationRecord;
import com.sparrow.sparrow.dto.meditationRecord.MeditationRecordRequestDto;
import com.sparrow.sparrow.dto.meditationRecord.MeditationRecordResponseDto;
import com.sparrow.sparrow.dto.response.ResponseDto;
import com.sparrow.sparrow.dto.response.ResponsePageDto;
import com.sparrow.sparrow.service.meditationRecord.MeditationRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class MeditationRecordApiController {

    private final MeditationRecordService meditationRecordService;

    @GetMapping("/meditation-record")
    public ResponseEntity<?> getRecord(@AuthenticationPrincipal String strUserId,
                                       @PageableDefault(page = 0, size=10) Pageable pageable){
        try{
            Long userId = Long.parseLong(strUserId);
            List<MeditationRecord> records = meditationRecordService.findAllByUserId(userId, pageable);
            List<MeditationRecordResponseDto> dto = records.stream()
                    .map(record -> new MeditationRecordResponseDto(record)).collect(Collectors.toList());
            ResponsePageDto<MeditationRecordResponseDto> response = ResponsePageDto.<MeditationRecordResponseDto>builder()
                    .pageCount((long) (meditationRecordService.countByUserId(userId)/10))
                    .data(dto)
                    .build();
            return ResponseEntity.ok().body(response);
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
    @PostMapping("/meditation-record")
    public ResponseEntity<?> postRecord(@AuthenticationPrincipal String strUserId,
                                        @RequestBody MeditationRecordRequestDto requestDto){
        try{
            Long userId = Long.parseLong(strUserId);
            MeditationRecord record = meditationRecordService.create(userId, requestDto);
            return ResponseEntity.ok().body(new MeditationRecordResponseDto(record));
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
    @PutMapping("/meditation-record")
    public ResponseEntity<?> putMeditationRecord(@AuthenticationPrincipal String strUserId,
                                                 @RequestBody MeditationRecordRequestDto requestDto){
        try{
            Long userId = Long.parseLong(strUserId);
            MeditationRecord record = meditationRecordService.update(userId, requestDto);
            return ResponseEntity.ok().body(new MeditationRecordResponseDto(record));
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
    @DeleteMapping("/meditation-record")
    public ResponseEntity<?> deleteMeditationRecord(@AuthenticationPrincipal String strUserId,
                                                    @RequestBody MeditationRecordRequestDto requestDto){
        try{
            Long userId = Long.parseLong(strUserId);
            meditationRecordService.delete(userId, requestDto.getMeditationRecordId());
            return ResponseEntity.ok().body("삭제 완료");
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
