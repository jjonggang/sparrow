package com.sparrow.sparrow.domain.meditationRecord;

import com.sparrow.sparrow.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface MeditationRecordRepository extends JpaRepository<MeditationRecord, Long> {
    List<MeditationRecord> findAllByUser(User user, Pageable pageable);

    Long countByUser(User user);
}
