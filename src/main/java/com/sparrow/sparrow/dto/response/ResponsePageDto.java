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
public class ResponsePageDto<T> {
    private String error;
    private Long pageCount;
    private List<T> data;
}
