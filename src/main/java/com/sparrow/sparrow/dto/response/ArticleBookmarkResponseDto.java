package com.sparrow.sparrow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleBookmarkResponseDto {
    private String error;
    private List<Long> bookmark;
}
