package com.sparrow.sparrow.service.tag;


import com.sparrow.sparrow.domain.tag.Tag;
import com.sparrow.sparrow.domain.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }
}
