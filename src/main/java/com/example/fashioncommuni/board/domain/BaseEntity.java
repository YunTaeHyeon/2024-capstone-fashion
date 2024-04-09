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
* BaseEntity에서는 생성 시간과 수정시간을 관리한다.
 */
@MappedSuperclass // 상속받는 엔티티에게 매핑 정보만 제공
@EntityListeners(AuditingEntityListener.class) // 엔티티의 생명주기를 감지
@Getter
public class BaseEntity { // 엔티티의 공통 매핑 정보가 필요할 때 사용

    @CreationTimestamp // 엔티티가 생성되어 저장될 때 시간이 자동 저장
    @Column(updatable = false) // 엔티티가 수정될 때 시간이 자동 저장
    private LocalDateTime createdAt; // 생성 시간

    @UpdateTimestamp // 엔티티가 수정되어 저장될 때 시간이 자동 저장
    @Column(insertable = false) // 엔티티가 생성될 때 시간이 자동 저장
    private LocalDateTime updatedAt; // 수정 시간
}
