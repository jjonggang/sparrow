package com.sparrow.sparrow.service.meditationRecord;

import com.sparrow.sparrow.domain.meditationRecord.MeditationRecord;
import com.sparrow.sparrow.domain.meditationRecord.MeditationRecordRepository;
import com.sparrow.sparrow.domain.meditationRecord.meditationRecordTag.MeditationRecordTag;
import com.sparrow.sparrow.domain.music.Music;
import com.sparrow.sparrow.domain.music.MusicRepository;
import com.sparrow.sparrow.domain.tag.TagRepository;
import com.sparrow.sparrow.domain.user.User;
import com.sparrow.sparrow.domain.user.UserRepository;
import com.sparrow.sparrow.dto.meditationRecord.MeditationRecordRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeditationRecordService {
    private final MeditationRecordRepository meditationRecordRepository;
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    public MeditationRecord create(Long userId, MeditationRecordRequestDto requestDto) {
        Music music = musicRepository.findById(requestDto.getMusicId()).orElseThrow(
                () -> new IllegalArgumentException("해당 노래가 없습니다. id=" + requestDto.getMusicId())
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId)
        );
        MeditationRecord record = MeditationRecord.builder()
                .user(user)
                .disclosure(requestDto.getDisclosure())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .music(music)
                .birdSound(requestDto.getBirdSound())
                .oceanSound(requestDto.getOceanSound())
                .rainSound(requestDto.getRainSound())
                .fireSound(requestDto.getFireSound())
                .durationSec(requestDto.getDurationSec())
                .build();
        MeditationRecord savedRecord = meditationRecordRepository.save(record);
        if (requestDto.getTagIds() != null) {
            List<MeditationRecordTag> tags = requestDto.getTagIds().stream().map(tagId -> {
                return MeditationRecordTag.builder()
                        .meditationRecord(savedRecord)
                        .tag(tagRepository.findById(tagId).orElseThrow(
                                () -> new IllegalArgumentException("해당 태그가 없습니다. id=" + tagId)
                        ))
                        .build();
            }).collect(Collectors.toList());
            savedRecord.setMeditationRecordTags(tags);
        }
        return meditationRecordRepository.save(record);
    }

    public MeditationRecord update(Long userId, MeditationRecordRequestDto requestDto){
        Music music = musicRepository.findById(requestDto.getMusicId()).orElseThrow(
                () -> new IllegalArgumentException("해당 노래가 없습니다. id=" + requestDto.getMusicId())
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId)
        );

        MeditationRecord dbRecord = meditationRecordRepository.findById(requestDto.getMeditationRecordId()).orElseThrow(
                () -> new IllegalArgumentException("해당 기록이 없습니다. id=" + requestDto.getMeditationRecordId())
        );

        dbRecord.update(requestDto, music, user);

        if (requestDto.getTagIds() != null) {
            List<MeditationRecordTag> tags = requestDto.getTagIds().stream().map(tagId -> {
                return MeditationRecordTag.builder()
                        .meditationRecord(dbRecord)
                        .tag(tagRepository.findById(tagId).orElseThrow(
                                () -> new IllegalArgumentException("해당 태그가 없습니다. id=" + tagId)
                        ))
                        .build();
            }).collect(Collectors.toList());
            dbRecord.getMeditationRecordTags().clear();
            dbRecord.getMeditationRecordTags().addAll(tags);
        }
        return meditationRecordRepository.save(dbRecord);
    }

    public void delete(Long userId, Long meditationRecordId){
        MeditationRecord record = meditationRecordRepository.findById(meditationRecordId).orElseThrow(
                () -> new IllegalArgumentException("해당 기록이 없습니다. id=" + meditationRecordId)
        );
        meditationRecordRepository.delete(record);
    }

    public List<MeditationRecord> findAllByUserId(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId)
        );
        return meditationRecordRepository.findAllByUser(user, pageable);
    }

    public Long countByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId)
        );
        return meditationRecordRepository.countByUser(user);
    }
}
