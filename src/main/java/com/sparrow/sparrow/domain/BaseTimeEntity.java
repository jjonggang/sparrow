package com.sparrow.sparrow.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들도 칼럼으로 인식하도록 한다.
@EntityListeners(AuditingEntityListener.class) // Auditing 기능을 포함시킨다.
public abstract class BaseTimeEntity {
    @JsonProperty("created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonProperty("modified_date")
    @LastModifiedDate // 조회한 Entity값을 변경할 때 시간이 자동 저장된다.
    private LocalDateTime modifiedDate;
}
