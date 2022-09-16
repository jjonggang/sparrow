package com.sparrow.sparrow.service.user;

import com.sparrow.sparrow.domain.user.User;
import com.sparrow.sparrow.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 유저가 없습니다."));
    }

    public User updateMeditationTime(Long userId, Integer duration) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 유저가 없습니다."));
        user.updateMeditationTime(duration);
        return userRepository.save(user);
    }
}
