package com.example.fashioncommuni.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
/*
* BaseEntity에서는 게시물의 작성 시간을 확인하고, 이를 상속시킨다
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // JPA에게 해당 엔티티는 Auditing 기능을 사용한다는 것을 알림
@Getter
public class BaseEntity { // 게시물의 작성 시간을 확인하는 클래스

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
